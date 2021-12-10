package alk.respawnplugin;

import com.destroystokyo.paper.event.player.PlayerSetSpawnEvent;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.jetbrains.annotations.NotNull;

public class EventListener extends PluginObject implements Listener {
    Integer defaultValue = (Integer) Config.get("default-value");

    @EventHandler
    public void onPlayerJoin(@NotNull PlayerJoinEvent e) {
        //获取玩家名
        String playerName = e.getPlayer().getName();

        //获取玩家在配置中的名字
        String inConfigName = Config.getString(playerName);

        //如果配置名是null（即为新玩家）
        if (inConfigName == null) {
            Config.set(playerName, defaultValue);

            //保存
            Plugin.saveConfig();
        }
    }

    @EventHandler
    public void onPlayerSetSpawn(@NotNull PlayerSetSpawnEvent e){
        if ( e.getCause() == PlayerSetSpawnEvent.Cause.BED ){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerRespawn(@NotNull PlayerRespawnEvent e)
    {
        Logger.info(e.getPlayer().getName() + " Respawn!");

        Player player = e.getPlayer();

        int life_value = Config.getInt(e.getPlayer().getName());

        if ( life_value > 0 ) {
            int new_value = life_value - 1;
            Config.set(player.getName(), new_value);
            Plugin.saveConfig();
        } else if (player.getGameMode() != GameMode.SPECTATOR) {
            player.setGameMode(GameMode.SPECTATOR);
        }
    }

    @EventHandler
    public void onPlayerDeath(@NotNull PlayerDeathEvent e)
    {
        Logger.info(e.getEntity().getName() + " Death!");
    }
}
