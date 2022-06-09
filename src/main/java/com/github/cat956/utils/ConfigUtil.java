package com.github.cat956.utils;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class ConfigUtil extends YamlConfiguration {
    private JavaPlugin plugin;
    private String fileName;

    public ConfigUtil(JavaPlugin plugin, String fileName) {
        this.plugin = plugin;
        this.fileName = fileName + (fileName.endsWith(".yml") ? "" : ".yml");
        File file = new File(this.plugin.getDataFolder(), this.fileName);
        if (!file.exists()) {
            this.createFile();
        } else {
            this.loaddata();
        }
    }

    private boolean createFile() {
        try {
            File file = new File(this.plugin.getDataFolder(), this.fileName);
            if (!file.exists()) {
                if (this.plugin.getResource(this.fileName) != null) {
                    this.plugin.saveResource(this.fileName, false);
                } else {
                    this.save(file);
                }
            } else {
                this.load(file);
                this.save(file);
            }
            return true;
        } catch (Exception file) {
        	return false;
        }
    }

    public void save() {
        try {
            this.save(new File(this.plugin.getDataFolder(), this.fileName));
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    } 

    public File getFile() {
        return new File(this.plugin.getDataFolder(), this.fileName);
    }

    public static String get(Plugin plugin, String path) {
        return plugin.getConfig().getString(path);
    }

    public static boolean contains(Plugin plugin, String path) {
        return plugin.getConfig().contains(path);
    }

    public static FileConfiguration getConfig(Plugin plugin) {
        return plugin.getConfig();
    }

    private void loaddata() {
        try {
            File file = new File(this.plugin.getDataFolder(), this.fileName);
            this.load(file);
        }
        catch (IOException ex) {
            ex.printStackTrace();
        } catch (InvalidConfigurationException ex) {
            Logger.getLogger(ConfigUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}


