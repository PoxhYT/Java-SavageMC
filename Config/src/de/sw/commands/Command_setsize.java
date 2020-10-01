package de.sw.commands;

import de.sw.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class Command_setsize implements CommandExecutor {

    static File file = new File("./plugins/Config/", "Config.yml");

    static YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);

    public Command_setsize(final String command) {
        Bukkit.getPluginCommand(command).setExecutor(this);
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        if(sender instanceof Player) {
            if(args.length == 1) {
                if (args[0].equalsIgnoreCase("8x1")) {
                    yamlConfiguration.set("Size", "8x1");
                    try {
                        yamlConfiguration.save(file);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    sender.sendMessage(Main.prefix + "Die aktuelle §eSpielvariante §7lautet nun: §e8x1");
                    sender.sendMessage(Main.prefix + "§7Die §eConfig §7wird §egespeichert§7...");
                    Main.configCountdown.start();
                }

                if (args[0].equalsIgnoreCase("8x2")) {
                    yamlConfiguration.set("Size", "8x2");
                    try {
                        yamlConfiguration.save(file);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    sender.sendMessage(Main.prefix + "Die aktuelle §eSpielvariante §7lautet nun: §e8x2");
                    sender.sendMessage(Main.prefix + "§7Die §eConfig §7wird §egespeichert§7...");
                    Main.configCountdown.start();
                }

                if (args[0].equalsIgnoreCase("8x3")) {
                    yamlConfiguration.set("Size", "8x3");
                    try {
                        yamlConfiguration.save(file);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    sender.sendMessage(Main.prefix + "Die aktuelle §eSpielvariante §7lautet nun: §e8x3");
                    sender.sendMessage(Main.prefix + "§7Die §eConfig §7wird §egespeichert§7...");
                    Main.configCountdown.start();
                }
            }

        } else
            player.sendMessage(Main.prefix + "§cDu musst ein Spieler sein!");
        return false;
    }
}
