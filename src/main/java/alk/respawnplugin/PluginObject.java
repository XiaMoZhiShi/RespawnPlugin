package alk.respawnplugin;

import org.apache.logging.log4j.Logger;
import org.bukkit.configuration.file.FileConfiguration;

/**
 * 在这里存放一些会被经常调用的东西
 */
public abstract class PluginObject {
    protected Logger Logger;
    protected RespawnPlugin Plugin;
    protected FileConfiguration Config;

    protected PluginObject()
    {
        Plugin = RespawnPlugin.GetInstance();

        Logger = Plugin.getLog4JLogger();
        Config = Plugin.getConfig();
    }
}
