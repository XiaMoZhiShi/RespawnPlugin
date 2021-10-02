package alk.respawn;


import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.EventListener;

public class Respawn extends JavaPlugin {
    public static JavaPlugin instance;

    @Override
    public void onEnable() {
        getLogger().info("Starting Respawn Rules Plugin");
        instance = this;
        saveDefaultConfig();
        Bukkit.getPluginManager().registerEvents(new ConfigWriter(), this);
    }
}
