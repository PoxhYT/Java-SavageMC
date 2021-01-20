package de.cb.poxh.commands;

import de.cb.poxh.api.ItemBuilderAPI;
import de.cb.poxh.main.Main;
import de.cb.poxh.manager.shop.PlayerShopManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Command_shopSetup implements CommandExecutor {

    private static Inventory inventory = Bukkit.createInventory(null, 36, "§b§lBlock§7-§b§lSetup");
    private static File file = new File("plugins/CityBuildSystem", "Items.yml");
    private static FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player player = (Player) sender;
        if(sender instanceof Player) {
            if(args.length == 3) {
                if(args[0].equalsIgnoreCase("setup")) {
                    if(args[1].equalsIgnoreCase("blockInv")) {
                        int price = Integer.valueOf(args[2]);
                        List<String> items = new ArrayList<>();
                        ItemStack itemStack = player.getInventory().getItemInMainHand();
                        ItemMeta itemMeta = itemStack.getItemMeta();
                        List<String> lore = new ArrayList<>();
                        lore.add("§c§lPreis §7➜ §c§l" + price);
                        itemMeta.setLore(lore);
                        itemStack.setItemMeta(itemMeta);
                        PlayerShopManager shopManager = new PlayerShopManager(price, itemStack);
                        items.add(String.valueOf(shopManager.getItemPrice()));

                        cfg.set("Items", items);
                        saveCfg();
                        player.sendMessage(Main.instance.prefix + "Du hast §b§l" + itemStack.getType().name() + " §7zur §b§lBlock§7-§b§lKategorie §7hinzugefügt.");

                        Inventory inventory = Bukkit.createInventory(null, 27, "§7HELLO");

                        for (String s : cfg.getConfigurationSection("Items").getKeys(false)) {
                            ItemStack finalItem = cfg.getItemStack("Items." + s);
                            for (int i = 0; i < inventory.getSize(); i++) {
                                inventory.setItem(i, finalItem);
                                inventory.setItem(i, new ItemBuilderAPI(Material.BLACK_STAINED_GLASS_PANE).setDisplayName("§1").build());
                            }

                        }
                    }
                }
            }
        }
        return false;
    }

    public static void saveCfg() {
        try {
            cfg.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
