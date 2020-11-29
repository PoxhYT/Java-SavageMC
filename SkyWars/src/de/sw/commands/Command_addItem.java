package de.sw.commands;

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

    private static File file = new File("plugins/SkyWars", "Items.yml");

    private static ArrayList<ItemStack> itemList = new ArrayList<>();

    private static FileConfiguration cfg = (FileConfiguration) YamlConfiguration.loadConfiguration(file);

    public static void saveCfg() {
        try {
            cfg.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        if(args.length == 0) {
            itemList.add(player.getItemInHand());
            cfg.set("Items", itemList);
            saveCfg();
        }
        return false;
    }
}
