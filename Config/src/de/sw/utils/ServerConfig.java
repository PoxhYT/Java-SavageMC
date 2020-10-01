package de.sw.utils;

import de.sw.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ServerConfig {

    private Main instance;

    private File file;

    private FileConfiguration fileConfiguration;

    private String hostname;

    private String database;

    private int port;

    private String username;

    private String password;

    public ServerConfig(Main instance) {
        this.instance = instance;
        this.file = new File("./plugins/Config/", "Config.yml");
        this.fileConfiguration = YamlConfiguration.loadConfiguration(this.file);
        setStandard();
        load();
    }

    private void setStandard() {
        if (this.fileConfiguration.get("Teams") != null)
            return;
        this.fileConfiguration.set("Teams", 8);
        this.fileConfiguration.set("PlayersInTeams", 1);
        this.fileConfiguration.set("AllKitsPerm", "skywars.kits");
        this.fileConfiguration.set("Hostname", "localhost");
        this.fileConfiguration.set("Size", "8x1");
        this.fileConfiguration.set("Datenbank", "skywars");
        this.fileConfiguration.set("Port", Integer.valueOf(3306));
        this.fileConfiguration.set("Benutzername", "root");
        this.fileConfiguration.set("Passwort", "bruh1234");
        try {
            this.fileConfiguration.save(this.file);
            Bukkit.getConsoleSender().sendMessage(Main.prefix + "§eDie Config wurde erstellt!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void load() {
        instance.teams = this.fileConfiguration.getInt("Teams");
        this.instance.playerInTeams = this.fileConfiguration.getInt("PlayersInTeams");
        this.instance.allKitsPermission = this.fileConfiguration.getString("AllKitForFreeWithThisPermission");
        this.hostname = this.fileConfiguration.getString("Hostname");
        this.database = this.fileConfiguration.getString("DataBase");
        this.port = this.fileConfiguration.getInt("Port");
        this.username = this.fileConfiguration.getString("Username");
        this.password = this.fileConfiguration.getString("Password");
    }

    public String getHostname() {
        return this.hostname;
    }

    public String getDatabase() {
        return this.database;
    }

    public int getPort() {
        return this.port;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }
}

