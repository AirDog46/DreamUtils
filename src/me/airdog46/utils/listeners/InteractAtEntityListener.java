package me.airdog46.utils.listeners;

import java.util.HashMap;

import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import me.airdog46.utils.MainUtils;

public class InteractAtEntityListener implements Listener {
	HashMap<Player, Boolean> staffModePlayer = MainUtils.staffmode;
	
    @EventHandler
    public void onPlayerInteractAtEntity(PlayerInteractEntityEvent event) {
    	if (staffModePlayer.get(event.getPlayer()) != null) {
    			if (event.getRightClicked() instanceof Player) {
    				switch (event.getPlayer().getInventory().getHeldItemSlot()) {
    					case 6: 
    						event.getPlayer().openInventory(((HumanEntity) event.getRightClicked()).getInventory());
    						event.setCancelled(true);
    						break;
    					case 8:
    						event.getPlayer().chat("/freeze " + event.getRightClicked().getName());
    						event.setCancelled(true);
    						break;
    				}
    			}
    		}
    	}
    }