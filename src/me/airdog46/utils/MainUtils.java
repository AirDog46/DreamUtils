package me.airdog46.utils;

import java.util.HashMap;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import me.airdog46.utils.commands.AdminChatCommand;
import me.airdog46.utils.commands.FlyCommand;
import me.airdog46.utils.commands.FreezeCommand;
import me.airdog46.utils.commands.InvseeCommand;
import me.airdog46.utils.commands.ListCommand;
import me.airdog46.utils.commands.MessageCommand;
import me.airdog46.utils.commands.PingCommand;
import me.airdog46.utils.commands.ReplyCommand;
import me.airdog46.utils.commands.SetMaxPlayersCommand;
import me.airdog46.utils.commands.StaffChatCommand;
import me.airdog46.utils.commands.StaffModeCommand;
import me.airdog46.utils.commands.SudoCommand;
import me.airdog46.utils.commands.TeleportAllCommand;
import me.airdog46.utils.commands.TeleportCommand;
import me.airdog46.utils.commands.TeleportHereCommand;
import me.airdog46.utils.listeners.*;
import net.luckperms.api.LuckPerms;
import net.md_5.bungee.api.ChatColor;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.permission.Permission;

public class MainUtils extends JavaPlugin {
	
	public static MainUtils plugin;
	public static HashMap<Player, Boolean> frozen = new HashMap<>();
	public static HashMap<Player, Boolean> staffmode = new HashMap<>();
	public static HashMap<Player, ItemStack[]> previousInventory = new HashMap<>();
	public static HashMap<Player, Player> messagePlayer = new HashMap<>();
    public static Permission perms = null;
    public static Chat chat = null;
    public static LuckPerms api = null;
	
	private void enableListeners() {
		this.getServer().getPluginManager().registerEvents(new UtilsListener(), this);
		this.getServer().getPluginManager().registerEvents(new ChatListener(), this);
		this.getServer().getPluginManager().registerEvents(new LeaveListener(), this);
		this.getServer().getPluginManager().registerEvents(new CommandListener(), this);
		this.getServer().getPluginManager().registerEvents(new InventoryListener(), this);
		this.getServer().getPluginManager().registerEvents(new InteractAtEntityListener(), this);
	}
	
	private void enableCommands() {
		this.getCommand("freeze").setExecutor(new FreezeCommand());
		this.getCommand("staffmode").setExecutor(new StaffModeCommand());
		this.getCommand("sudo").setExecutor(new SudoCommand());
		this.getCommand("setmaxplayers").setExecutor(new SetMaxPlayersCommand());
		this.getCommand("ping").setExecutor(new PingCommand());
		this.getCommand("invsee").setExecutor(new InvseeCommand());
		this.getCommand("teleport").setExecutor(new TeleportCommand());
		this.getCommand("teleporthere").setExecutor(new TeleportHereCommand());
		this.getCommand("teleportall").setExecutor(new TeleportAllCommand());
		this.getCommand("fly").setExecutor(new FlyCommand());
		this.getCommand("msg").setExecutor(new MessageCommand());
		this.getCommand("list").setExecutor(new ListCommand());
		this.getCommand("r").setExecutor(new ReplyCommand());
		this.getCommand("staffchat").setExecutor(new StaffChatCommand());
		this.getCommand("adminchat").setExecutor(new AdminChatCommand());
	}
	
	
    private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
        perms = rsp.getProvider();
        return perms != null;
    }
    
    private boolean setupLuckPerms() {
    	RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
    	if (provider != null) {
    	    api = provider.getProvider();
    	    return true;
    	}
    	return false;
    }
    
    private boolean setupChat() {
    	RegisteredServiceProvider<Chat> rsp = getServer().getServicesManager().getRegistration(Chat.class);
    	chat = rsp.getProvider();
    	return chat != null;
    }
    
    public static boolean staffBroadcast(String s) {
    	Bukkit.broadcast(s, "utils.staff.notify");
    	Bukkit.getConsoleSender().sendMessage(s);
    	return true;
    }
    
    public static boolean adminBroadcast(String s) {
    	Bukkit.broadcast(s, "utils.admin.notify");
    	Bukkit.getConsoleSender().sendMessage(s);
    	return true;
    }
	
    public static String getPrefixedName(Player p) {
    	String prefix = chat.getPlayerPrefix(p);
    	String prefixedName = prefix + p.getName();
    	return prefixedName;
    }
    
    public static String getColoredName(Player p) {
    //	System.out.println(String.valueOf(MainUtils.getPrefixedName(p).charAt(4)));
    	switch (chat.getPlayerPrefix(p).length()) {
    	case 0:
    		return p.getName();
    	case 1: 
    		return p.getName();
    	case 2:
    		return ChatColor.getByChar(MainUtils.getPrefixedName(p).charAt(1)).toString() + p.getName();
    	case 3:
    		return ChatColor.getByChar(MainUtils.getPrefixedName(p).charAt(2)).toString() + p.getName();
    	default:
    		if (MainUtils.getPrefixedName(p).charAt(4) != '[') {
    			return ChatColor.getByChar(MainUtils.getPrefixedName(p).charAt(4)).toString() + p.getName();
    		} else {
    			return ChatColor.getByChar(MainUtils.getPrefixedName(p).charAt(6)).toString() + p.getName();
    		}
    	}
    }
    
	@Override
	public void onEnable() {
		this.saveDefaultConfig();
		if (this.setupPermissions() == false || this.setupChat() == false) {
			throw new NullPointerException("The Vault API hasn't initialized properly.");
		}
		if (this.setupLuckPerms() == false) {
			throw new NullPointerException("The LuckPerms API hasn't initialized properly.");
		}
		plugin = this;
		this.enableListeners();
		this.enableCommands();
	}
	

	@Override
	public void onDisable() {
		
	}
}
