package de.signsystem.api;

import de.gamestateapi.main.GameStateAPIManager;
import de.signsystem.main.Main;
import de.signsystem.main.Var;
import net.minecraft.server.v1_8_R3.World;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.configuration.ConfigurationSection;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class ServerSign {

    private static Main main = Main.getInstance();

    private static HashMap<Integer, GameStateAPIManager> status = new HashMap<>();

    private Location location;

    private Sign sign;

    private int port;

    private String ip;

    private String name;

    private String group;

    private int id;

    private String statusText;

    private boolean maintenance;

    public ServerSign(Location location) {
        this.location = location;
        this.sign = (Sign)location.getBlock().getState();
    }

    public ServerSign(Location location, int port, String ip, String name, String group, int uuid, boolean maintenancebol) {
        this.port = port;
        this.ip = ip;
        this.name = name;
        this.group = group;
        this.location = location;
        this.id = uuid;
        this.sign = (Sign)location.getBlock().getState();
        this.maintenance = maintenancebol;
        if (Main.maintenance.containsKey(group)) {
            if (((Boolean)Main.maintenance.get(group)).booleanValue()) {
                status.put(Integer.valueOf(uuid), GameStateAPIManager.LOBBY);
            } else {
                status.put(Integer.valueOf(uuid), GameStateAPIManager.Offline);
            }
        } else {
            Main.maintenance.put(group, Boolean.valueOf(maintenancebol));
            if (maintenancebol) {
                status.put(Integer.valueOf(uuid), GameStateAPIManager.Maintenance);
            } else {
                status.put(Integer.valueOf(uuid), GameStateAPIManager.Offline);
            }
        }
    }

    public ServerSign(Location location, int port, String ip, String name, String group, boolean mainten) {
        this.port = port;
        this.ip = ip;
        this.name = name;
        this.group = group;
        this.location = location;
        this.sign = (Sign)location.getBlock().getState();
        this.maintenance = mainten;
    }

    public void setID(int uuid) {
        this.id = uuid;
    }

    public Integer getID() {
        return Integer.valueOf(this.id);
    }

    public void setState(GameStateAPIManager state) {
        status.put(Integer.valueOf(this.id), state);
    }

    public void setMaintenanceStatusAfter() {
        if (Main.maintenance.containsKey(this.group)) {
            if (((Boolean)Main.maintenance.get(this.group)).booleanValue()) {
                status.put(Integer.valueOf(this.id), GameStateAPIManager.Maintenance);
            } else {
                status.put(Integer.valueOf(this.id), GameStateAPIManager.Offline);
            }
        } else {
            Main.maintenance.put(this.group, Boolean.valueOf(this.maintenance));
            main.getConfig().set(getID() + ".info.maintenance", Boolean.valueOf(this.maintenance));
            main.saveConfig();
            if (this.maintenance) {
                status.put(Integer.valueOf(this.id), GameStateAPIManager.Maintenance);
            } else {
                status.put(Integer.valueOf(this.id), GameStateAPIManager.Offline);
            }
        }
    }

    public String getName() {
        return this.name;
    }

    public boolean getMaintenance() {
        return this.maintenance;
    }

    public String getIP() {
        return this.ip;
    }

    public String getGroup() {
        return this.group;
    }

    public Integer save() {
        int i = main.getConfig().getKeys(false).size() + 1;
        main.getConfig().set(String.valueOf(i) + ".info.name", this.name);
        main.getConfig().set(String.valueOf(i) + ".info.ip", this.ip);
        main.getConfig().set(String.valueOf(i) + ".info.port", Integer.valueOf(this.port));
        main.getConfig().set(String.valueOf(i) + ".info.group", this.group);
        main.getConfig().set(String.valueOf(i) + ".location.world", this.location.getWorld().getName());
        main.getConfig().set(String.valueOf(i) + ".location.x", Integer.valueOf(this.location.getBlockX()));
        main.getConfig().set(String.valueOf(i) + ".location.y", Integer.valueOf(this.location.getBlockY()));
        main.getConfig().set(String.valueOf(i) + ".location.z", Integer.valueOf(this.location.getBlockZ()));
        main.saveConfig();
        return Integer.valueOf(i);
    }

    public GameStateAPIManager getState() {
        return status.get(Integer.valueOf(this.id));
    }

    public ServerSign getServerSign() {
        return new ServerSign(this.location, this.port, this.ip, this.name, this.group, this.id, this.maintenance);
    }

    public String getStatusText() {
        return this.statusText;
    }

    public void setStatusText(String text) {
        this.statusText = text;
    }

    public void update() {
        if (!((GameStateAPIManager)ServerSign.status.get(Integer.valueOf(this.id))).equals(GameStateAPIManager.Maintenance)) {
            Socket socket = new Socket();
            try {
                socket.connect(new InetSocketAddress(this.ip, this.port));
                InputStream in = socket.getInputStream();
                OutputStream out = socket.getOutputStream();
                out.write(254);
                StringBuilder sb = new StringBuilder();
                int i;
                while ((i = in.read()) != -1) {
                    if (i != 0 && i > 16 && i != 255 && i != 23 && i != 24)
                        sb.append((char)i);
                }
                String[] data = sb.toString().split("");
                        String motd = data[0];
                String mesh = String.valueOf(data[1]) + data[2];
                byte blockbyte = 0;
                int onlinePlayers = Integer.valueOf(data[1]).intValue();
                int maxPlayers = Integer.valueOf(data[2]).intValue();
                String fetch_motd = motd.toLowerCase();
                String status = "";
                String motd_state = "";
                if (fetch_motd.contains("lobby")) {
                    if (onlinePlayers >= maxPlayers) {
                        motd_state = Var.FULL_LOBBY;
                    } else {
                        motd_state = Var.LOBBY;
                    }
                    motd.replace("LOBBY", "");
                    motd.replace("Lobby", "");
                    motd.replace("lobby", "");
                } else {
                    if (fetch_motd.contains("ingame") || fetch_motd.contains("game")) {
                        setState(GameStateAPIManager.Maintenance);
                        if (!Var.HIDEINGAMESERVERS) {
                            status = String.valueOf(motd_state) + ";" + onlinePlayers + " / " + maxPlayers + ";" + motd;
                            setStatusText(status);
                        }
                        return;
                    }
                    motd_state = Var.UNKNOWN;
                }
                status = String.valueOf(motd_state) + ";" + onlinePlayers + " / " + maxPlayers + ";" + motd;
                setStatusText(status);
                setState(GameStateAPIManager.Offline);
                socket.close();
            } catch (IOException error) {
                setState(GameStateAPIManager.Offline);
            }
        } else {
            setState(GameStateAPIManager.Maintenance);
        }
    }

    public Location getLocation() {
        return this.location;
    }

    public Sign getSign() {
        return this.sign;
    }

    public static ArrayList<ServerSign> getSigns() {
        ArrayList<ServerSign> list = new ArrayList<>();
        for (String x : main.getConfig().getKeys(false)) {
            ConfigurationSection section = main.getConfig().getConfigurationSection(x);
            ConfigurationSection cs = section.getConfigurationSection("location");
            World world = (World) Bukkit.getServer().getWorld(cs.getString("world"));
            Location location = new Location((org.bukkit.World) world, cs.getDouble("x"), cs.getDouble("y"), cs.getDouble("z"));
            Block b = location.getBlock();
            if (b != null) {
                ConfigurationSection info = section.getConfigurationSection("info");
                list.add(new ServerSign(location, info.getInt("port"), info.getString("ip"), info.getString("name"), info.getString("group"), Integer.parseInt(x), info.getBoolean("maintenance")));
                continue;
            }
            main.getConfig().set(x, null);
        }
        return list;
    }
}

