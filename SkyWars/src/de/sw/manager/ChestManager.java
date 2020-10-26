package de.sw.manager;

import de.sw.enums.ChestType;
import de.sw.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChestManager {

    private Map<Integer, Inventory> basicChestList = new HashMap<>();
    private Map<Integer, Inventory> centerChestList = new HashMap<>();
    private final Map<Integer, Inventory> crateItemList = new HashMap<>();

    public ChestManager() {
        load(this.basicChestList, "basicchest");
        load(this.centerChestList, "centerchest");
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
                    if (isInteger(key)) {
                        int percent = Integer.parseInt(key);
                        List<ItemStack> items = (List<ItemStack>) yamlConfiguration.getList("chestItems." + key + ".items");
                        if (!itemList.containsKey(Integer.valueOf(percent)))
                            itemList.put(Integer.valueOf(percent), Bukkit.createInventory(null, 54, fileName + " " + percent));
                        for (ItemStack iStack : items) {
                            if (iStack != null)
                                ((Inventory) itemList.get(Integer.valueOf(percent))).addItem(new ItemStack[]{iStack});
                        }
                    }
                }
        }
    }

    private Map<Integer, Inventory> getItemMap(ChestType chestType) {
        if(chestType == ChestType.BASIC) {
            return  this.basicChestList;
        }
        return null;
    }

    public boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
