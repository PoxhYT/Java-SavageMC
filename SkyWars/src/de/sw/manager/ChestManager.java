package de.sw.manager;

import de.sw.enums.ChestType;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Random;

import de.sw.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.block.DoubleChest;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ChestManager {
    private final Map<Integer, Inventory> chestItemList = new HashMap<>();

    private final Map<Integer, Inventory> centerChestItemList = new HashMap<>();

    private final Map<Integer, Inventory> crateItemList = new HashMap<>();

    private final Map<Integer, Inventory> basicChestItemList = new HashMap<>();

    private final Random random = new Random();

    private List<Integer> randomLoc = new ArrayList<>();

    private List<Integer> randomDLoc = new ArrayList<>();

    public ChestManager() {
        load(this.chestItemList, "chest.yml");
        load(this.centerChestItemList, "centerchest.yml");
        load(this.basicChestItemList, "basicchest.yml");

        int i;
        for (i = 0; i < 27; i++)
            this.randomLoc.add(Integer.valueOf(i));
        for (i = 0; i < 54; i++)
            this.randomDLoc.add(Integer.valueOf(i));
    }

    public void addItems(List<ItemStack> items, ChestType ct, int percent) {
        Map<Integer, Inventory> toAddTo = getItemMap(ct);
        String fileName = getFileName(ct);
        if (!toAddTo.containsKey(Integer.valueOf(percent)))
            toAddTo.put(Integer.valueOf(percent), Bukkit.createInventory(null, 54, fileName + " " + percent));
        for (ItemStack iStack : items) {
            ((Inventory)toAddTo.get(Integer.valueOf(percent))).addItem(new ItemStack[] { iStack });
        }
        save(toAddTo, ct);
    }

    public void load(Map<Integer, Inventory> itemList, String fileName) {
        itemList.clear();
        File chestFile = new File(Main.getInstance().getDataFolder(), fileName);
        if (!chestFile.exists())
            Main.getInstance().saveResource(fileName, false);
        if (chestFile.exists()) {
            YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(chestFile);
            if (yamlConfiguration.getConfigurationSection("chestItems") != null)
                for (String key : yamlConfiguration.getConfigurationSection("chestItems").getKeys(false)) {
                    if (Util.get().isInteger(key)) {
                        int percent = Integer.parseInt(key);
                        List<ItemStack> items = (List<ItemStack>) yamlConfiguration.getList("chestItems." + key + ".items");
                        if (!itemList.containsKey(Integer.valueOf(percent)))
                            itemList.put(Integer.valueOf(percent), Bukkit.createInventory(null, 54, fileName + " " + percent));
                        for (ItemStack iStack : items) {
                            if (iStack != null)
                                ((Inventory)itemList.get(Integer.valueOf(percent))).addItem(new ItemStack[] { iStack });
                        }
                    }
                }
        }
    }

    public void save(String title) {
        String[] parts = title.split(" ", 2);
        ChestType ct = getChestType(ChatColor.stripColor(parts[0]));
        if (ct != null) {
            Map<Integer, Inventory> toSave = getItemMap(ct);
            save(toSave, ct);
        }
    }

    private void save(Map<Integer, Inventory> chestList, ChestType ct) {
        String fileName = getFileName(ct);
        File chestFile = new File(Main.getInstance().getDataFolder(), fileName);
        if (!chestFile.exists())
            Main.getInstance().saveResource(fileName, false);
        if (chestFile.exists())
            try {
                YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(chestFile);
                for (Iterator<Integer> iterator = chestList.keySet().iterator(); iterator.hasNext(); ) {
                    int percent = ((Integer)iterator.next()).intValue();
                    List<ItemStack> items = new ArrayList<>();
                    for (ItemStack item : ((Inventory)chestList.get(Integer.valueOf(percent))).getContents()) {
                        if (item != null && !item.getType().equals(Material.AIR))
                            items.add(item);
                    }
                    yamlConfiguration.set("chestItems." + percent + ".items", items);
                }
                yamlConfiguration.save(chestFile);
            } catch (IOException ioException) {
                System.out.println("Failed to save chestfile " + fileName + ": " + ioException.getMessage());
            }
    }

    private void fillChest(Object chest, Map<Integer, Inventory> fill) {
        Inventory inventory = null;
        if (chest instanceof Chest) {
            inventory = ((Chest)chest).getInventory();
        } else if (chest instanceof DoubleChest) {
            inventory = ((DoubleChest)chest).getInventory();
        }
        if (inventory != null) {
            inventory.clear();
            int added = 0;
            Collections.shuffle(this.randomLoc);
            Collections.shuffle(this.randomDLoc);
            for (Iterator<Integer> iterator = fill.keySet().iterator(); iterator.hasNext(); ) {
                int chance = ((Integer)iterator.next()).intValue();
                for (ListIterator<ItemStack> listIterator = ((Inventory)fill.get(Integer.valueOf(chance))).iterator(); listIterator.hasNext(); ) {
                    ItemStack item = listIterator.next();
                    if (item != null && !item.getType().equals(Material.AIR)) {
                        if (chest instanceof Chest &&
                                this.random.nextInt(100) + 1 <= chance) {
                            inventory.setItem(((Integer)this.randomLoc.get(added)).intValue(), item);
                            added++;
                            if (added < inventory.getSize() - 1) {
                                if (added >= Main.getInstance().getMaxChest()) {
                                }
                            } else {
                                // Byte code: goto -> 326
                            }
                        }
                        if (chest instanceof DoubleChest &&
                                this.random.nextInt(100) + 1 <= chance) {
                            inventory.setItem(((Integer)this.randomDLoc.get(added)).intValue(), item);
                            added++;
                            if (added < inventory.getSize() - 1) {
                                if (added >= Main.getInstance().getMaxDoubleChest())
                                    // Byte code: goto -> 326
                                    continue;
                            }
                            // Byte code: goto -> 326
                        }
                    }
                }
            }
        }
    }

    public void fillCrate(Inventory inventory, int max) {
        if (inventory != null) {
            inventory.clear();
            int added = 0;
            Collections.shuffle(this.randomLoc);
            for (Iterator<Integer> iterator = this.crateItemList.keySet().iterator(); iterator.hasNext(); ) {
                int chance = ((Integer)iterator.next()).intValue();
                for (ListIterator<ItemStack> listIterator = ((Inventory)this.crateItemList.get(Integer.valueOf(chance))).iterator(); listIterator.hasNext(); ) {
                    ItemStack item = listIterator.next();
                    if (item != null && !item.getType().equals(Material.AIR) &&
                            this.random.nextInt(100) + 1 <= chance) {
                        inventory.setItem(((Integer)this.randomLoc.get(added)).intValue(), item);
                        added++;
                        if (added >= inventory.getSize() - 1 || added >= max)
                            break;
                    }
                }
            }
        }
    }

    public void editChest(ChestType ct, int percent, Player player) {
        Map<Integer, Inventory> toEdit = getItemMap(ct);
        String fileName = getFileName(ct);
        if (!toEdit.containsKey(Integer.valueOf(percent)))
            toEdit.put(Integer.valueOf(percent), Bukkit.createInventory(null, 54, fileName + " " + percent));
        player.openInventory(toEdit.get(Integer.valueOf(percent)));
    }

    private String getFileName(ChestType ct) {
        if (ct == ChestType.BASIC)
            return "basicchest.yml";
        if (ct == ChestType.CENTER)
            return "basiccenterchest.yml";
        return "chest.yml";
    }

    private ChestType getChestType(String fileName) {
        if (fileName.equalsIgnoreCase("basicchest.yml"))
            return ChestType.BASIC;
        if (fileName.equalsIgnoreCase("centerchest.yml"))
            return ChestType.CENTER;
        return null;
    }

    private Map<Integer, Inventory> getItemMap(ChestType ct) {
        if (ct == ChestType.BASIC)
            return this.basicChestItemList;
        if (ct == ChestType.CENTER)
            return  this.centerChestItemList;
        return this.chestItemList;
    }
}

