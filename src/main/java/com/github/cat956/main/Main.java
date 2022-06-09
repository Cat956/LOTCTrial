package com.github.cat956.main;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.github.cat956.commands.CropCommand;
import com.github.cat956.listeners.BlockBreakListener;
import com.github.cat956.listeners.CropDestroyedListener;
import com.github.cat956.listeners.PlayerInteractListener;
import com.github.cat956.listeners.PlayerJoinListener;

public class Main extends JavaPlugin {
	public static Main plugin;
	public static HashMap<Player, Boolean> playerTrample = new HashMap<Player, Boolean>();
	
	// Crops affected by plugin. The material of the planted block is mapped to a list of its drops: seeds at index 0, crop at index 1.
	@SuppressWarnings("serial")
	public static HashMap<Material, List<Material>> crops = new HashMap<Material, List<Material>>() {{
		put(Material.WHEAT, Arrays.asList(Material.WHEAT_SEEDS, Material.WHEAT));
		put(Material.MELON_STEM, Arrays.asList(Material.MELON_SEEDS, null));
		put(Material.PUMPKIN_STEM, Arrays.asList(Material.PUMPKIN_SEEDS, null));
		put(Material.MELON, Arrays.asList(null, Material.MELON_SLICE));
		put(Material.PUMPKIN, Arrays.asList(null, Material.PUMPKIN));
		put(Material.BEETROOTS, Arrays.asList(Material.BEETROOT_SEEDS, Material.BEETROOT));
		put(Material.CARROTS, Arrays.asList(null, Material.CARROT));
		put(Material.POTATOES, Arrays.asList(null, Material.POTATO));
	}};
	
	@Override
    public void onEnable() {
		plugin = this;
		final PluginManager pluginManager = getServer().getPluginManager();
		this.getCommand("crop").setExecutor((CommandExecutor)new CropCommand());
		pluginManager.registerEvents(new BlockBreakListener(), this);
		pluginManager.registerEvents(new PlayerJoinListener(), this);
		pluginManager.registerEvents(new CropDestroyedListener(), this);
		pluginManager.registerEvents(new PlayerInteractListener(), this);
		getLogger().info("LOTC Trial activated.");
    }
    
    @Override
    public void onDisable() {
    	getLogger().info("LOTC Trial deactivated.");
    }
}