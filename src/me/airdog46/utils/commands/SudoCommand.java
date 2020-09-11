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

public class SudoCommand implements CommandExecutor {
	FileConfiguration config = MainUtils.plugin.getConfig();
	Permission perms = MainUtils.perms;

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] arg) {
		if (!(perms.has(sender, "utils.sudo"))) {
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("NoPermission")));
			return true;
		}
		if (arg.length < 2) {
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("SudoUsage").replaceAll("%command%", label)));
			return true;
		}
		Player victim = Bukkit.getPlayer(arg[0]);
		if (victim.hasPermission("utils.sudo.bypass.exempt")) {
			sender.sendMessage(ChatColor.RED + "You are not allowed to sudo this player");
			return true;
		}
		String message = "";
		for (int i = 1; i < arg.length; i++) {
			message = message + arg[i] + " ";
		}
	//	System.out.println(message);
		if (victim != null) {
			victim.chat(message.trim());
		}
		return true;
	}
}
