package alk.respawnplugin;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class RespawnPlugin extends JavaPlugin {

    public static JavaPlugin instance;

    public RespawnPlugin()
    {
        //设置pl为this
        pl = this;
    }

    private static RespawnPlugin pl;
    public static RespawnPlugin GetInstance() { return pl; }

    /**
     * 一个 玩家-玩家 词典，用来记录玩家请求
     * Key: 发起玩家
     * Value: 目标玩家
     */
    //Lin: Dictionary<Player, Player>?
    public final Map<Player, Player> RequestCommandPlayerMap = new ConcurrentHashMap<>();

    @Override
    public void onEnable() {
        //输出启动信息
        getLogger().info("Starting Respawn Rules Plugin");

        //保存默认设置
        saveDefaultConfig();

        //创建配置文件
        saveResource("data.yml", false);

        //注册Listener
        Bukkit.getPluginManager().registerEvents(new EventListener(), this);

        //设置 instance
        instance = this;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("Shutting down Respawn Rules!");
    }

}
