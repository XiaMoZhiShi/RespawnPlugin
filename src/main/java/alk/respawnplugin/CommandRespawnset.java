package alk.respawnplugin;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;

public class CommandRespawnset extends PluginObject implements CommandExecutor {
    @Override
    @ParametersAreNonnullByDefault
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (strings.length == 0) {
            return false;
        }
        Player player = Bukkit.getPlayer(strings[0]);
        if (player == null){
            return false;
        }
        int newValue = Integer.parseInt(strings[1]);
        Config.set(player.getName(), newValue);
        Plugin.saveConfig();
        commandSender.sendMessage("已将 " + strings[0] + " 的剩余死亡回归次数设置为 " + strings[1]);
        return true;
    }
}
