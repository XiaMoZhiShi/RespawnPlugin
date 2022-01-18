package alk.respawnplugin;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.apache.logging.log4j.Logger;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;

/**
 * 在这里存放一些会被经常调用的东西
 */
public abstract class PluginObject {
    protected Logger Logger;
    protected RespawnPlugin Plugin;
    protected FileConfiguration Config;
    protected @NotNull TextComponent titleHealthRunOut;
    protected @NotNull TextComponent titleHealthRemaining;
    protected @NotNull TextComponent titleRespawn;
    protected int RemainingHealth;

    protected PluginObject()
    {
        Plugin = RespawnPlugin.GetInstance();

        Logger = Plugin.getLog4JLogger();
        Config = Plugin.getConfig();

        titleHealthRunOut = Component.text("\uE45A 你的死亡回归加护已被耗尽，请等待下一轮回开始");
        titleRespawn = Component.text("\uE426 死亡回归发动 \uE426");
    }
}
