package alk.respawnplugin;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
import java.util.logging.Level;

public class ConfigSaver implements IConfigSaver{
    static FileConfiguration data;

    @Override
    public void saveAll() {
        try {
            File dataFile = new File(RespawnPlugin.instance.getDataFolder(), "data.yml");
            data.save(dataFile);
        } catch (IOException e) {
            RespawnPlugin.instance.getLogger().log(Level.WARNING, "配置数据未能保存，可能产生回档问题！");
            e.printStackTrace();
        }
    }

    @Override
    public void loadAll() {
        File dataFile = new File(RespawnPlugin.instance.getDataFolder(), "data.yml");
        data = YamlConfiguration.loadConfiguration(dataFile);
    }

    @Override
    public int getPlayerLivingValue(UUID id) {
        return data.getInt("livingvalue." + id.toString(), Integer.parseInt(""));
    }
}
