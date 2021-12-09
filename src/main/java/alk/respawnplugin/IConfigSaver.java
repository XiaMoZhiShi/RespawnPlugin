package alk.respawnplugin;

import java.util.UUID;

public interface IConfigSaver {
    void saveAll();
    void loadAll();
    int getPlayerLivingValue(UUID id);
}
