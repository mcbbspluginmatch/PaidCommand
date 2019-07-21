package net.udgame.gdenga.paidcommandpoints.command;

import net.udgame.gdenga.paidcommandpoints.setting.Paid;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.List;

/**
 * @author: gdenga
 * @date: 2019/7/19 7:57
 * @content:
 */
public class CommandHandler implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("pcp.admin")){
            sender.sendMessage("[PaidCommandPoints] > §c您没有权限");
            return true;
        }
        if (args.length == 0) {
            sendHelp(sender);
            return true;
        }
        //添加
        if ("add".equals(args[0])){
            if (args.length < 2){
                sendHelp(sender);
                return true;
            }
            //为指令添加免费玩家
            if ("player".equals(args[1])){
                if (args.length <= 3){
                    sender.sendMessage("[PaidCommandPoints] > §c格式错误 /pcp add player [PlayerName] [Command]");
                    return true;
                }
                String playerName = args[2];

                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 3; i < args.length; i++) {
                    if (i == args.length - 1) {
                        stringBuilder.append(args[i]);
                    } else {
                        stringBuilder.append(args[i] + "-");
                    }
                }

                if (!Paid.getPaidCommandList().contains(stringBuilder.toString())){
                    sender.sendMessage("[PaidCommandPoints] > §c付费指令不存在");
                    return true;
                }
                Paid.addCommandIgnorePlayer(stringBuilder.toString(),playerName);
                sender.sendMessage("[PaidCommandPoints] > §b添加成功");
                return true;
            }


            //添加付费指令
            if ("paid".equals(args[1])) {
                if (args.length <= 3) {
                    sender.sendMessage("[PaidCommandPoints] > §c格式错误 /pcp add paid [Cost] [Command]");
                    return true;
                }
                int cost = Integer.valueOf(args[2]);
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 3; i < args.length; i++) {
                    if (i == args.length - 1) {
                        stringBuilder.append(args[i]);
                    } else {
                        stringBuilder.append(args[i] + "-");
                    }
                }
                Paid.addPaidCommand(stringBuilder.toString(), cost);
                sender.sendMessage("[PaidCommandPoints] > §b添加成功");
                return true;
            }
            sendHelp(sender);
            return true;
        }

        if ("list".equals(args[0])){
            if (args.length < 2){
                sendHelp(sender);
                return true;
            }

            //列出所有付费指令
            if ("command".equals(args[1])){
                sender.sendMessage("[PaidCommandPoints] > §5§a以下为付费指令以及所需费用");
                sender.sendMessage("[PaidCommandPoints] > §9---------------------------------------------");
                List<String> commandList = Paid.getPaidCommandList();
                for (String command : commandList){
                    sender.sendMessage("[PaidCommandPoints] > §e"+command.replaceAll("-"," ")+"    §b售价："+Paid.getCommandCost(command)+"点券");
                }
                sender.sendMessage("[PaidCommandPoints] > §9---------------------------------------------");
                return true;
            }

            //查询某个指令的免费玩家列表
            if ("player".equals(args[1])){
                if (args.length < 3) {
                    sender.sendMessage("[PaidCommandPoints] > §c格式错误 /pcp list player [Command]");
                    return true;
                }
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 2; i < args.length; i++) {
                    if (i == args.length - 1) {
                        stringBuilder.append(args[i]);
                    } else {
                        stringBuilder.append(args[i] + "-");
                    }
                }
                if (!Paid.getPaidCommandList().contains(stringBuilder.toString())){
                    sender.sendMessage("[PaidCommandPoints] > §c付费指令不存在");
                    return true;
                }
                List<String> playerList = Paid.getIgnorePlayer(stringBuilder.toString());
                sender.sendMessage("[PaidCommandPoints] > §5§a以下玩家可免费使用付费指令 §e"+stringBuilder.toString().replaceAll("-"," "));
                sender.sendMessage("[PaidCommandPoints] > §9---------------------------------------------");
                for (String playerName : playerList){
                    sender.sendMessage("[PaidCommandPoints] > §e    "+playerName);
                }
                sender.sendMessage("[PaidCommandPoints] > §9---------------------------------------------");
                return true;
            }
            sendHelp(sender);
            return true;

        }

        if ("del".equals(args[0])){
            if (args.length < 2){
                sendHelp(sender);
                return true;
            }
            //删除付费指令
            // /pcp del paid [Command]
            if ("paid".equals(args[1])){

                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 2; i < args.length; i++) {
                    if (i == args.length - 1) {
                        stringBuilder.append(args[i]);
                    } else {
                        stringBuilder.append(args[i] + "-");
                    }
                }
                Paid.delPaidCommand(stringBuilder.toString());
                sender.sendMessage("[PaidCommandPoints] > §b成功删除付费指令/"+stringBuilder.toString().replaceAll("-"," "));
                return true;
            }
            // /pcp del player [Player] [Command]
            if ("player".equals(args[1])){
                if (args.length < 3){
                    sender.sendMessage("[PaidCommandPoints] > §c格式错误 /pcp add player [PlayerName] [Command]");
                    return true;
                }
                String playerName = args[2];
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 3; i < args.length; i++) {
                    if (i == args.length - 1) {
                        stringBuilder.append(args[i]);
                    } else {
                        stringBuilder.append(args[i] + "-");
                    }
                }
                if (!Paid.getPaidCommandList().contains(stringBuilder.toString())){
                    sender.sendMessage("[PaidCommandPoints] > §c付费指令不存在");
                    return true;
                }
                if (!Paid.getIgnorePlayer(stringBuilder.toString()).contains(playerName)){
                    sender.sendMessage("[PaidCommandPoints] > §c付费指令忽略玩家不存在");
                    return true;
                }
                Paid.delPaidCommandIgnorePlayer(stringBuilder.toString(),playerName);
                sender.sendMessage("[PaidCommandPoints] > §b成功删除免费玩家"+playerName);
                return true;
            }
            sendHelp(sender);
            return true;
        }

        sendHelp(sender);
        return true;
    }

    public static void sendHelp(CommandSender sender){
        sender.sendMessage("[PaidCommandPoints] >§a§b---------------------------------------------");
        sender.sendMessage("[PaidCommandPoints] >§b§a/pcp add paid [Cost] [Command]             添加新的付费指令");
        sender.sendMessage("[PaidCommandPoints] >§b§a/pcp add player [PlayerName] [Command]     为指令添加免费玩家");
        sender.sendMessage("[PaidCommandPoints] >§b§a/pcp list command                    列出所有付费指令");
        sender.sendMessage("[PaidCommandPoints] >§b§a/pcp list player [Command]           查询某个指令的免费玩家列表");
        sender.sendMessage("[PaidCommandPoints] >§b§a/pcp del paid [Command]              删除付费指令");
        sender.sendMessage("[PaidCommandPoints] >§b§a/pcp del player [Player] [Command]   删除付费指令的免费玩家");
        sender.sendMessage("[PaidCommandPoints] >§a§b--------------------------------------------");
    }
}
