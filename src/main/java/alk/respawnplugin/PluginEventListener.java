package alk.respawnplugin;

import com.destroystokyo.paper.event.player.PlayerSetSpawnEvent;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.title.Title;
import net.kyori.adventure.title.TitlePart;
import org.bukkit.Bukkit;
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
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.geysermc.cumulus.Form;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.util.UUID;

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

    //region 玩家重生

    private final TextComponent titleHealthRunOut = Component.text("\uE45A 你的死亡回归加护已被耗尽，请等待下一轮回开始");
    private final TextComponent titleRespawn = Component.text("\uE426 死亡回归发动 \uE426");

    protected PotionEffect blindPotion = new PotionEffect(PotionEffectType.BLINDNESS, 180, 0, false, false, false);
    protected PotionEffect slownessPotion = new PotionEffect(PotionEffectType.SLOW, 180, 3, false, false, false);

    @EventHandler
    public void onPlayerRespawn(@NotNull PlayerRespawnEvent e)
    {
        Player player = e.getPlayer();

        Logger.info(player.getName() + " Respawn!");

        int lifeRemaining = Config.getInt(player.getName());

        var titleHealthRemaining = Component.text("\uE461 你的死亡回归加护剩余 \uE46E * " + lifeRemaining);

        if (lifeRemaining <= -1){
            player.setGameMode(GameMode.SPECTATOR);
            player.sendTitlePart(TitlePart.SUBTITLE, titleHealthRunOut);
        } else {
            if (Floodgate.isFloodgatePlayer(player.getUniqueId())){
                //还没想好怎么实现基岩版的音效处理
            } else {
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        player.addPotionEffect(blindPotion);
                        player.addPotionEffect(slownessPotion);
                        player.playSound(Sounds.RespawnParse1);
                    }
                }.runTaskLater(Plugin, 1);

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        player.playSound(Sounds.RespawnParse2);

                        long[] times = new long[] {100, 2000, 100};
                        player.sendTitlePart(TitlePart.TIMES, Title.Times.of(Duration.ofMillis(times[0]), Duration.ofMillis(times[1]), Duration.ofMillis(times[2])));
                        player.sendTitlePart(TitlePart.TITLE, titleRespawn);
                        player.sendTitlePart(TitlePart.SUBTITLE, titleHealthRemaining);
                    }
                }.runTaskLater(Plugin, 180);
            }
        }

        if (lifeRemaining >= 0 && player.getGameMode() == GameMode.SPECTATOR){
            player.setGameMode(GameMode.SURVIVAL);
        }
    }

    //endregion 玩家重生

    @EventHandler
    public void onPlayerDeath(@NotNull PlayerDeathEvent e)
    {
        Logger.info(e.getEntity().getName() + " Death!");
        int lifeRemaining = Config.getInt(e.getPlayer().getName());

        if ( lifeRemaining >= 0 ) {
            lifeRemaining--;
            Config.set(e.getPlayer().getName(), lifeRemaining);
            Plugin.saveConfig();
        }

        if (lifeRemaining == -1) {
            e.setKeepInventory(false);
            e.deathMessage(Component.text("\uE464 " + e.getPlayer().getName() + " 死亡回归加护已耗尽，轮回结束"));
        }
    }

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
                        e.getEntity().getKiller().sendMessage("\uE461 你的死亡回归加护次数 \uE46E + 1， 你当前剩余 \uE46E * " + currentLifeRemaining);
                    }
                }
            }
        }
    }
}
