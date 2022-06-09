package com.github.cat956.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import com.github.cat956.main.Main;

public class PlayerInteractListener implements Listener {
	@EventHandler
	public void onEntityInteract(PlayerInteractEvent event) {
		// Disable trampling
		if (event.getAction().equals(Action.PHYSICAL) && event.getClickedBlock().getType().equals(Material.FARMLAND)) {
			// Check if player has trampling off, if so cancel event.
			Player player = event.getPlayer();
			if (!Main.playerTrample.get(player)) {
				event.setCancelled(true);
			}
		}
	}
}
