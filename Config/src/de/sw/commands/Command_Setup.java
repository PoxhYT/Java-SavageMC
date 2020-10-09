package de.sw.commands;

import de.sw.api.Locations;
import de.sw.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;

public class Command_Setup implements CommandExecutor {

    private Main instance = Main.instance;

    public Command_Setup(final String command) {
        Bukkit.getPluginCommand(command).setExecutor(this);
    }

    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        File file = new File("plugins/Config", "config.yml");
        YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);

        if(sender instanceof Player) {
            int MaxTeams = configuration.getInt("MaxTeams");
            Player player = (Player) sender;
            if(player.hasPermission("server.owner")) {
                if(args.length == 0) {
                    player.sendMessage(Main.prefix + "§cBitte benutze §7/§esetup lobby");
                } else {
                    if (args[0].equalsIgnoreCase("lobby")) {
                        if(args.length == 1) {
                            Locations.setSpawn("Lobby", player.getLocation());
                            player.sendMessage(Main.prefix + "Die §eLobby §7wurde gesetzt.");
                            player.playSound(player.getLocation(), Sound.LEVEL_UP, 1,1);
                        } else
                            player.sendMessage(Main.prefix + "§cBitte benutze §7/§esetup lobby");
                    }
                }
            } else
                player.sendMessage(Main.prefix + "§cDu hast dafür nicht genügend Rechte!");
        }
        return false;
    }
}
