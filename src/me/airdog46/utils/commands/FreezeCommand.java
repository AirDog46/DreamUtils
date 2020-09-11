package me.airdog46.utils.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import me.airdog46.utils.MainUtils;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.permission.Permission;

public class FreezeCommand implements CommandExecutor {
	FileConfiguration config = MainUtils.plugin.getConfig();
	Permission perms = MainUtils.perms;
	Chat chat = MainUtils.chat;

	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		// TODO Auto-generated method stub
	//	System.out.println(arg3.length);
		if (!(perms.has(arg0, "utils.freeze"))) {
			arg0.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("NoPermission")));
			return true;
		}
		if (arg3.length == 0) {
			arg0.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("FreezeCmdUsage").replaceAll("%command%", arg2)));
			return true;
		}
		Player frozenPlayer = Bukkit.getServer().getPlayer(arg3[0]);
		if (frozenPlayer == null) {
			arg0.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("FreezeCmdUsage").replaceAll("%command%", arg2)));
			return true;
		}
		if (MainUtils.frozen.get(frozenPlayer) == null) {
			arg0.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("FreezeSuccess")).replaceAll("%player%", MainUtils.getColoredName(frozenPlayer)));
			frozenPlayer.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("FrozenMessage")));
			MainUtils.staffBroadcast(ChatColor.translateAlternateColorCodes('&', config.getString("FrozenAlert"))
					.replaceAll("%frozen%", MainUtils.getColoredName(frozenPlayer))
					.replaceAll("%freezer%", (arg0 instanceof Player) ? MainUtils.getColoredName((Player) arg0) : arg0.getName()));
			MainUtils.frozen.put(frozenPlayer, true);
			return true;
		} else if (MainUtils.frozen.get(frozenPlayer) == true) {
			arg0.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("UnFreezeSuccess")).replaceAll("%player%", MainUtils.getColoredName(frozenPlayer)));
			MainUtils.staffBroadcast(ChatColor.translateAlternateColorCodes('&', config.getString("UnFrozenAlert"))
					.replaceAll("%frozen%", MainUtils.getColoredName(frozenPlayer))
					.replaceAll("%freezer%", (arg0 instanceof Player) ? MainUtils.getColoredName((Player) arg0) : arg0.getName()));
			MainUtils.frozen.remove(frozenPlayer);
			return true;
		}
		return false;
	}
}
