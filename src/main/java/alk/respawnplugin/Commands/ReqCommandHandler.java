package alk.respawnplugin.Commands;

import alk.respawnplugin.PluginObject;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ReqCommandHandler extends PluginObject implements CommandExecutor {
    @Override
    public boolean onCommand(
            @NotNull CommandSender sender,
            @NotNull Command command,
            @NotNull String label,
            @NotNull String[] args)
    {
        if (args.length != 1) return false;

        if (!(sender instanceof Player))
        {
            Logger.warning("你不能向其他玩家发送请求！");
            return true;
        }

        //获取发起玩家和目标玩家
        Player sourcePlayer = (Player)sender;
        Player targetPlayer = Bukkit.getPlayer(args[0]);

        //如果没有目标玩家
        if (targetPlayer == null)
        {
            sender.sendMessage("未找到该玩家");
            return true;
        }

        //如果目标玩家是自己
        if (sourcePlayer.getUniqueId().equals(targetPlayer.getUniqueId()))
        {
            sender.sendMessage("你不能给你自己发送申请");
            return true;
        }

        //如果已经发送了同样的请求
        if (Plugin.RequestCommandPlayerMap.get(sourcePlayer) != null)
        {
            sender.sendMessage("你已经发送过了");
            return true;
        }

        //加入PlayerMap
        Plugin.RequestCommandPlayerMap.put(sourcePlayer, targetPlayer);

        //发送消息
        targetPlayer.sendRawMessage("收到来自" + sourcePlayer + "的请求！");
        sourcePlayer.sendMessage("已发送到" + targetPlayer + "的请求！");

        return true;
    }
}
