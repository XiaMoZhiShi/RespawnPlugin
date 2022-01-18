package alk.respawnplugin;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.apache.logging.log4j.Logger;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.geysermc.floodgate.api.FloodgateApi;
import org.jetbrains.annotations.NotNull;

/**
 * 在这里存放一些会被经常调用的东西
 */
public abstract class PluginObject {
    protected FloodgateApi floodgateApi;
    protected Logger Logger;
    protected RespawnPlugin Plugin;
    protected FileConfiguration Config;
    protected @NotNull TextComponent titleHealthRunOut;
    protected @NotNull TextComponent titleHealthRemaining;
    protected @NotNull TextComponent titleRespawn;
    protected int RemainingHealth;
    protected PotionEffect blindPotion;
    protected PotionEffect slownessPotion;

    protected PluginObject()
    {
        Plugin = RespawnPlugin.GetInstance();

        Logger = Plugin.getLog4JLogger();
        Config = Plugin.getConfig();
        floodgateApi = FloodgateApi.getInstance();

        titleHealthRunOut = Component.text("\uE45A 你的死亡回归加护已被耗尽，请等待下一轮回开始");
        titleRespawn = Component.text("\uE426 死亡回归发动 \uE426");

        blindPotion = new PotionEffect(PotionEffectType.BLINDNESS, 180, 0, false, false, false);
        slownessPotion = new PotionEffect(PotionEffectType.SLOW, 180, 3, false, false, false);
    }
}
