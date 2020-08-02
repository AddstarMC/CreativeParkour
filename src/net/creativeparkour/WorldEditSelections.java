/* CreativeParkour - Bukkit Plugin that allows everyone on the server to create, publish, share and play cool parkour maps.
    Copyright (C) 2017  ObelusPA

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package net.creativeparkour;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.material.MaterialData;
import org.bukkit.util.Vector;

import com.sk89q.worldedit.IncompleteRegionException;
import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.Region;

class WorldEditSelections
{
	private static WorldEditPlugin worldEdit = (WorldEditPlugin) CreativeParkour.getWorldEdit();

	static CubeDeBlocs getSelectionCubique(Player p)
	{
		LocalSession s = worldEdit.getSession(p);
		if (s == null)
			return null;
		Region region = null;
		try 
		{
			region = s.getSelection(s.getSelectionWorld());
		} 
		catch (IncompleteRegionException IncompleteRegionException) 
		{
			
		}

		Map<Vector, MaterialData> blocs = new HashMap<Vector, MaterialData>();
		Block min = getBlock(region,region.getMinimumPoint());
		Block max = getBlock(region,region.getMaximumPoint());
		World w = min.getWorld();
		int taille = region.getHeight();
		if (region.getLength() > taille)
			taille = region.getLength();
		if (region.getWidth() > taille)
			taille = region.getWidth();
		int xLim = min.getX() + taille;
		int yLim = min.getY() + taille;
		int zLim = min.getZ() + taille;
		for (int x = min.getX(); x <= xLim; x++)
		{
			for (int y = min.getY(); y <= yLim; y++)
			{
				for (int z = min.getZ(); z <= zLim; z++)
				{
					MaterialData md = null;
					if (x <= max.getX() && y <= max.getY() && z <= max.getZ()) // Si c'est dans la sélection, on prend le bloc, sinon on met un bloc d'air pour que ça soit cubique
					{
						Block b = w.getBlockAt(x, y, z);
						md = new MaterialData(b.getType(), b.getData());
					}
					else
						md = new MaterialData(Material.AIR);
					blocs.put(new Vector(x - min.getX(), y - min.getY(), z - min.getZ()), md);
				}
			}
		}
		return new CubeDeBlocs(blocs, taille);
	}
	
	private static Block getBlock(Region region, BlockVector3 vector)
	{
		return (Block) region.getWorld().getBlock(vector);
	}
}
