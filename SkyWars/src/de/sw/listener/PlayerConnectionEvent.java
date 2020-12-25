package de.sw.listener;

import de.poxh.services.main.MainService;
import de.sw.api.LocationAPI;
import de.sw.enums.Path;
import de.sw.main.Main;
import de.sw.manager.*;
import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.io.File;
import java.util.Random;

public class PlayerConnectionEvent implements Listener {

    private Main instance;
    private LuckPerms luckPerms;
    private MainService service;

    private static File file = new File("plugins/SkyWars", "config.yml");
    private static YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);
    private static InventoryManager inventoryManager;
    private static boolean joinMessage = false;

    public PlayerConnectionEvent(Main instance, LuckPerms luckPerms) {
        service = MainService.getService(service);
        this.instance = instance;
        this.luckPerms = luckPerms;
    }

    @EventHandler
    public void onJoin(org.bukkit.event.player.PlayerJoinEvent event) {
        Player player = event.getPlayer();

        int MAX_PLAYERS = yamlConfiguration.getInt(Path.MaxPlayers.toString());
        int MIN_PLAYERS = yamlConfiguration.getInt(Path.MaxPlayersInTeam.toString());

//        CachedMetaData metaData = luckPerms.getPlayerAdapter(Player.class).getMetaData(player);
//        String prefix = metaData.getPrefix();

        player.getInventory().clear();
        Main.getInstance().getInventoryManager().setLobbyInventory(player);

        Main.alivePlayers.add(player);

        player.teleport(LocationAPI.getSpawn("Lobby"));

        event.setJoinMessage(Main.prefix + "§8» §e" + player.getName() + " §7hat das Spiel betreten! §7[§a"
                + Main.alivePlayers.size() + "§7/§c" + MAX_PLAYERS + "§7]");

        //Adding player to ScoreBoard
        for (int i = 0; i < Main.instance.teams.length; i++) {
            changeName(player, Main.instance.teams[i].getTeamName());
        }


        Main.getInstance().sbManager.setLobbyBoard(player);


        Random chance = new Random();
        int chanceNumber = 100;
        int randomChance = chance.nextInt(chanceNumber);

        //Creates a random int between 1 - 100


        player.sendMessage(Main.prefix + "§7Aktuelle Map: §e" + Main.MapName1.get(Path.MapName.toString()));
        player.sendMessage(Main.prefix + "§7Spielvariante: §8(§e" + Main.MapName1.get(Path.GameSize.toString()) + "§8)");

        player.setLevel(60);
        player.setFoodLevel(20);
        if (Main.alivePlayers.size() >= MIN_PLAYERS) {
            if(!Main.instance.countdown.isRunning()) {
                Main.instance.countdown.stopIdle();
                Main.instance.countdown.start();
            }
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        Main.alivePlayers.remove(player);

        Integer MAX_PLAYERS = yamlConfiguration.getInt(Path.MaxPlayers.toString());
        Integer MIN_PLAYERS = yamlConfiguration.getInt(Path.MinPlayers.toString());

        event.setQuitMessage(Main.prefix + "§8» §e" + player.getDisplayName() + " §7hat das Spiel verlassen! §7[§a"
                + Main.alivePlayers.size()+ "§7/§c" + MAX_PLAYERS + "§7]");
        if(Main.alivePlayers.size() < MIN_PLAYERS) {
            if(Main.instance.countdown.isRunning()) {
                Main.instance.countdown.stop();
                Main.instance.countdown.startIdle();
            }
        }
    }

    private static void changeName(Player player, String prefix) {
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
        Team team = scoreboard.getTeam(prefix);
        if(team == null) {
            team = scoreboard.registerNewTeam(prefix);
        }
        team.setPrefix(prefix);
        team.addPlayer(player);
        for (Player players : Bukkit.getOnlinePlayers()) {
            players.setScoreboard(scoreboard);
        }
    }
}
