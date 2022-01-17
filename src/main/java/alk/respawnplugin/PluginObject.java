package alk.respawnplugin;

import net.kyori.adventure.text.Component;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.apache.logging.log4j.Logger;
import org.bukkit.configuration.file.FileConfiguration;

/**
 * 在这里存放一些会被经常调用的东西
 */
public abstract class PluginObject {
    protected Logger Logger;
    protected RespawnPlugin Plugin;
    protected FileConfiguration Config;
    protected BaseComponent titleHealthRunOut;
    protected BaseComponent titleHealthRemaining;
    protected int RemainingHealth;

    protected PluginObject()
    {
        Plugin = RespawnPlugin.GetInstance();

        Logger = Plugin.getLog4JLogger();
        Config = Plugin.getConfig();

        titleHealthRunOut = new TextComponent("\uE036 你的死亡回归加护已被耗尽，请等待下一轮回开始");
        titleHealthRemaining = new TextComponent("\uE010 你的死亡回归加护剩余 \uEE46 * " + RemainingHealth);
    }
}
