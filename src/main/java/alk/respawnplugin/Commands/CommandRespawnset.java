package alk.respawnplugin.Commands;

import alk.respawnplugin.PluginObject;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;

public class CommandRespawnset extends PluginObject implements PluginCommandExecutor {
    @Override
    public String GetCommandName()
    {
        return "respawnset";
    }

    @Override
    @ParametersAreNonnullByDefault
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (strings.length != 2) {
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
