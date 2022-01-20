package alk.respawnplugin.Listeners;

import alk.respawnplugin.PluginObject;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.jetbrains.annotations.NotNull;

public class PlayerKilledEntityListener extends PluginObject implements Listener {

    @EventHandler
    public void onPlayerKillEntity(@NotNull EntityDeathEvent e){
        Entity deathEntity = e.getEntity();
        if (deathEntity.getType() == EntityType.VILLAGER){
            Villager vi = (Villager) deathEntity;
            if (vi.isAdult()){
                //不要男妈妈不要男妈妈
            } else {
                //今日份迫害村民
                if ( e.getEntity().getKiller() != null){
                    if ( 0.2 < Math.random()){
                        int currentLifeRemaining = Config.getInt(e.getEntity().getKiller().getName());
                        currentLifeRemaining++;

                        Config.set(e.getEntity().getKiller().getName(), currentLifeRemaining);
                        Plugin.saveConfig();
                        e.getEntity().getKiller().sendMessage("\uE361 你的死亡回归加护次数 \uE36E + 1， 你当前剩余 \uE36E * " + currentLifeRemaining);
                    }
                }
            }
        }
    }
}
