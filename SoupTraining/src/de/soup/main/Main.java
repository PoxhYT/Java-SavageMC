package de.soup.main;

import de.soup.commands.Command_build;
import de.soup.commands.Command_setlocation;
import de.soup.commands.SoupCommand;
import de.soup.events.PlayerInteractListener;
import de.soup.events.PlayerJoinListener;
import de.soup.events.ProtectionListener;
import de.soup.events.SoupListener;
import de.soup.manager.SBManager;
import de.soup.storage.Item;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class Main extends JavaPlugin {

    public static String prefix = "§cSoupTraining §8❘ §7";

    public static String noPerms = Main.prefix + "§r§cDu hast keine Rechte§7, §r§cum dies auszuführen§7.";

    public static String notFound = Main.prefix + "§r§cDieser Spieler wurde nicht gefunden§7!";

    public static List<Player> build = new ArrayList<>();

    public static Main instance;

    public void onEnable() {
        instance = this;
        registerEvents();
        registerCommands();
        loadItems();
    }

    private void registerEvents() {
        final PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents((Listener) new PlayerJoinListener(), this);
        pluginManager.registerEvents((Listener) new SoupListener(), this);
        pluginManager.registerEvents((Listener) new ProtectionListener(), this);
        pluginManager.registerEvents((Listener) new PlayerInteractListener(), this);
    }

    private void registerCommands() {
        getCommand("soup").setExecutor((CommandExecutor)new SoupCommand());
        getCommand("setlocation").setExecutor((CommandExecutor)new Command_setlocation());
        getCommand("build").setExecutor((CommandExecutor)new Command_build());
    }

    private void loadItems() {
        ItemStack i = new ItemStack(Material.MUSHROOM_SOUP);
        ItemMeta im = i.getItemMeta();
        im.setDisplayName("§eSuppe");
        i.setItemMeta(im);
        Item.setItem(i);
    }

    public static void scoreCD() {
        Bukkit.getScheduler().scheduleAsyncRepeatingTask((Plugin)Main.getInstance(), new Runnable() {

            @Override
            public void run() {
                for (Player all : Bukkit.getOnlinePlayers()) {
                    SBManager.updateScoreboard(all);
                }
            }
        }, 0, 1);
    }

    public static Main getInstance() {
        return instance;
    }
}
