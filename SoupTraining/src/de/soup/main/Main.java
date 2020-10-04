package de.soup.main;

import de.soup.storage.Item;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    public static String prefix = "§cSoupTraining §8❘ §7";

    public static Main instance;

    public void onEnable() {
        instance = this;
    }

    private void loadItems() {
        ItemStack i = new ItemStack(Material.MUSHROOM_SOUP);
        ItemMeta im = i.getItemMeta();
        im.setDisplayName("§eSuppe");
        i.setItemMeta(im);
        Item.setItem(i);
    }

    public static Main getInstance() {
        return instance;
    }
}
