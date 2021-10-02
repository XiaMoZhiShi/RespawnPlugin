package alk.respawnplugin;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.jetbrains.annotations.NotNull;

public class EventListener implements Listener {

    public static FileConfiguration config = RespawnPlugin.GetInstance().getConfig();

    Integer defaulter = (Integer) config.get("default-value");

    @EventHandler
    public void onPlayerJoin(@NotNull PlayerJoinEvent e) {
        //获取玩家名
        String playerName = e.getPlayer().getName();

        //获取玩家在配置中的名字
        String inConfigName = config.getString(playerName);

        //如果配置名是null（即为新玩家）
        if (inConfigName == null) {
            config.set(playerName, defaulter);

            //保存
            RespawnPlugin.GetInstance().saveConfig();
        }
    }

    @EventHandler
    public void onPlayerRespawn(@NotNull PlayerRespawnEvent e)
    {
        RespawnPlugin.GetInstance().getLogger().info(e.getPlayer().getName() + "Respawn!");
    }

    @EventHandler
    public void onPlayerDeath(@NotNull PlayerDeathEvent e)
    {
        RespawnPlugin.GetInstance().getLogger().info(e.getEntity().getName() + "Death!");
    }
}
