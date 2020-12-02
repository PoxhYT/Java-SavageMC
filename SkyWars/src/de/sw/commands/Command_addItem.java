package de.sw.commands;

import de.sw.enums.ChestType;
import de.sw.listener.RandomChestItems;
import de.sw.main.Main;
import de.sw.manager.ItemDocument;
import de.sw.manager.KitEnchantments;
import de.sw.manager.RandomItemInChest;
import net.minecraft.server.v1_8_R3.Items;
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
import java.util.List;
import java.util.Map;

public class Command_addItem implements CommandExecutor {

    private static File file = new File("plugins/SkyWars", "Items.yml");

    private static FileConfiguration cfg = (FileConfiguration) YamlConfiguration.loadConfiguration(file);

    private List<RandomItemInChest> normal = (List<RandomItemInChest>) Main.yamlConfigurationSkyWars.getList("normal");
    private List<ItemDocument> center = (List<ItemDocument>) Main.yamlConfigurationSkyWars.getList("center");

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        if(args.length == 4) {

            int min = Integer.parseInt(args[0]);
            int max = Integer.parseInt(args[1]);
            String handOrInventory = args[3];

            ItemStack playerHandItemItems = player.getItemInHand();
            ItemStack[] playerInventoryItems = player.getInventory().getContents();

            if(normal == null) {
                normal = new ArrayList<>();
            }

            if(center == null) {
                center = new ArrayList<>();
            }


            ItemDocument document = new ItemDocument(playerHandItemItems.getData().getItemType().getId(), (List<KitEnchantments>) playerHandItemItems.getEnchantments(), 4);

            if(args[2].equalsIgnoreCase("normal")) {
                if (args[3].equalsIgnoreCase("hand")) {
                    normal.add(document);
                    try {
                        cfg.save(file);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        } else {
            player.sendMessage(Main.prefix + "§cBitte benutze den Befehl /addItem <min> <max> <normal/center> <hand/inventory>");
        }
        return false;
    }
}
