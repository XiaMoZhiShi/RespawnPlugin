package alk.respawnplugin;

import org.bukkit.configuration.file.FileConfiguration;

import java.util.logging.Logger;

/**
 * 在这里存放一些会被经常调用的东西
 */
public abstract class PluginObject {
    protected Logger Logger;
    protected RespawnPlugin Plugin;
    protected FileConfiguration Config;

    protected PluginObject()
    {
        Logger = Utils.GetLogger();
        Plugin = RespawnPlugin.GetInstance();
        Config = Plugin.getConfig();
    }
}
