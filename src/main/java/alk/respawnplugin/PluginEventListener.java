package alk.respawnplugin;

import com.destroystokyo.paper.event.player.PlayerSetSpawnEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.TitlePart;
import org.bukkit.GameMode;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.jetbrains.annotations.NotNull;

public class PluginEventListener extends PluginObject implements Listener {
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
        if ( e.getCause() == PlayerSetSpawnEvent.Cause.BED || e.getCause() == PlayerSetSpawnEvent.Cause.RESPAWN_ANCHOR ){
            e.setCancelled(true);
            e.getPlayer().sendMessage("\uE410你的重生点不会被更改\uE410");
        }
    }

    @EventHandler
    public void onPlayerRespawn(@NotNull PlayerRespawnEvent e)
    {
        Logger.info(e.getPlayer().getName() + " Respawn!");

        Player player = e.getPlayer();

        int life_value = Config.getInt(e.getPlayer().getName());
        RemainingHealth = life_value;
        titleHealthRemaining = Component.text("\uE461 你的死亡回归加护剩余 \uE46E * " + RemainingHealth);

        if (life_value == -1){
            e.getPlayer().setGameMode(GameMode.SPECTATOR);
            e.getPlayer().sendTitlePart(TitlePart.SUBTITLE, titleHealthRunOut);
        } else {
            e.getPlayer().sendTitlePart(TitlePart.TITLE, titleRespawn);
            e.getPlayer().sendTitlePart(TitlePart.SUBTITLE, titleHealthRemaining);
        }

        if (life_value >= 0 && e.getPlayer().getGameMode() == GameMode.SPECTATOR){
            e.getPlayer().setGameMode(GameMode.SURVIVAL);
        }
    }

    @EventHandler
    public void onPlayerDeath(@NotNull PlayerDeathEvent e)
    {
        Logger.info(e.getEntity().getName() + " Death!");
        int life_value = Config.getInt(e.getPlayer().getName());

        if ( life_value >= 0 ) {
            int new_value = life_value - 1;
            Config.set(e.getPlayer().getName(), new_value);
            Plugin.saveConfig();
        }
        if (life_value == -1) {
            e.setKeepInventory(false);
            e.setDeathMessage("\uE464 " + e.getPlayer().getName() + " 死亡回归加护已耗尽，轮回结束");
        }
    }

    @EventHandler
    public void onPlayerKillVillagers(@NotNull EntityDeathEvent e){
        Entity deathEntity = e.getEntity();
        if (deathEntity.getType() == EntityType.VILLAGER){
            Villager vi = (Villager) deathEntity;
            if (vi.isAdult()){
                //不要男妈妈不要男妈妈
            } else {
                //今日份迫害村民
                if ( e.getEntity().getKiller() != null){
                    if ( 0.2 < Math.random()){
                        int lifevalue = Config.getInt(e.getEntity().getKiller().getName());
                        int newValue = lifevalue + 1;
                        Config.set(e.getEntity().getKiller().getName(), newValue);
                        Plugin.saveConfig();
                        e.getEntity().getKiller().sendMessage("\uE461 你的死亡回归加护次数 \uE46E + 1， 你当前剩余 \uE46E * " + newValue);
                    }
                }
            }
        }
    }
}
