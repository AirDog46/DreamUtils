package me.airdog46.utils.listeners;

import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCreativeEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;

import me.airdog46.utils.MainUtils;

public class InventoryListener implements Listener {
	static HashMap<Player, Boolean> mode = MainUtils.staffmode;
	
	@EventHandler
	public static void onCreativeEvent(InventoryCreativeEvent e) {
		if (mode.get((Player) e.getWhoClicked()) != null) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public static void onDragEvent(InventoryDragEvent e) {
		if (mode.get((Player) e.getWhoClicked() != null)) {
				e.setCancelled(true);
		}
	}
	
	@EventHandler
	public static void onInteractEvent(InventoryInteractEvent e) {
		if (mode.get((Player) e.getWhoClicked() != null)) {
				e.setCancelled(true);
		}
	}
	
	@EventHandler
	public static void onClickEvent(InventoryClickEvent e) {
		if (mode.get((Player) e.getWhoClicked()) != null) {
			e.setCancelled(true);
		}
	}
}