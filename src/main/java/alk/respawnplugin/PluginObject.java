package alk.respawnplugin;

import org.apache.logging.log4j.Logger;
import org.bukkit.configuration.file.FileConfiguration;
import org.geysermc.floodgate.api.FloodgateApi;

/**
 * 在这里存放一些会在所有插件对象中被经常调用的东西
 */
public abstract class PluginObject {
    protected FloodgateApi Floodgate;
    protected Logger Logger;
    protected RespawnPlugin Plugin;
    protected FileConfiguration Config;

    protected PluginObject()
    {
        Plugin = RespawnPlugin.GetInstance();

        Logger = Plugin.getLog4JLogger();
        Config = Plugin.getConfig();
        Floodgate = Plugin.GetFloodgateAPI();
    }
}
