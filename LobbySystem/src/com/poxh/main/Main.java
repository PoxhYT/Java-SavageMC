package com.poxh.main;

import com.poxh.api.LocationAPI;
import com.poxh.commands.Command_build;
import com.poxh.listener.PlayerChatEvent;
import com.poxh.listener.PlayerJoinListener;
import com.poxh.manager.InventoryManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class Main extends JavaPlugin {

    //Strings
    public static String prefix = "§5SavageMC §7❙ ";
    public static String noPerms = prefix + "§cDu hast leider nicht genügende Rechte um dies auszuführen!";
    public static String usage = prefix + "§cBitte benutze den Befehl: ";

    //boolean
    public static Boolean existLobby = false;

    //Instances
    public static Main instance;

    //Objects
    public static InventoryManager inventoryManager = new InventoryManager();

    //ArrayList
    public static List<Player> build = new ArrayList<>();


    @Override
    public void onEnable() {
        init();
    }

    @Override
    public void onDisable() {
    }

    private void init() {
        registerEvents();
        Bukkit.getConsoleSender().sendMessage(Main.prefix + "§eDas Plugin wurde erfolgreich getstartet!");
        registerCommands();

        if(LocationAPI.file.exists()) {
            existLobby = true;
        } else {
            existLobby = false;
        }

        if(!existLobby) {
            sendWarning("Bitte setzte eine Lobby damit das System funktioniert!");
        }
    }

    //registerCommands
    private void registerCommands(){
        getCommand("build").setExecutor(new Command_build());
    }

    //registerListener
    private void registerEvents() {
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new PlayerChatEvent(), this);
        pluginManager.registerEvents(new PlayerJoinListener(), this);
    }


    private void sendMessage(Object message) {
        Bukkit.getConsoleSender().sendMessage("§8[§eLOG§8] » §e" + message);
    }

    private void sendWarning(Object message) {
        Bukkit.getConsoleSender().sendMessage("§8[§cWARNING§8] » §e" + message);
    }
}
