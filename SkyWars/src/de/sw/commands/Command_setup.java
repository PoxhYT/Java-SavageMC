package de.sw.commands;

import de.sw.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import javax.imageio.IIOException;
import java.io.File;
import java.io.IOException;

public class Command_setup implements CommandExecutor {

    static File file = new File("plugins/SkyWars", "Config.yml");

    static YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);

    public Command_setup(final String command) {
        Bukkit.getPluginCommand(command).setExecutor(this);
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;
            if(player.hasPermission("server.owner")) {
                if(args.length == 0) {
                    Main.inventoryManager.setTeamInventory(player);
                } else {
                    if(args[0].equalsIgnoreCase("setsize")) {
                        if(args.length == 2) {
                            if(args[1].equalsIgnoreCase("8x1")) {
                                player.sendMessage(Main.prefix + "Die aktuelle §eSpielgröße §7beträgt nun §e8x1§7.");
                                player.playSound(player.getLocation(), Sound.LEVEL_UP,1, 1);
                                yamlConfiguration.set("gameSize", args[1]);
                                try {
                                    yamlConfiguration.save(file);
                                }catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }

                            if(args[1].equalsIgnoreCase("8x2")) {
                                player.sendMessage(Main.prefix + "Die aktuelle §eSpielgröße §7beträgt nun §e8x2§7.");
                                player.playSound(player.getLocation(), Sound.LEVEL_UP,1, 1);
                                yamlConfiguration.set("gameSize", args[1]);
                                try {
                                    yamlConfiguration.save(file);
                                }catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                    }

                    if(args[0].equalsIgnoreCase("setmapname")) {
                        if(args.length == 2) {
                            player.sendMessage(Main.prefix + "Der aktuelle Name der §eMap §7lautet nun §e" + args[1] + "§7.");
                            player.playSound(player.getLocation(), Sound.LEVEL_UP,1, 1);
                            yamlConfiguration.set("MapName", args[1]);
                            try {
                                yamlConfiguration.save(file);
                            }catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }

            }
        }

        return false;
    }
}
