package alk.respawnplugin;

import java.util.UUID;

public interface IConfigManager {
    void saveAll();
    void loadAll();

    int getPlayerLivingValue(UUID id);
}
