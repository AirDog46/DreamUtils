package me.airdog46.utils.commands;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import me.airdog46.utils.MainUtils;
import me.airdog46.utils.otherstuff.BukkitReflection;
import net.md_5.bungee.api.ChatColor;
import net.milkbowl.vault.permission.Permission;

public class SetMaxPlayersCommand implements CommandExecutor {
	FileConfiguration config = MainUtils.plugin.getConfig();
	Permission perms = MainUtils.perms;

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(perms.has(sender, "utils.setmaxplayers"))) {
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("NoPermission")));
			return true;
		}
		if (args.length < 1) {
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("SetPlayersUsage").replaceAll("%command%", label)));
			return true;
		}
		Server server = Bukkit.getServer();
		try {
		BukkitReflection.setMaxPlayers(server, Integer.parseInt(args[0]));
		} catch (NumberFormatException e) {
			sender.sendMessage(ChatColor.RED + "Please insert a valid integer.");
			return true;
		}
		String slotschange = ChatColor.translateAlternateColorCodes('&', config.getString("MaxPlayersSet")).replaceAll("%slots%", args[0]);
		sender.sendMessage(slotschange);
		MainUtils.adminBroadcast(ChatColor.GRAY.toString() + ChatColor.ITALIC.toString() + "[" + sender.getName() + ": " + slotschange + ChatColor.GRAY.toString() + ChatColor.ITALIC.toString() + "]");
		// TODO Demote StarZorrow for being a black kid
		return true;
	}
	
}
