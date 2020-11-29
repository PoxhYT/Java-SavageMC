package de.loop.commands;

import de.loop.main.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Command_addItem implements CommandExecutor {

    public static File file = new File("plugins/RandomChestItems", "ItemListSize.yml");
    public static FileConfiguration yamlConfiguration = (FileConfiguration) YamlConfiguration.loadConfiguration(file);

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        if(sender instanceof Player) {
            if(args.length == 0) {

            }
        } else {
            Main.sendWarning(Main.prefix + "§cDieser Befehl kann nur durch ein Spieler ausgeführt werden!");
        }

        return false;
    }
}
