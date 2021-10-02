package alk.respawn;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class RespawnEventProcessor implements Listener {

    public static FileConfiguration config = Respawn.instance.getConfig();

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent e) {
        Player player = e.getPlayer();
        int life_value = config.getInt(e.getPlayer().getName());
        if ( life_value > 0 ) {
            int new_value = life_value - 1;
            config.set(player.getName(), new_value);
            Respawn.instance.saveConfig();
        } else if ( life_value == 0 ) {
            player.setGameMode(GameMode.SPECTATOR);
        }
    }
}
