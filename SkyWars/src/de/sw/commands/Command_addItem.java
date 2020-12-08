package de.sw.commands;

import com.rosemite.services.helper.Convert;
import com.rosemite.services.helper.Log;
import de.sw.enums.ChestType;
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

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        if(args.length == 5) {
            List<Map<String, Object>> normal = (List<Map<String, Object>>) Main.itemConfiguration.getList("normal");
            List<Map<String, Object>> center = (List<Map<String, Object>>) Main.itemConfiguration.getList("center");


            int chance = Integer.parseInt(args[0]);
            int min = Integer.parseInt(args[1]);
            int max = Integer.parseInt(args[2]);

            ItemStack playerHandItemItems = player.getItemInHand();
            ItemStack[] playerInventoryItems = player.getInventory().getContents();

            if(normal == null) {
                normal = new ArrayList<>();
            }

            if(center == null) {
                center = new ArrayList<>();
            }

            List<KitEnchantments> enchantments = new ArrayList<>();

            if(args[3].equalsIgnoreCase("normal") && args[4].equalsIgnoreCase("hand")) {
                playerHandItemItems.getEnchantments().forEach((enchantment, level) -> {
                    enchantments.add(new KitEnchantments(enchantment.getId(), level));
                });

                RandomItemInChest randomItemInChest = new RandomItemInChest(playerHandItemItems.getData().getItemType().getId(), enchantments, min, max, chance);

                Log.d("ACHTUNG:" + randomItemInChest);
                Log.d("ACHTUNGV2:" + normal);

                normal.add(Convert.getPropertiesToMap(randomItemInChest));

                try {
                    Main.itemConfiguration.set("normal", normal);
                    Main.itemConfiguration.save(Main.itemFile);
                    player.sendMessage(Main.prefix + "§eDu hast die Items erfolgreich hinzugefügt!");
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return true;
            }

            if(args[3].equalsIgnoreCase("center") && args[4].equalsIgnoreCase("hand")) {
                playerHandItemItems.getEnchantments().forEach((enchantment, level) -> {
                    enchantments.add(new KitEnchantments(enchantment.getId(), level));
                });

                RandomItemInChest randomItemInChest = new RandomItemInChest(playerHandItemItems.getData().getItemType().getId(), enchantments, min, max, chance);

                center.add(Convert.getPropertiesToMap(randomItemInChest));
                try {
                    Main.itemConfiguration.set("center", center);
                    Main.itemConfiguration.save(Main.itemFile);
                    player.sendMessage(Main.prefix + "§eDu hast die Items erfolgreich hinzugefügt!");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return true;
            }

            if(args[3].equalsIgnoreCase("normal")) {
                if (args[4].equalsIgnoreCase("inventory")) {
                    for (int i = 0; i < playerInventoryItems.length; i++) {
                        if (playerInventoryItems[i] == null) {
                            continue;
                        }

                        playerInventoryItems[i].getEnchantments().forEach((enchantment, level) -> {
                            enchantments.add(new KitEnchantments(enchantment.getId(), level));
                        });

                        RandomItemInChest randomItemInChest = new RandomItemInChest(playerInventoryItems[i].getData().getItemType().getId(), enchantments, min, max, chance);

                        normal.add(Convert.getPropertiesToMap(randomItemInChest));
                        enchantments.clear();
                    }

                    try {
                        Main.itemConfiguration.set("normal", normal);
                        Main.itemConfiguration.save(Main.itemFile);
                        player.sendMessage(Main.prefix + "§eDu hast die Items erfolgreich hinzugefügt!");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                return true;

            }

            if(args[3].equalsIgnoreCase("center")) {
                if (args[4].equalsIgnoreCase("inventory")) {
                    for (int i = 0; i < playerInventoryItems.length; i++) {
                        if (playerInventoryItems[i] == null) {
                            continue;
                        }

                        playerInventoryItems[i].getEnchantments().forEach((enchantment, level) -> {
                            enchantments.add(new KitEnchantments(enchantment.getId(), level));
                        });

                        RandomItemInChest randomItemInChest = new RandomItemInChest(playerInventoryItems[i].getData().getItemType().getId(), enchantments, min, max, chance);

                        center.add(Convert.getPropertiesToMap(randomItemInChest));
                        enchantments.clear();
                    }
                    try {
                        Main.itemConfiguration.set("center", center);
                        Main.itemConfiguration.save(Main.itemFile);
                        player.sendMessage(Main.prefix + "§eDu hast die Items erfolgreich hinzugefügt!");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                return true;
            }

        } else {
            player.sendMessage(Main.prefix + "§cBitte benutze den Befehl /addItem <chance> <min> <max> <normal/center> <hand/inventory>");
        }
        return false;
    }
}
