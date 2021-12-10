package alk.respawnplugin;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.IOException;
import java.util.UUID;
import java.util.logging.Level;

public class PluginConfigManager extends PluginObject implements IConfigManager {
    static FileConfiguration data;

    @Override
    public void saveAll() {
        try {
            data.save(Plugin.ConfigFile);
        } catch (IOException e) {
            Logger.log(Level.WARNING, "配置数据未能保存，可能产生回档问题！");
            e.printStackTrace();
        }
    }

    @Override
    public void loadAll() {
        data = YamlConfiguration.loadConfiguration(Plugin.ConfigFile);
    }

    @Override
    public int getPlayerLivingValue(UUID id) {
        return data.getInt("livingvalue." + id.toString(), Integer.parseInt(""));
    }
}
