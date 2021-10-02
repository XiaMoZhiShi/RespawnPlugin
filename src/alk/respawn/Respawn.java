package alk.respawn;


import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Respawn extends JavaPlugin {
    public static JavaPlugin instance;

    @Override
    public void onEnable() {
        getLogger().info("Starting Respawn Rules Plugin");
        instance = this;
        saveDefaultConfig();
        Bukkit.getPluginManager().registerEvents(new ConfigWriter(), this);
        Bukkit.getPluginManager().registerEvents(new RespawnEventProcessor(), this);
    }
}
