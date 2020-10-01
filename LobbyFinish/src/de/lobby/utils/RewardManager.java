package de.lobby.utils;

import com.sun.deploy.util.BlackList;
import de.lobby.main.Main;
import java.io.File;
import java.io.IOException;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class RewardManager {
    private Main instance;

    public RewardManager(Main instance) {
        this.instance = instance;
    }

    public boolean getAllowReward(Player player) {
        long current = System.currentTimeMillis();
        long millis = getTime(player);
        return (current > millis);
    }

    public File getRewardFile() {
        return new File(this.instance.getDataFolder(), "rewards.yml");
    }

    public void setReward(Player player) {
        YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(getRewardFile());
        long toSet = System.currentTimeMillis() + 86400000L;
        yamlConfiguration.set(player.getUniqueId() + ".millis", Long.valueOf(toSet));
        try {
            yamlConfiguration.save(getRewardFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (String command : YamlConfiguration.loadConfiguration(FileManager.getConfigFile()).getStringList("rewardCommands")) {
            command = command.replace("%player%", player.getName());
            Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), command);
        }
    }

    public long getTime(Player player) {
        YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(getRewardFile());
        return yamlConfiguration.getLong(player.getUniqueId() + ".millis");
    }

    public String getRemainingTime(long millis) {
        long seconds = millis / 1000L;
        long minutes = 0L;
        while (seconds > 60L) {
            seconds -= 60L;
            minutes++;
        }
        long hours = 0L;
        while (minutes > 60L) {
            minutes -= 60L;
            hours++;
        }
        return "§7" + hours + " Stunde(n) " + minutes + " Minute(n) " + seconds + " Sekunde(n)";
    }
}
