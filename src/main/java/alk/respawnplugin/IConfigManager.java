package alk.respawnplugin;

import java.util.UUID;

public interface IConfigManager {
    void saveAll();
    void loadAll();
    void savePlayerConfig(UUID id, int value);

    int getPlayerLivingValue(UUID id);

    boolean isExist(UUID id);
}
