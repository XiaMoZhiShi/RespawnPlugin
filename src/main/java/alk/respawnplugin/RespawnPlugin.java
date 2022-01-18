package alk.respawnplugin;

import org.apache.logging.log4j.Logger;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.geysermc.floodgate.api.FloodgateApi;

import java.io.File;

public final class RespawnPlugin extends JavaPlugin {
    /**
     * 插件本体的实例
     */
    private static RespawnPlugin instance;

    /**
     * 获取插件实例，用于初始化PluginObject
     * @return 插件实例
     */
    public static RespawnPlugin GetInstance() { return instance; }

    /**
     * 获取Floodgate接口，用于初始化PluginObject
     * @return FloodgateApi
     */
    public FloodgateApi GetFloodgateAPI() { return floodgate; }

    /**
     * 数据目录
     */
    public File DataFolder;

    /**
     * 配置文件
     */
    public File ConfigFile;

    private final Logger logger;
    private final PluginEventListener listener;
    private static FloodgateApi floodgate;

    public RespawnPlugin()
    {
        //设置 instance，确保instance在被调用前不会是null
        instance = this;

        //在这行下面初始化依赖
        logger = this.getLog4JLogger();
        DataFolder = this.getDataFolder();
        floodgate = FloodgateApi.getInstance();
        ConfigFile = new File(DataFolder, "data.yml");

        //在这行下面初始化功能
        listener = new PluginEventListener();
    }

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

        //注册 respawnset 指令
        if (Bukkit.getPluginCommand(CommandRespawnset.CommandName) != null) {
            Bukkit.getPluginCommand(CommandRespawnset.CommandName).setExecutor(new CommandRespawnset());
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        logger.info("Shutting down Respawn Rules!");
    }
}
