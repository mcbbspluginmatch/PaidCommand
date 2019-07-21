package net.udgame.gdenga.paidcommandpoints.setting;

import net.udgame.gdenga.paidcommandpoints.PaidCommandPoints;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

/**
 * @author: gdenga
 * @date: 2019/7/19 11:58
 * @content:
 */
public class Language {
    public static FileConfiguration openLanguageYml() {
        File regionFile = new File(PaidCommandPoints.getInstance().getDataFolder() + "//language.yml");
        FileConfiguration filec = YamlConfiguration.loadConfiguration(regionFile);
        return filec;
    }
    public static String getNotEnough(){
        return openLanguageYml().getString("language.not_enough");
    }

    public static String getSuccess(){
        return openLanguageYml().getString("language.success");
    }
}
