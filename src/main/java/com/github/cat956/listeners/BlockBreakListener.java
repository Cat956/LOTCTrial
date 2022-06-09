package com.github.cat956.listeners;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import com.github.cat956.main.Main;

public class BlockBreakListener implements Listener {
	
	// Player breaks a block
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		Block block = event.getBlock();
		Material blockMat = block.getType();
		if (Main.crops.containsKey(blockMat)) {
			event.setDropItems(false);
			
			// Seed drops are between 1 and 3, just like vanilla.
			int seedNum = 1 + (int)(Math.random() * 3);
			int cropNum;
			
			ItemStack tool = event.getPlayer().getInventory().getItemInMainHand();
			boolean isHoe = true;
			Material toolMat = tool.getType();
			
			// Normally I wouldn't use a switch statement for this since there are repeats, but the brief asked for one.
			// Sets crop nums for each tool.
			switch (toolMat) {
				case WOODEN_HOE:
					cropNum = 1;
					break;
				case STONE_HOE:
					cropNum = 1;
					break;
				case IRON_HOE:
					cropNum = 2;
					break;
				case GOLDEN_HOE:
					cropNum = 2;
					break;
				case DIAMOND_HOE:
					cropNum = 3;
					break;
				default:
					seedNum = 0;
					cropNum = 0;
					isHoe = false;
			}
			
			// If item is hoe, reduce durability and drop items.
			if (isHoe) {
				// By the wiki, 100/(level+1) % chance the item reduces durability.
				double unbreakingLevel = (double) tool.getEnchantmentLevel(Enchantment.DURABILITY);
				double durabilityChance = 1.0/(unbreakingLevel+1.0);
				Damageable dm = (Damageable) tool.getItemMeta();
				if (Math.random() < durabilityChance) {
					dm.setDamage(dm.getDamage()+1);
				}
				tool.setItemMeta((ItemMeta) dm);
				event.getPlayer().getInventory().setItemInMainHand(tool);
				
				// Again, fortune formula from the wiki
				double fortuneLevel = (double) tool.getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS);
				double dropMultiplier = (1.0/(fortuneLevel+2.0))+((fortuneLevel+1.0)/2.0);
				cropNum = (int) Math.round(cropNum*dropMultiplier);
				
				// Do not give crops and more seeds if plant is matured.
				if (block.getBlockData() instanceof Ageable) {
					Ageable ageable = (Ageable) block.getBlockData();
					if (ageable.getAge() < ageable.getMaximumAge()) {
						cropNum = 0;
						seedNum = 1;
					}
				}
			}
			
			// Drop crops and seeds
			if (Main.crops.get(blockMat).get(1) != null && cropNum > 0) {
				ItemStack cropItem = new ItemStack(Main.crops.get(blockMat).get(1), cropNum);
				event.getPlayer().getWorld().dropItem(block.getLocation(), cropItem);
			}
			if (Main.crops.get(blockMat).get(0) != null && seedNum > 0) {
				ItemStack seedItem = new ItemStack(Main.crops.get(blockMat).get(0), seedNum);
				event.getPlayer().getWorld().dropItem(block.getLocation(), seedItem);
			}
		}
	}
}
