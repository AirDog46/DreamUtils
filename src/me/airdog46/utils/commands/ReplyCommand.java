package me.airdog46.utils.commands;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import me.airdog46.utils.MainUtils;

public class ReplyCommand implements CommandExecutor {
	FileConfiguration config = MainUtils.plugin.getConfig();
	HashMap<Player, Player> messagePlayer = MainUtils.messagePlayer;
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("NoPermission")));
			return true;
		}
		switch (args.length) {
		case 0:
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("ReplyUsage")));
			return true;
		default:
			Player messaged = messagePlayer.get(Bukkit.getServer().getPlayer(args[0]));
			if (messaged == null) {
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("NoOneToReply")));
				return true;
			}
			String message = "";
			for (int i = 0; i < args.length; i++) {
					message = message + args[i] + " ";
			}
			messaged.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("FromMessage").replaceAll("%player%", MainUtils.getColoredName((Player) sender)).replaceAll("%message%", message)));
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("ToMessage").replaceAll("%player%", MainUtils.getColoredName(messaged)).replaceAll("%message%", message)));
			return true;
		}
	}
}
