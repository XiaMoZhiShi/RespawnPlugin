package alk.respawnplugin.Listeners;

import alk.respawnplugin.PluginObject;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.title.Title;
import net.kyori.adventure.title.TitlePart;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;

public class PlayerHealthControl extends PluginObject implements Listener {

    @EventHandler
    public void onPlayerKillEntity(@NotNull EntityDeathEvent e){
        PotionEffect blindPotion = new PotionEffect(PotionEffectType.BLINDNESS, 60, 0, false, false, false);
        PotionEffect slownessPotion = new PotionEffect(PotionEffectType.SLOW, 60, 3, false, false, false);
        final TextComponent titleMain = Component.text("值得吗");
        final TextComponent titleSub = Component.text("你为了自己的生命，杀戮了一个幼年村民");
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
                        e.getEntity().getKiller().sendMessage("虽然你续上了生命，但是你是否在内心泯灭了些什么？");
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                e.getEntity().getKiller().addPotionEffect(blindPotion);
                                e.getEntity().getKiller().addPotionEffect(slownessPotion);
                                long[] times = new long[] {100, 2800, 100};
                                e.getEntity().getKiller().sendTitlePart(TitlePart.TIMES, Title.Times.of(Duration.ofMillis(times[0]), Duration.ofMillis(times[1]), Duration.ofMillis(times[2])));
                                e.getEntity().getKiller().sendTitlePart(TitlePart.TITLE, titleMain);
                                e.getEntity().getKiller().sendTitlePart(TitlePart.SUBTITLE, titleSub);
                            }
                        }.runTaskLater(Plugin, 20);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onItemConsume(@NotNull PlayerItemConsumeEvent e){
        if (e.getItem().getType() == Material.GOLDEN_APPLE && Math.random() <= 0.5){
            String playerName = e.getPlayer().getName();
            int currentLifeRemaining = Config.getInt(playerName);
            currentLifeRemaining++;
            Config.set(playerName, currentLifeRemaining);
            Plugin.saveConfig();
            e.getPlayer().sendMessage("\uE361 你的死亡回归加护次数 \uE36E + 1， 你当前剩余 \uE36E * " + currentLifeRemaining);
        }
    }

    @EventHandler
    public void onPlayerRespawn(@NotNull PlayerRespawnEvent e) {
        Player player = e.getPlayer();
        int lifeRemaining = Config.getInt(player.getName());
        if (lifeRemaining <= -1) {
            int time = Config.getInt("wait-time");
            int tick = time * 60 * 20;
            new BukkitRunnable() {
                @Override
                public void run() {
                    Integer defaultValue = (Integer) Config.get("default-value");
                    Config.set(player.getName(), defaultValue);
                    if (player.isOnline()) {
                        player.setHealth((double) 0);
                    }
                }
            }.runTaskLater(Plugin, tick);
        }

        player.setMaxHealth(Math.min(20 + lifeRemaining * 2, 40));
    }
}
