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

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;

class BlocTP extends BlocSpecial
{
	private Location locTP;

	BlocTP(Block b,Material material,Location locTP)
	{
		super(b, true,material);
		this.locTP = locTP;
	}

	@Override
	void faireAction(Joueur j)
	{
		if (j.getMapObjet().containsBlock(locTP.getBlock()))
		{
			Location l = locTP.clone();
			l.setPitch(j.getPlayer().getLocation().getPitch());
			l.setYaw(j.getPlayer().getLocation().getYaw());
			j.getPlayer().teleport(l);
		}
	}

	@Override
	void setConfig(ConfigurationSection conf)
	{
		conf.set("t", getType());
		conf.set("x", locTP.getX());
		conf.set("y", locTP.getY());
		conf.set("z", locTP.getZ());
	}

	static String getType() {
		return "tp";
	}

	@Override
	String getTypeA() {
		return getType();
	}

	@Override
	String[] getPanneau() {
		return new String[]{getTag(), String.valueOf(locTP.getX()), String.valueOf(locTP.getY()), String.valueOf(locTP.getZ())};
	}

	Location getLocTP() {
		return locTP;
	}

	static String getTag()
	{
		return CPUtils.bracket("tp");
	}
}
