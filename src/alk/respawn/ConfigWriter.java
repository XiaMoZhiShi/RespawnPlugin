package alk.respawn;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class ConfigWriter implements Listener {

    public static FileConfiguration config = Respawn.instance.getConfig();
    Integer defaulter = (Integer) config.get("default-value");

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        String playerName = e.getPlayer().getName();
        String inConfigName = config.getString(playerName);
        if ( inConfigName == null ){
            config.set(playerName, defaulter);
            Respawn.instance.saveConfig();
        }
    }
}
