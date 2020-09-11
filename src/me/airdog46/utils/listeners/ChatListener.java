package me.airdog46.utils.listeners;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import me.airdog46.utils.MainUtils;
import net.md_5.bungee.api.ChatColor;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.permission.Permission;

public class ChatListener implements Listener {
	HashMap<Player, Boolean> frozenPlayer = MainUtils.frozen;
	FileConfiguration config = MainUtils.plugin.getConfig();
	Chat chat = MainUtils.chat;
	Permission perms = MainUtils.perms;
	
	@EventHandler
	public void onChatEvent(AsyncPlayerChatEvent f) {
	if (f.getMessage().startsWith(config.getString("StaffChatPrefix")) && perms.has(f.getPlayer(), "utils.staffchat")) {
		MainUtils.staffBroadcast(ChatColor.translateAlternateColorCodes('&', config.getString("StaffChatFormat").replaceAll("%player%", MainUtils.getPrefixedName(f.getPlayer())).replaceAll("%message%", f.getMessage().replaceFirst(config.getString("StaffChatPrefix"), ""))));
		f.setCancelled(true);
	}
	if (f.getMessage().startsWith(config.getString("AdminChatPrefix")) && perms.has(f.getPlayer(), "utils.adminchat")) {
		MainUtils.staffBroadcast(ChatColor.translateAlternateColorCodes('&', config.getString("AdminChatFormat").replaceAll("%player%", MainUtils.getPrefixedName(f.getPlayer())).replaceAll("%message%", f.getMessage().replaceFirst(config.getString("AdminChatPrefix"), ""))));
		f.setCancelled(true);
	}
	if (frozenPlayer.get(f.getPlayer()) != null) {
			Bukkit.broadcast(ChatColor.translateAlternateColorCodes('&', config.getString("FrozenPrefix")) + " " + ChatColor.translateAlternateColorCodes('&', chat.getPlayerPrefix(f.getPlayer())) + f.getPlayer().getName() + ChatColor.RESET.toString() + ": " + f.getMessage(), "utils.staff.notify");
			f.setCancelled(true);
		}
	}
}