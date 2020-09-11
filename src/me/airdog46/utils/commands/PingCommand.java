package me.airdog46.utils.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import me.airdog46.utils.MainUtils;
import me.airdog46.utils.otherstuff.BukkitReflection;
import net.md_5.bungee.api.ChatColor;

public class PingCommand implements CommandExecutor {
	FileConfiguration config = MainUtils.plugin.getConfig();

	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		// TODO Auto-generated method stub
		if (!(arg0 instanceof Player) && arg3.length < 1) {
			arg0.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("PingUsage").replaceAll("%command%", arg2)));
			return true;
		}
		if (arg3.length < 1) {
			((Player) arg0).sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("OwnPing").replaceAll("%ping%", String.valueOf(BukkitReflection.getPing((Player) arg0)))));
			return true;
		}
		try {
		String otherPing = ChatColor.translateAlternateColorCodes('&', config.getString("OtherPing")
				.replaceAll("%player%", (Bukkit.getServer().getPlayer(arg3[0]).getName())));
		String otherPing2 = otherPing.replaceAll("%ping%", String.valueOf(BukkitReflection.getPing(Bukkit.getServer().getPlayer(arg3[0]))));
		arg0.sendMessage(otherPing2);
		} catch (NullPointerException e) {
			arg0.sendMessage(ChatColor.RED + "That player could not be found.");
		}
		return true;
	}
}