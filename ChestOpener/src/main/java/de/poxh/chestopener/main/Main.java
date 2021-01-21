package de.poxh.chestopener.main;

import de.poxh.chestopener.api.ItemBuilderAPI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public ItemStack setItems(ItemStack itemStack, String displayName) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(displayName);
        return itemStack;
    }

    List<Inventory> invs = new ArrayList<>();
    ItemStack[] contents;
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

    public ItemStack getRandomItem(ItemStack[] content) {
        Random r = new Random();
        ArrayList<ItemStack> items = new ArrayList<ItemStack>();

        for (ItemStack item : content) {
            if (item != null)
                items.add(item);
        }

        return items.get(r.nextInt(items.size()));
    }

    public void shuffle(Inventory inventory) {
        if(contents == null) {
            ItemStack[] items = new ItemStack[13];
            items[0] = new ItemBuilderAPI(Material.BARRIER).setDisplayName("§c§lVERLOREN").build();
            items[1] = new ItemBuilderAPI(Material.BARRIER).setDisplayName("§c§lVERLOREN").build();
            items[2] = new ItemBuilderAPI(Material.BARRIER).setDisplayName("§c§lVERLOREN").build();
            items[3] = new ItemBuilderAPI(Material.BARRIER).setDisplayName("§c§lVERLOREN").build();
            items[4] = new ItemBuilderAPI(Material.BARRIER).setDisplayName("§c§lVERLOREN").build();
            items[5] = setItems(new ItemStack(Material.GOLDEN_APPLE), "§e§lUHC§7-§8§lKIT §7(§b§lSkyWars§7)");
            items[6] = setItems(new ItemStack(Material.IRON_CHESTPLATE), "§5§lTANK§7-§8§lKIT §7(§b§lSkyWars§7)");
            items[7] = setItems(new ItemStack(Material.WOODEN_AXE), "§c§lVERLOREN");
            items[8] = setItems(new ItemStack(Material.SAND), "§c§lVERLOREN");
            items[9] = setItems(new ItemStack(Material.GRASS), "§c§lVERLOREN");
            items[10] = setItems(new ItemStack(Material.DIAMOND), "§c§lVERLOREN");
            items[11] = setItems(new ItemStack(Material.IRON_INGOT), "§c§lVERLOREN");
            items[12] = setItems(new ItemStack(Material.LAPIS_ORE), "§c§lVERLOREN");
            contents = items;
        }

        int startingIndex = ThreadLocalRandom.current().nextInt(contents.length);

        for (int index = 0; index < startingIndex; index++) {
            for (int itemstacks = 9; itemstacks < 18; itemstacks++) {
                inventory.setItem(itemstacks, getRandomItem(contents));
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
                        inventory.setItem(itemstacks, getRandomItem(contents));
                        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 3, 5);
                    }
                    itemIndex++;
                    if(delay >= .5) {
                        done = true;
                        new BukkitRunnable() {
                            public void run() {
                                ItemStack item = inventory.getItem(13);
                                if(item.getType() == Material.GOLD_INGOT) {
                                    player.sendMessage("§4§lALLAH");
                                    cancel();
                                }
                                player.closeInventory();
                            }
                        }.runTaskLater(Main.getPlugin(Main.class), 50);
                        cancel();
                    }
                }

            }

        }.runTaskTimer(this, 0, 1);
    }
}
