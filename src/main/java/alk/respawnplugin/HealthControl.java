package alk.respawnplugin;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.jetbrains.annotations.NotNull;

public class HealthControl extends PluginObject implements Listener {
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
