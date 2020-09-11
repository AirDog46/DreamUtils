package me.airdog46.utils.listeners;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import me.airdog46.utils.MainUtils;
import net.md_5.bungee.api.ChatColor;
import net.milkbowl.vault.permission.Permission;

public class LeaveListener implements Listener {
	FileConfiguration config = MainUtils.plugin.getConfig();
	HashMap<Player, Boolean> frozenPlayer = MainUtils.frozen;
	HashMap<Player, Boolean> mode = MainUtils.staffmode;
	HashMap<Player, ItemStack[]> previousInv = MainUtils.previousInventory;
	HashMap<Player, Player> messagePlayer = MainUtils.messagePlayer;
	Permission perms = MainUtils.perms;
	
	@EventHandler
	public void onLeaveEvent(PlayerQuitEvent h) {
		if (perms.has(h.getPlayer(), "utils.staff")) {
			MainUtils.staffBroadcast(ChatColor.translateAlternateColorCodes('&', config.getString("StaffLeaveMessage").replaceAll("%player%", MainUtils.getColoredName(h.getPlayer()))));
		}
		h.setQuitMessage("");
		if (frozenPlayer.get(h.getPlayer()) != null) {
			for (Player player : Bukkit.getOnlinePlayers()) {
				if (player.hasPermission("utils.freeze")) {
					player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("LeftWhileFrozen").replaceAll("%player%", h.getPlayer().getName())));
				}
			}
			frozenPlayer.remove(h.getPlayer());
		}
		if (mode.get(h.getPlayer()) != null) {
			h.getPlayer().getInventory().clear();
			h.getPlayer().getInventory().setContents(previousInv.get(h.getPlayer()));
			mode.remove(h.getPlayer());
		}
		if (messagePlayer.get(h.getPlayer()) != null) {
			messagePlayer.remove(h.getPlayer());
		}
	}
}