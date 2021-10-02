package alk.respawnplugin;

import java.util.logging.Logger;

public class Utils {

    public static Logger GetLogger()
    {
        return RespawnPlugin.GetInstance().getLogger();
    }
}
