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

import java.util.UUID;

public class PluginEventListener extends PluginObject implements Listener {
    Integer defaultValue = (Integer) Config.get("default-value");

    IConfigManager icm;
    @EventHandler
    public void onPlayerJoin(@NotNull PlayerJoinEvent e) {
        //获取玩家UUID
        UUID playerUUID = e.getPlayer().getUniqueId();

        //判断玩家是否存在于配置中，若无，设置为配置中的默认值
        if( icm.isExist(playerUUID) == false){
            icm.savePlayerConfig(playerUUID, defaultValue);
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

        int life_value = icm.getPlayerLivingValue(player.getUniqueId());

        if ( life_value > 0 ) {
            int new_value = life_value - 1;
            icm.savePlayerConfig(player.getUniqueId(), new_value);
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
