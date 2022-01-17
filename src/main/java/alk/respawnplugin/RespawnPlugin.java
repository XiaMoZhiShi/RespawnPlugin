package alk.respawnplugin;

import org.apache.logging.log4j.Logger;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import java.io.File;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class RespawnPlugin extends JavaPlugin {
    /**
     * 插件本体的实例
     */
    private static RespawnPlugin instance;

    /**
     * 获取插件实例
     * @return 插件实例
     */
    public static RespawnPlugin GetInstance() { return instance; }

    /**
     * 数据目录
     */
    public File DataFolder;

    /**
     * 配置文件
     */
    public File ConfigFile;

    /**
     * 一个 玩家-玩家 词典，用来记录玩家请求
     * Key: 发起玩家
     * Value: 目标玩家
     */
    //Lin: Dictionary<Player, Player>?
    public final Map<Player, Player> RequestCommandPlayerMap = new ConcurrentHashMap<>();

    public RespawnPlugin()
    {
        //设置 instance，确保instance在被调用前不会是null
        instance = this;

        logger = this.getLog4JLogger();
    }

    private Logger logger;
    private PluginEventListener listener = new PluginEventListener();

    @Override
    public void onEnable() {
        //输出启动信息
        logger.info("Starting Respawn Rules Plugin");

        //保存默认设置
        saveDefaultConfig();

        //创建配置文件
        saveResource("data.yml", false);

        //注册Listener
        Bukkit.getPluginManager().registerEvents(listener, this);
        DataFolder = this.getDataFolder();
        ConfigFile = new File(DataFolder, "data.yml");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        logger.info("Shutting down Respawn Rules!");
    }
}
