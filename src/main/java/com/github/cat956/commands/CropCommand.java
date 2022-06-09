package com.github.cat956.commands;

import java.io.File;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.cat956.main.Main;
import com.github.cat956.utils.ConfigUtil;

public class CropCommand implements CommandExecutor {
	public static String header = (Object)ChatColor.GOLD + "[" + (Object)ChatColor.AQUA + "LOTC Crops" + (Object)ChatColor.GOLD + "]" + ChatColor.AQUA;
	
	public boolean onCommand(final CommandSender commandSender, final Command command, final String s, final String[] args) {
        if (!(commandSender instanceof Player)) return true;

        final Player player = (Player) commandSender;
        
        // Player trample toggle
        if (args.length == 0) {
	        toggleTrample(player);
        } else if (Bukkit.getPlayer(args[0]) != null) {
        	// Moderator trample toggle
        	if (player.hasPermission("lotc.trample")) {
	        	Player target = Bukkit.getPlayer(args[0]);
	        	toggleTrample(target);
	        	player.sendMessage(header + ChatColor.AQUA + " Trample off for " + args[0]);
        	} else {
        		player.sendMessage(header + ChatColor.RED + " You don't have permission for that command.");
        	}
        } else {
        	player.sendMessage(header + ChatColor.RED + args[0] + " is offline or is not a player.");
        }

        return true;
    }
	
	// Toggle crop trample for player
	private void toggleTrample(Player player) {
		ConfigUtil playerData = new ConfigUtil(Main.plugin, File.separator + "data" + File.separator + player.getUniqueId().toString());
		if (Main.playerTrample.containsKey(player)) {
        	if (Main.playerTrample.get(player)) {
        		Main.playerTrample.put(player, false);
        		playerData.set("trample", false);
        		player.sendMessage(header + ChatColor.AQUA + " Trample off.");
        	} else {
        		Main.playerTrample.put(player, true);
        		playerData.set("trample", true);
        		player.sendMessage(header + ChatColor.AQUA + " Trample on.");
        	}
        } else {
        	Main.playerTrample.put(player, true);
        	playerData.set("trample", true);
        	player.sendMessage(header + ChatColor.AQUA + " Trample on.");
        }
	}
}
