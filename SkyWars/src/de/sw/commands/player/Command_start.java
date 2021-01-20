package de.sw.commands.player;

import de.sw.countdown.LobbyCountdown;
import de.sw.enums.Path;
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
        if(player.hasPermission("server.owner")) {

            int MIN_PLAYERS = (int) Main.MapName1.get(Path.MaxPlayersInTeam.toString());

            if(!started) {
                if(Main.alivePlayers.size() == 1) {
                    if (Main.instance.countdown.isRunning()) {
                        Main.countdown.seconds = 15;
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
