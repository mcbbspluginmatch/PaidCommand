package net.udgame.gdenga.paidcommandpoints;

import net.udgame.gdenga.paidcommandpoints.command.CommandHandler;
import net.udgame.gdenga.paidcommandpoints.event.CommandListener;
import org.black_ixx.playerpoints.PlayerPoints;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class PaidCommandPoints extends JavaPlugin {
    private static PlayerPoints playerPoints = null;
    private static PaidCommandPoints instance;
    @Override
    public void onLoad(){
        File paid = new File(getDataFolder() + "//paid.yml");
        File language = new File(getDataFolder() + "//language.yml");
        if (!paid.exists()){
            saveResource("paid.yml",false);
        }
        if (!language.exists()){
            saveResource("language.yml",false);
        }
        saveDefaultConfig();
    }

    @Override
    public void onEnable() {


        instance = this;
        if (!Bukkit.getPluginManager().isPluginEnabled("PlayerPoints")){
            Bukkit.getServer().getConsoleSender().sendMessage("§7[PaidCommandPoints§7] §l§e> 插件需要PlayerPoints前置");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }
        if (!hookPlayerPoints()){
            Bukkit.getServer().getConsoleSender().sendMessage("§7[PaidCommandPoints§7] §l§e> 插件初始化失败");
        }
        Bukkit.getPluginCommand("pcp").setExecutor(new CommandHandler());
        Bukkit.getPluginManager().registerEvents(new CommandListener(),this);
        Bukkit.getServer().getConsoleSender().sendMessage("§7[PaidCommandPoints§7] §l§b> 付费指令插件已加载——点券版");
        Bukkit.getServer().getConsoleSender().sendMessage("§7[PaidCommandPoints§7] §l§b> 作者：冰冷  QQ:736131306");
    }

    @Override
    public void onDisable() {
        Bukkit.getServer().getConsoleSender().sendMessage("§7[PaidCommandPoints§7] §l§e> 付费指令插件已卸载，感谢使用");
    }

    private boolean hookPlayerPoints() {
        final Plugin plugin = this.getServer().getPluginManager().getPlugin("PlayerPoints");
        playerPoints = PlayerPoints.class.cast(plugin);
        return playerPoints != null;
    }

    public static PaidCommandPoints getInstance(){ return instance; }
    public static PlayerPoints getPlayerPoints() { return playerPoints; }
}
