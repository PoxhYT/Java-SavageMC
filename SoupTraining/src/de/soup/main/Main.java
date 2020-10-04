package de.soup.main;

import de.soup.commands.SoupCommand;
import de.soup.events.SoupListener;
import de.soup.storage.Item;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    public static String prefix = "§cSoupTraining §8❘ §7";

    public static Main instance;

    public void onEnable() {
        instance = this;
        Bukkit.getPluginManager().registerEvents((Listener)new SoupListener(), (Plugin)this);
        getCommand("soup").setExecutor((CommandExecutor)new SoupCommand());
        loadItems();
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
