package de.sw.commands;

import com.rosemite.services.helper.Log;
import de.sw.countdown.LobbyCountdown;
import de.sw.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;

public class Command_start implements CommandExecutor {

    private Main instance;

    private static File file = new File("plugins/SkyWars", "Config.yml");

    private static YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);
    private static boolean started = false;

    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        Player player = (Player) sender;
        Log.d(1);
        if(player.hasPermission("server.owner")) {

            Log.d(2);
            int MIN_PLAYERS = yamlConfiguration.getInt("Settings.MinPlayers");

            Log.d(MIN_PLAYERS);
            Log.d(Main.alivePlayers.size());

            if(!started) {
                Log.d(3);
                if(Main.alivePlayers.size() == MIN_PLAYERS) {
                    Log.d(4);
                    if (Main.instance.countdown.isRunning()) {
                        Log.d(5);
                        LobbyCountdown.seconds = 15;
                        Bukkit.broadcastMessage(Main.prefix + "Der §eTimer §7wurde beschleunigt!");
                        started = true;
                    }
                }
            }

            if(started) {
                if(LobbyCountdown.seconds < 15) {
                    player.sendMessage(Main.prefix + "§cDas Spiel wurde bereits gestartet!");
                    player.playSound(player.getLocation(), Sound.VILLAGER_DEATH, 1, 1);
                }
            }

            if(Main.alivePlayers.size() < MIN_PLAYERS) {
                sender.sendMessage(Main.prefix + "§cEs sind nicht genügene Spieler im Spiel!");
                ((Player) sender).playSound(player.getLocation(), Sound.ANVIL_BREAK, 1, 1);
            }
        } else {
            sender.sendMessage(Main.noPerms);
            ((Player) sender).playSound(player.getLocation(), Sound.ANVIL_BREAK, 1,1 );
        }
        return false;
    }
}
