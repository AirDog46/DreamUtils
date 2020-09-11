package me.airdog46.utils.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import me.airdog46.utils.MainUtils;
import net.md_5.bungee.api.ChatColor;
import net.milkbowl.vault.permission.Permission;

public class TeleportHereCommand implements CommandExecutor {
	FileConfiguration config = MainUtils.plugin.getConfig();
	Permission perms = MainUtils.perms;

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(perms.has(sender, "utils.teleporthere")) || !(sender instanceof Player)) {
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("NoPermission")));
			return true;
		}
		if (args.length < 1) {
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("TeleportHereUsage").replaceAll("%command%", label)));
			return true;
		}
		Player p = Bukkit.getServer().getPlayer(args[0]);
		if (p == null) {
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("TeleportHereUsage").replaceAll("%command%", label)));
			return true;
		}
		p.teleport((Player) sender);
		sender.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("TeleportHereSelf").replaceAll("%player%", MainUtils.getColoredName(p))));
		MainUtils.adminBroadcast(ChatColor.translateAlternateColorCodes('&', config.getString("TeleportHereOthers").replaceAll("%player%", p.getName()).replaceAll("%sender%", ((Player) sender).getName())));
		return true;
	}

}
