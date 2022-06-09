package com.github.cat956.listeners;

import java.io.File;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.github.cat956.main.Main;
import com.github.cat956.utils.ConfigUtil;

public class PlayerJoinListener implements Listener {
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		// Get playerdata pertaining to trampling from config
		Player player = e.getPlayer();
		ConfigUtil playerData = new ConfigUtil(Main.plugin, File.separator + "data" + File.separator + player.getUniqueId().toString());
		playerData.addDefault("trample", false);
		playerData.options().copyDefaults(true);
		playerData.save();

		Main.playerTrample.put(player, playerData.getBoolean("trample"));
	}
}
