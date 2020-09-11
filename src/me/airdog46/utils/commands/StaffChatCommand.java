package me.airdog46.utils.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import me.airdog46.utils.MainUtils;
import net.milkbowl.vault.permission.Permission;

public class StaffChatCommand implements CommandExecutor {
	Permission perms = MainUtils.perms;
	FileConfiguration config = MainUtils.plugin.getConfig();

	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		if (!(perms.has(arg0, "utils.staffchat") || arg0 instanceof Player)) {
			arg0.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("NoPermission")));
			return true;
		}
		switch (arg3.length) {
		case 0:
			arg0.sendMessage(ChatColor.translateAlternateColorCodes('&',config.getString("StaffChatUsage")));
			return true;
	default:
		String message = "";
		for (int i = 0; i < arg3.length; i++) {
			message = message + arg3[i] + " ";
		}
		MainUtils.staffBroadcast(ChatColor.translateAlternateColorCodes('&', config.getString("StaffChatFormat").replaceAll("%player%", MainUtils.getPrefixedName(((Player) arg0).getPlayer())).replaceAll("%message%", message)));
		return true;
		}
	}
}
