package alk.respawnplugin.Commands;

import alk.respawnplugin.PluginObject;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Optional;

public class ReqAcceptCommandHandler extends PluginObject implements CommandExecutor {
    @Override
    public boolean onCommand(
            @NotNull CommandSender sender,
            @NotNull Command command,
            @NotNull String label,
            @NotNull String[] args)
    {
        if (args.length > 1 || !(sender instanceof Player)) return false;

        //发起玩家
        Player sourcePlayer = null;

        //如果长度为0，手动获取目标玩家
        //Lin: KeyValuePair in C#?
        Optional<Map.Entry<Player, Player>> optional;

        //如果没指定玩家
        if (args.length == 0)
        {
            //在PlayerMap中查询发起玩家
            optional = Plugin.RequestCommandPlayerMap.entrySet()
                    .stream()
                    .filter(e -> e.getValue().getName().equals(sender.getName())).findFirst();
        }
        else
        {
            //在PlayerMap中查找玩家
            optional = Plugin.RequestCommandPlayerMap.entrySet()
                    .stream()
                    .filter(e -> e.getKey().getName().equals(args[0])).findFirst();
        }

        //获取Key
        if (optional.isPresent()) sourcePlayer = optional.get().getKey();

        //如果还是没有，那么返回false
        if (sourcePlayer == null)
        {
            sender.sendMessage("未找到玩家或无申请");
            return true;
        }

        //如果玩家不在线，返回false
        if (!sourcePlayer.isOnline())
        {
            sender.sendMessage("该玩家已下线");
            return true;
        }

        sender.sendMessage("将你传送到" + sourcePlayer.getName());

        //将发送者传送到发起玩家旁边
        ((Player) sender).teleport(sourcePlayer);

        //从PlayerMap中移除发起玩家
        Plugin.RequestCommandPlayerMap.remove(sourcePlayer);

        return true;
    }
}
