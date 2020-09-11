package me.airdog46.utils.listeners;

import java.util.HashMap;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import me.airdog46.utils.MainUtils;
import net.md_5.bungee.api.ChatColor;

public class CommandListener implements Listener {
	HashMap<Player, Boolean> frozenPlayer = MainUtils.frozen;
	FileConfiguration config = MainUtils.plugin.getConfig();
	
	@EventHandler
	public void onCommandEvent(PlayerCommandPreprocessEvent g) {
	if (MainUtils.frozen.get(g.getPlayer()) != null) {
			if (!(g.getMessage().startsWith("/r"))) {
				g.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("FreezeCommandDeny")));
				g.setCancelled(true);
			}
		}
	}
}