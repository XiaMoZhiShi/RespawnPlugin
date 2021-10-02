package alk.respawnplugin;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class RespawnPlugin extends JavaPlugin {

    public RespawnPlugin()
    {
        //设置pl为this
        pl = this;
    }

    @Override
    public void onEnable() {
        //输出启动信息
        getLogger().info("Starting Respawn Rules Plugin");

        //保存默认设置
        saveDefaultConfig();

        //注册Listener
        Bukkit.getPluginManager().registerEvents(new EventListener(), this);
    }

    private static RespawnPlugin pl;
    public static RespawnPlugin GetInstance() { return pl; }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("Shutting down Respawn Rules!");
    }
}
