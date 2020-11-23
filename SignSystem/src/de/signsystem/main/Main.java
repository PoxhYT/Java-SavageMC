package de.signsystem.main;

import de.gamestateapi.main.GameStateAPIManager;
import de.signsystem.api.ServerSign;
import de.signsystem.command.Command_setsign;
import de.signsystem.events.SetupListener;
import de.signsystem.events.SignInteractListener;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.bukkit.Bukkit;
import org.bukkit.block.Sign;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.*;
import org.bukkit.plugin.java.JavaPlugin;
import java.io.DataOutputStream;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Main extends JavaPlugin {

    //Strings
    public static String prefix = "§eSignSystem §7❘ §7";
    public static String noPerms = prefix + "§cDazu hast du nicht genügend Rechte!";
    private GameStateAPIManager gameStateAPIManager;
    public static String setup = "";

    //Instances
    public static Main instance;

    //HashMap
    public static HashMap<String, Boolean> maintenance = new HashMap<>();

    //File
    public static File file = new File("plugins//SignSystem", "settings.yml");
    public static YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);

    //Lists
    public static ArrayList<ServerSign> serversigns = new ArrayList<>();


    @Override
    public void onEnable() {
        init();
    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage(prefix + "§cDas Plugin wurde erfolgreich beendet!");
    }

    public static void connect(Player p, String server) {
        try {
            ByteArrayOutputStream b = new ByteArrayOutputStream();
            DataOutputStream out = new DataOutputStream(b);
            out.writeUTF("Connect");
            out.writeUTF(server);
            p.sendPluginMessage((Plugin)getInstance(), "BungeeCord", b.toByteArray());
        } catch (IOException iOException) {}
    }

    private void init() {
        registerCommands();
        registerEvents();
        instance = this;
    }


    private void registerCommands() {
        getCommand("addsign").setExecutor(new Command_setsign());
    }

    private void registerEvents() {
        PluginManager manager = Bukkit.getPluginManager();
        manager.registerEvents(new SetupListener(), this);
        manager.registerEvents(new SignInteractListener(), this);
    }

    public static Main getInstance() {
        return instance;
    }

    public static void clearSign(Sign sign) {
        sign.setLine(0, "");
        sign.setLine(1, "");
        sign.setLine(2, "");
        sign.setLine(3, "");
    }
}
