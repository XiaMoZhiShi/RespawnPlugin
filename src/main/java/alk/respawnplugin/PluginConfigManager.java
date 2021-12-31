package alk.respawnplugin;

import org.apache.logging.log4j.Level;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.IOException;
import java.util.UUID;

public class PluginConfigManager extends PluginObject implements IConfigManager {
    @Override
    public void saveAll() {
        try {
            Config.save(Plugin.ConfigFile);
        } catch (IOException e) {
            Logger.log(Level.WARN, "配置数据未能保存，可能产生回档问题！");
            e.printStackTrace();
        }
    }

    @Override
    public void loadAll() { } //

    @Override
    public void savePlayerConfig(UUID id, int value) {
        Config.set("livingvalue." + id.toString(), value);
    }

    @Override
    public int getPlayerLivingValue(UUID id) {
        return Config.getInt("livingvalue." + id.toString());
    }

    @Override
    public boolean isExist(UUID id) {
        return Config.contains("livingvalue." + id.toString());
    }
}
