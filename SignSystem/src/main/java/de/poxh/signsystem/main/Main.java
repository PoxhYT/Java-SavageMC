package de.poxh.signsystem.main;

import de.poxh.signsystem.manager.PingServer;
import de.poxh.signsystem.manager.ServerSign;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Main extends JavaPlugin {

    public static ArrayList<ServerSign> serversigns = new ArrayList<>();

    public static String prefix = "§cSignSystem §7❘ §7» ";

    public static HashMap<String, String> servers = new HashMap<>();
    public static HashMap<String, String> serverInfo = new HashMap<>();

    public static File serverfile = new File("./plugins/SignSystem", "server.yml");
    public static FileConfiguration server = YamlConfiguration.loadConfiguration(serverfile);

    public static File signfile = new File("./plugins/SignSystem", "signs.yml");
    public static FileConfiguration sign = YamlConfiguration.loadConfiguration(signfile);

    public static Main instance;


    @Override
    public void onDisable() {

    }


    @Override
    public void onEnable() {
        loadFiles();
        servers = loadServers();
        if(serverfile.exists()) {
            pingServer();
            Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> {
                //Update the ServerSigns
            }, 20L);
        }
    }

    public void loadFiles() {
        loadServerFile();
        loadSignFile();
    }

    public void loadServerFile() {
        File serverfile = new File("./plugins/SignSystem", "server.yml");
        FileConfiguration server = YamlConfiguration.loadConfiguration(serverfile);
        server.addDefault("Server.Adress", "localhost:25565");
        server.addDefault("Server.ServerName", "Lobby-1");
        server.options().copyDefaults(true);
        try {
            server.save(serverfile);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadSignFile() {
        File signfile = new File("./plugins/SignSystem", "signs.yml");
        FileConfiguration signs = YamlConfiguration.loadConfiguration(signfile);
        try {
            signs.save(signfile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private HashMap<String, String> loadServers() {
        HashMap<String, String> serverinfo = new HashMap<>();
        for (String s : server.getConfigurationSection("Server").getKeys(false)) {
            serverinfo.put(server.getString("Server." + s + ".ServerName"), server.getString("Server." + s + ".Adress"));
        }

        return serverinfo;
    }

    private void pingServer() {
        new BukkitRunnable() {
            @Override
            public void run() {
                for(String server : servers.keySet()) {

                    String[] ip = servers.get(server).split(":");
                    String address = ip[0];
                    int port = Integer.valueOf(ip[1]);

                    PingServer pingServer = new PingServer(address, port);
                    pingServer.fetchData();
                    int onlinePlayers = pingServer.getOnlinePlayers();
                    int maxPlayers = pingServer.getMaxPlayers();
                    String MOTD = pingServer.getMotd();
                    boolean online = pingServer.isOnline();

                    serverInfo.put(server, online + "/" + onlinePlayers + "/" + maxPlayers + "/" + MOTD);
                }
            }
        }.runTaskTimer(Main.getInstance(), 0L, 40L);
    }

    public void signUpdate() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable() {
            @Override
            public void run() {
                List<String> signs = sign.getStringList("String");
                for (String sign : signs) {
                    String [] infos = sign.split(",");
                    String servername = infos[0];
                    String world = infos[1];
                    double x = Double.valueOf(infos[2]);
                    double y = Double.valueOf(infos[3]);
                    double z = Double.valueOf(infos[4]);
                    Location location = new Location(Bukkit.getWorld(world), x, y, z);

                    if(location.getBlock().getState() instanceof Sign) {
                        Sign s = (Sign)location.getBlock().getState();
                        String[] serverinfos = serverInfo.get(servername).split("/");

                        int onlinePlayers = Integer.valueOf(serverinfos)
                    }
                }

            }
        }, 0, 20);
    }


    public static Main getInstance() {
        return instance;
    }
}
