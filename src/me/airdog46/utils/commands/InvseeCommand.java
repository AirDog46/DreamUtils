package me.airdog46.utils.commands;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import me.airdog46.utils.MainUtils;
import net.md_5.bungee.api.ChatColor;
import net.milkbowl.vault.permission.Permission;

public class InvseeCommand implements CommandExecutor {
	FileConfiguration config = MainUtils.plugin.getConfig();
	Permission perms = MainUtils.perms;
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(perms.has(sender, "utils.invsee")) || !(sender instanceof Player)) {
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("NoPermission")));
			return true;
		}
		if (args.length < 1) {
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("InvseeUsage").replaceAll("%command%", label)));
			return true;
		}
		Player invseed = Bukkit.getServer().getPlayer(args[0]);
		if (invseed == null) {
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("InvseeUsage")));
			return true;
		}
		((Player) sender).openInventory(invseed.getInventory());
		if (args.length > 1) {
			if (args[1].equalsIgnoreCase("-i")) {
				sender.sendMessage(ChatColor.YELLOW.toString() + args[0] + "'s health: " + ChatColor.RESET.toString() + invseed.getHealth());
				sender.sendMessage(ChatColor.YELLOW.toString() + args[0] + "'s hunger: " + ChatColor.RESET.toString() + invseed.getSaturation());
				List<Object> potionEffectsList = Arrays.asList(invseed.getActivePotionEffects().stream().toArray());
				sender.sendMessage(ChatColor.YELLOW.toString() + args[0] + "'s effects: " + ChatColor.RESET.toString() + potionEffectsList.toString());
			}
		}
		return true;
	}

}
