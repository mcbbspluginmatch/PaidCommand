package net.udgame.gdenga.paidcommandpoints.event;

import net.udgame.gdenga.paidcommandpoints.PaidCommandPoints;
import net.udgame.gdenga.paidcommandpoints.setting.Language;
import net.udgame.gdenga.paidcommandpoints.setting.Paid;
import net.udgame.gdenga.paidcommandpoints.util.PlayerPoints;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.ArrayList;
import java.util.List;

public class CommandListener implements Listener {
    @EventHandler
    public void commandListen(PlayerCommandPreprocessEvent event){
        if (!PaidCommandPoints.getInstance().getConfig().getBoolean("PaidCommandPoints.Paid_Enable")){
            if (event.isCancelled()){
                event.setCancelled(false);
                return;
            }
            return;
        }
        Player player = event.getPlayer();
        String command = event.getMessage();
        if (player.hasPermission("pcp.free")){
            if (event.isCancelled()){
                event.setCancelled(false);
                return;
            }
            return;
        }
        //-----------------------------------------------------------------------
        command = command.replace("/","");
        command = command.trim();
        int commandNum = command.indexOf(" ");
        String[] commands = command.split(" ");
        int cost = 0;
        List<String> ignorePlayer = new ArrayList<>();
        List<String> commandList =  Paid.getPaidCommandList();
        g1:for (int i=0; i<commandList.size();i++){
            String paidCommand = commandList.get(i);
            if (!(paidCommand.contains("-")&&command.contains(" "))){
                if (paidCommand.equals(command)){
                    cost = Paid.getCommandCost(commandList.get(i));
                    ignorePlayer = Paid.getIgnorePlayer(commandList.get(i));
                    break g1;
                }
            }
            List<String> nn = new ArrayList<>();
            String[] paidCommands = paidCommand.split("-");
            if (!paidCommand.contains("-")){
                paidCommands =new String[1];
                nn.add(paidCommand);
                nn.toArray(paidCommands);
            }
            int n = paidCommands.length;
            int n1 = 0;
            for (int i1 = 0;i1 < n; i1++){
                for (int i2 = 0;i2 < commands.length; i2++){
                    if (paidCommands[i1].equals(commands[i2])){
                        n1++;
                    }
                }
            }
            if (n1 == n){
                cost = Paid.getCommandCost(commandList.get(i));
                ignorePlayer = Paid.getIgnorePlayer(commandList.get(i));
                break g1;
            }
        }
        if (cost == 0 || ignorePlayer.contains(player.getName())){
            event.setCancelled(false);
            return;
        }
        PlayerPoints playerPoints = new PlayerPoints();
        int hasPoints = playerPoints.getMoney(player.getName());
        String message = "";
        if (!playerPoints.payPoints(player.getName(),cost)){
            message = Language.getNotEnough().replaceAll("\\$\\{POINTS\\}",String.valueOf(cost)).replaceAll("\\$\\{NOW\\}",String.valueOf(hasPoints));
            player.sendMessage(message);
            event.setCancelled(true);
            return;
        }
        message = Language.getSuccess().replaceAll("\\$\\{POINTS\\}",String.valueOf(cost)).replaceAll("\\$\\{NOW\\}",String.valueOf(playerPoints.getMoney(player.getName())));
        player.sendMessage(message);
        event.setCancelled(false);
        return;
    }
}
