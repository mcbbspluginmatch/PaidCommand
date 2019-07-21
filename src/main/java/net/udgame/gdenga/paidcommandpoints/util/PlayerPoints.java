package net.udgame.gdenga.paidcommandpoints.util;
import net.udgame.gdenga.paidcommandpoints.PaidCommandPoints;

/**
 * @author: gdenga
 * @date: 2019/7/19 11:42
 * @content:
 */
public class PlayerPoints {
    public boolean payPoints(String playerName, int points)
    {
        if ((playerName == null) || (playerName.length() <= 0)) {
            return false;
        }
        if (points <= 0.0D) {
            return true;
        }
        return PaidCommandPoints.getPlayerPoints().getAPI().take(playerName,points);
    }
    public int getMoney(String playerName)
    {
        return PaidCommandPoints.getPlayerPoints().getAPI().look(playerName);
    }
}
