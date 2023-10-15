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

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.metadata.MetadataValue;

class PlayerVisibilityManager
{
	private static ProtocolManager protocolManager;
	private static boolean enabled;

	static void enable()
	{
		protocolManager = ProtocolLibrary.getProtocolManager();
		enabled = true;
	}

	static boolean isEnabled()
	{
		return enabled;
	}

	static void majVisibiliteJoueurs(Joueur joueur) {
		for (Joueur observer : GameManager.getJoueurs(joueur.getMap()))
		{
			if (!observer.equals(joueur))
			{
				if (!isVanished(observer.getPlayer())) {
					if (joueur.visibiliteJoueurs() == VisibiliteJoueurs.VISIBLE)
						showPlayerTo(observer.getPlayer(), joueur.getPlayer());
					else
						hidePlayerFrom(observer.getPlayer(), joueur.getPlayer());
				}
			}
		}
	}

	static void updatePlayerVisibility(Joueur player) {
		if (player.getMap() != null) {
			// Player is inside a map
			majVisibiliteJoueurs(player);
		} else {
			// Player not in a map
			// Ensure observers can see this play
			for (Player observer : Bukkit.getOnlinePlayers()) {
				if (!observer.equals(player.getPlayer())) {
					if (!isVanished(observer.getPlayer())) {
						showPlayerTo(player.getPlayer(), observer);
						showPlayerTo(observer, player.getPlayer());
					}
				}
			}
		}
	}

	static public void hidePlayerFrom(Player playerToHide, Player observer) {
		observer.hidePlayer(CreativeParkour.getPlugin(), playerToHide);
	}

	static public void showPlayerTo(Player playerToShow, Player observer) {
		observer.showPlayer(CreativeParkour.getPlugin(), playerToShow);
	}

	static private boolean isVanished(Player player) {
		for (MetadataValue meta : player.getMetadata("vanished")) {
			if (meta.asBoolean()) return true;
		}
		return false;
	}
}
