package de.loop.main;

import de.loop.commands.Command_addItem;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Main extends JavaPlugin {

    //Instances
    public static Main instance;

    //Strings
    public static String prefix = "§eRandomChestItems §8» §7";

    //List
    public static List<ItemStack> itemStackList = new ArrayList<>();

    //YamlConfigurations
    public static File file = new File("plugins/RandomChestItems", "RandomChestItems.yml");
    public static FileConfiguration yamlConfiguration = (FileConfiguration) YamlConfiguration.loadConfiguration(file);

    @Override
    public void onEnable() {
        init();
    }

    //This is the mainFunction of the class
    private void init() {
        Bukkit.getConsoleSender().sendMessage(prefix + "§eDas Plugin wurde erfolgreich gestartet!");
        registerCommands();
        sendMessage(itemStackList.size());
    }

    //This is going to register all events in the current function
    private void registerEvents() {

    }

    //This is going to register all commands in the current function
    private void registerCommands() {
        getCommand("addItem").setExecutor(new Command_addItem());
    }

    public void load(String fileName) {
        File files = new File("plugins/RandomChestItems", fileName);
        if (!files.exists())
            Main.instance.saveResource(fileName, false);
        Bukkit.getConsoleSender().sendMessage(Main.prefix + "§eDie Yml-Datei : " + fileName + " §ewurde erfolgreich erstellt!");
        if (files.exists()) {
            YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(files);
            Bukkit.getConsoleSender().sendMessage(Main.prefix + "§eAlle Yml's wurden aktuallisiert!");
        }
    }

    public void loadFiles() {
        load("Items.yml");
    }

    public static void sendMessage(Object message) { Bukkit.getConsoleSender().sendMessage("§6[Log]: §e" + message); }
    public static void sendWarning(Object message) { Bukkit.getConsoleSender().sendMessage("§c[Log] Warning: §e" + message); }
}
