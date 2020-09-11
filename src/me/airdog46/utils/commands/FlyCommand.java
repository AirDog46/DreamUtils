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

public class FlyCommand implements CommandExecutor {
	FileConfiguration config = MainUtils.plugin.getConfig();
	Permission perms = MainUtils.perms;

	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		if (!(perms.has(arg0, "utils.fly"))) {
			arg0.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("NoPermission")));
			return true;
		}
		
		if (!(arg0 instanceof Player) && arg3.length < 1) {
			arg0.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("FlyUsage").replaceAll("%command", arg2)));
			return true;
		}
		
		if (arg3.length < 1) {
			if (((Player) arg0).getAllowFlight()) {
				((Player) arg0).setAllowFlight(false);
				arg0.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("FlyDisabledSelf")));
				return true;
			} else {
				((Player) arg0).setAllowFlight(true);
				arg0.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("FlyEnabledSelf")));
				return true;
			}
		}
		Player player2 = Bukkit.getServer().getPlayer(arg3[0]);
		if (perms.has(arg0, "utils.fly.others")) {
			if (player2 != null) {
				if (player2.getAllowFlight()) {
					player2.setAllowFlight(false);
					arg0.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("FlyDisabledOther").replaceAll("%player%", MainUtils.getColoredName(player2))));
					return true;
				} else {
					player2.setAllowFlight(true);
					arg0.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("FlyEnabledOther").replaceAll("%player%", MainUtils.getColoredName(player2))));
					return true;
				}
			} else {
				arg0.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("FlyUsage").replaceAll("%command%", arg2)));
				return true;
			}
		}
		// TODO Auto-generated method stub
		return false;
	}

}
