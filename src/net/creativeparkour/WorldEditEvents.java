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

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.event.extent.EditSessionEvent;
import com.sk89q.worldedit.extension.platform.Actor;
import com.sk89q.worldedit.extent.AbstractBufferingExtent;
import com.sk89q.worldedit.extent.Extent;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.math.Vector3;
import com.sk89q.worldedit.util.eventbus.Subscribe;
import com.sk89q.worldedit.world.block.BlockState;
import com.sk89q.worldedit.world.block.BlockStateHolder;

class WorldEditEvents 
{
	/*private static boolean registered = false;
	static Map<CPMap, Date> majMaps = new HashMap<CPMap, Date>();

	@Subscribe
	public void wrapForLogging(EditSessionEvent event) {
		Actor actor = event.getActor();

		// An actor should be generally available for every case where
		// EditSessions are created because of a player-invoked command,
		// but the field may be null if, for example, the edit session
		// is created for another plugin
		if (actor != null && actor.isPlayer()) {
			event.setExtent((Extent) new WorldEditLogger(actor, event.getExtent()));
		}
	}

	static void register()
	{
		if (!registered)
		{
			WorldEdit.getInstance().getEventBus().register(new WorldEditEvents());
			registered = true;
		}
	}*/
}

/*class WorldEditLogger extends AbstractBufferingExtent {
	
	private final Actor actor;

	WorldEditLogger(Actor actor, Extent extent) 
	{
		super(extent);
		this.actor = actor;
	}

	protected void onBlockChange(BlockVector3 position, BlockState newBlock) 
	{
		BlockState oldBlock = position.

		// Remember that the player is the internal WorldEdit one, which
		// MAY be castable to an implementation-specific one, but this
		// is not guaranteed, even if your plugin is for one specific
		// implementation (such as Bukkit). It may be possible for a
		// server running more than one implementation (i.e. Forge and
		// Bukkit) to reuse the same instance of WorldEdit, which would
		// mean that the player could either be a ForgePlayer or a
		// BukkitPlayer, or even maybe something else!
		//System.out.println(actor.getName() + " set block @ " + position + " from " + oldBlock + " to " + newBlock);
		
		Player p = (Player) BukkitAdapter.adapt(actor);
		final Joueur j = GameManager.getJoueur(p);
		boolean revert = false;
		boolean dansUneMap = false;
		if (j != null)
		{
			final CPMap m = j.getMapObjet();
			if (j.getEtat() == EtatJoueur.CREATION && m != null)
			{
				dansUneMap = true;
				boolean maj = true;
				if (WorldEditEvents.majMaps.containsKey(m) && WorldEditEvents.majMaps.get(m).getTime() + 1000 > new Date().getTime()) // Si la dernière mise à jour était il y a moins d'une seconde, pas de mise à jour
					maj = false;
				if (maj)
				{
					Bukkit.getScheduler().runTaskLater(CreativeParkour.getPlugin(), new Runnable() {
						public void run() {
							m.setValide(false);
							j.getPlayer().setScoreboard(m.getScoreboardC());
						}
					}, 2); // 2 ticks après
					WorldEditEvents.majMaps.put(m, new Date());
				}
				if (m.estEnTest() || position.getBlockX() < m.getMinLoc().getX() || position.getBlockY() < m.getMinLoc().getY() || position.getBlockZ() < m.getMinLoc().getZ() || 
						position.getBlockX() > m.getMaxLoc().getX() || position.getBlockY() > m.getMaxLoc().getY() || position.getBlockZ() > m.getMaxLoc().getZ())
				{
					revert = true;
					if (m.estEnTest())
						j.avertissementWorldEdit(Langues.getMessage("creation.test build"));
					else
						j.avertissementWorldEdit(Langues.getMessage("creation.wand.error"));

				}
				else if (GameManager.blocsInterdits.contains(Material.getMaterial(newBlock.toBaseBlock().getType())))
				{
					newBlock.setIdAndData(oldBlock.toBaseBlock().getBlockType().getLegacyId(),(byte) 0);
					j.avertissementWorldEdit(Langues.getMessage("creation.wand.error block"));
				}
			}
		}
		if (!dansUneMap && !p.hasPermission("creativeparkour.manage") && position.getBlockX() >= Config.getConfig().getInt("map storage.storage location x min") - 1 && position.getBlockY() >= Config.getConfig().getInt("map storage.storage location y min") - 1 && position.getBlockZ() >= Config.getConfig().getInt("map storage.storage location z min") - 1)
		{
			revert = true;
		}
		
		if (revert)
		{
			newBlock.setIdAndData(oldBlock.getBlockType());
		}
	}

	@Override
	public <T extends BlockStateHolder<T>> boolean setBlock(BlockVector3 arg0, T arg1) throws WorldEditException {
		// TODO Auto-generated method stub
		return false;
	}
}*/