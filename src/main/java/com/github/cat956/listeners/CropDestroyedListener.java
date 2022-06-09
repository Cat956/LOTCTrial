package com.github.cat956.listeners;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockPistonExtendEvent;

import com.destroystokyo.paper.event.block.BlockDestroyEvent;
import com.github.cat956.main.Main;

public class CropDestroyedListener implements Listener {
	// Stop water from destroying crops
	@EventHandler
	public void onWaterPassThrough(BlockFromToEvent event){
		Material mat = event.getToBlock().getType();
		if (Main.crops.containsKey(mat)) {
			event.setCancelled(true);
        }
	}
	
	// Stop pistons from destroying crops
	@EventHandler
	public static void onBlockPistonExtend(BlockPistonExtendEvent event) {
		 for (Block block : event.getBlocks()) {
			 if (Main.crops.containsKey(block.getType())) {
				 event.setCancelled(true);
			 }
		 }
	}
	
	// Stops other block destroy events (e.g soil broken)
	@EventHandler
	public static void onBlockDestroy(BlockDestroyEvent event) {
		if (Main.crops.containsKey(event.getBlock().getType())) {
			event.setCancelled(true);
		}
	}
}

