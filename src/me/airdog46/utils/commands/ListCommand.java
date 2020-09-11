package me.airdog46.utils.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import me.airdog46.utils.MainUtils;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.group.Group;
import net.md_5.bungee.api.ChatColor;
import net.milkbowl.vault.permission.Permission;

public class ListCommand implements CommandExecutor {
	FileConfiguration config = MainUtils.plugin.getConfig();
	LuckPerms api = MainUtils.api;
	Permission perms = MainUtils.perms;

	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		int rankAmount = api.getGroupManager().getLoadedGroups().size();
		StringBuilder ranks = new StringBuilder(rankAmount);
		for (Group group : api.getGroupManager().getLoadedGroups()) {
			if (group.getDisplayName() != null) {
				ranks.append(ChatColor.translateAlternateColorCodes('&', group.getDisplayName()));
				ranks.append(ChatColor.WHITE + (api.getGroupManager().getLoadedGroups().size() - 1 == api.getGroupManager().getLoadedGroups().size() ? "" : ", "));
			}
		}
		
		StringBuilder players = new StringBuilder(Bukkit.getOnlinePlayers().size());
		int onlineCount = Bukkit.getServer().getOnlinePlayers().size();
		for (Player player : Bukkit.getServer().getOnlinePlayers()) {
			players.append(MainUtils.getColoredName(player));
			onlineCount--;
			if (onlineCount != 0) {
				players.append(ChatColor.WHITE + ", ");
			} else {
				players.append(ChatColor.WHITE + ".");
			}

		}
		arg0.sendMessage(ranks.toString());
		arg0.sendMessage("(" + Bukkit.getOnlinePlayers().size() + "/" + Bukkit.getMaxPlayers() + "): " + players.toString());
		return true;
	}

}
