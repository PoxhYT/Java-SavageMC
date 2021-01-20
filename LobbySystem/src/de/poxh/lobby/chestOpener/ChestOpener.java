package de.poxh.lobby.chestOpener;

import de.poxh.lobby.api.ItemBuilderAPI;
import de.poxh.lobby.main.Main;
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
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class ChestOpener implements CommandExecutor {

    List<Inventory> invs = new ArrayList<>();
    private ItemStack[] contents;
    private int itemIndex = 0;


    private File file = new File("plugins/LobbySystem", "ChestOpener.yml");
    private FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);


    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(sender instanceof Player) {
            if(args.length == 0) {
                Player player = (Player) sender;
                spin(player);
            }
        }


        return false;
    }

    public void shuffle(Inventory inventory) {
        if(contents == null) {
            ItemStack[] items = new ItemStack[10];

            items[0] = new ItemStack(Material.GOLD_INGOT, 21);
            items[1] = new ItemStack(Material.GRASS, 1);
            items[2] = new ItemStack(Material.GRAVEL, 9);
            items[3] = new ItemStack(Material.STONE, 16);
            items[4] = new ItemStack(Material.IRON_INGOT, 19);
            items[5] = new ItemStack(Material.DIAMOND, 12);
            items[6] = new ItemStack(Material.DIAMOND_SWORD, 15);
            items[7] = new ItemStack(Material.SAND, 23);
            items[8] = new ItemStack(Material.SANDSTONE_STAIRS, 29);
            items[9] = new ItemStack(Material.LAPIS_ORE, 8);
            contents = items;
        }

        int startingIndex = ThreadLocalRandom.current().nextInt(contents.length);

        for (int index = 0; index < startingIndex; index++) {
            for (int itemstacks = 9; itemstacks < 18; itemstacks++) {
                inventory.setItem(itemstacks, contents[(itemstacks + itemIndex) % contents.length]);
            }
            itemIndex++;
        }

        ItemStack item = new ItemStack(Material.HOPPER);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§8↓");
        item.setItemMeta(meta);
        inventory.setItem(4, item);
    }


    public void spin(final Player player) {
        Inventory inventory = Bukkit.createInventory(null, 27, "§c§lChestOpening");
        shuffle(inventory);
        invs.add(inventory);
        player.openInventory(inventory);

        Random r = new Random();
        double seconds = 7.0 + (12.0 + 7.0) * r.nextDouble();

        new BukkitRunnable() {
            double delay = 0;
            int ticks = 0;
            boolean done = false;

            public void run() {
                if(done) {
                    return;
                }
                ticks++;
                delay += 1 / (20 * seconds);
                if(ticks > delay * 10) {
                    ticks = 0;
                    for (int itemstacks = 9; itemstacks < 18; itemstacks++) {
                        inventory.setItem(itemstacks, contents[(itemstacks + itemIndex) % contents.length]);
                    }
                    itemIndex++;
                    if(delay >= .5) {
                        done = true;
                        new BukkitRunnable() {
                            public void run() {
                                ItemStack item = inventory.getItem(13);
                                if(item.getType() == Material.GOLD_INGOT) {
                                    player.closeInventory();
                                    player.sendMessage(Main.prefix + "§4§lALLAH");
                                    cancel();
                                }
                            }
                        }.runTaskLater(Main.getPlugin(Main.class), 50);
                        cancel();
                    }
                }

            }

        }.runTaskTimer(Main.getInstance(), 0, 20);
    }
}
