package me.airdog46.utils.commands;

import java.util.HashMap;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import me.airdog46.utils.MainUtils;
import net.md_5.bungee.api.ChatColor;
import net.milkbowl.vault.permission.Permission;

public class StaffModeCommand implements CommandExecutor {
	FileConfiguration config = MainUtils.plugin.getConfig();
	HashMap<Player, Boolean> mode = MainUtils.staffmode;
	HashMap<Player, ItemStack[]> previousInv = MainUtils.previousInventory;
	Permission perms = MainUtils.perms;
	
	public void setStaffInv(Player player) {
		ItemStack compass = new ItemStack(Material.COMPASS);
		ItemStack playerHead = new ItemStack(Material.SKULL_ITEM);
		ItemStack randomPlayer = new ItemStack(Material.INK_SACK, 1, (short) 12);
		ItemStack betterView = new ItemStack(Material.CARPET, 1,(short) 1);
		ItemStack worldEditWand = new ItemStack(Material.CHEST, 1);
		ItemStack vanish = new ItemStack(Material.INK_SACK, 1,(short) 10);
		ItemStack freezePlayer = new ItemStack(Material.INK_SACK);
    
    
		ItemMeta cM = compass.getItemMeta();
		ItemMeta pHM = playerHead.getItemMeta();
		ItemMeta rPM = randomPlayer.getItemMeta();
		ItemMeta bVM = betterView.getItemMeta();
		ItemMeta wEWM = worldEditWand.getItemMeta();
    	ItemMeta vM = vanish.getItemMeta();
    	ItemMeta fPM = freezePlayer.getItemMeta();
    
    	SkullMeta skullMeta = (SkullMeta) playerHead.getItemMeta();

    	cM.setDisplayName(ChatColor.AQUA + "WorldEdit Compass");
    	pHM.setDisplayName(ChatColor.AQUA + "Online Staff");
    	skullMeta.setOwner(player.getName());
    	skullMeta.setDisplayName(ChatColor.AQUA + "Online staff");
    	rPM.setDisplayName(ChatColor.AQUA + "Random Player");
    	bVM.setDisplayName(ChatColor.AQUA + "Better View");
    	wEWM.setDisplayName(ChatColor.AQUA + "Invsee Player");
    	vM.setDisplayName(ChatColor.AQUA + "Vanish");
    	fPM.setDisplayName(ChatColor.AQUA + "Freeze Player");
    
    	playerHead.setItemMeta(pHM);
    	compass.setItemMeta(cM);
    	playerHead.setItemMeta(skullMeta);
    	randomPlayer.setItemMeta(rPM);
    	betterView.setItemMeta(bVM);
    	worldEditWand.setItemMeta(wEWM);
    	vanish.setItemMeta(vM);
    	freezePlayer.setItemMeta(fPM);
    
    	player.getInventory().setItem(0, compass);
    	player.getInventory().setItem(1, playerHead);
    	player.getInventory().setItem(2, randomPlayer);
    	player.getInventory().setItem(4, betterView);
    	player.getInventory().setItem(6, worldEditWand);
    	player.getInventory().setItem(7, vanish);
    	player.getInventory().setItem(8, freezePlayer);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(perms.has(sender, "utils.staffmode")) || !(sender instanceof Player)) {
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("NoPermission")));
			return true;
		}
		if (mode.get(sender) == null) {
			previousInv.put(((Player) sender), ((Player) sender).getInventory().getContents());
			((Player) sender).getInventory().clear();
			((Player) sender).setGameMode(GameMode.CREATIVE);
			this.setStaffInv((Player) sender);
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("StaffModeEnableSelf")));
			MainUtils.staffBroadcast(ChatColor.translateAlternateColorCodes('&', config.getString("StaffModeEnableOthers")).replaceAll("%player%", sender.getName()));
			mode.put((Player) sender, true);
			return true;
		} else if (mode.put((Player) sender, true)) {
			((Player) sender).getInventory().clear();
			((Player) sender).getInventory().setContents(previousInv.get((Player) sender));
			((Player) sender).setGameMode(GameMode.SURVIVAL);
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("StaffModeDisableSelf")));
			MainUtils.staffBroadcast(ChatColor.translateAlternateColorCodes('&', config.getString("StaffModeDisableOthers")).replaceAll("%player%", sender.getName()));
			mode.remove((Player) sender);
			return true;
		}
		return false;
	}

}
