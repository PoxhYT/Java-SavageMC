package de.sw.listener;

import de.sw.countdown.LobbyCountdown;
import de.sw.gameManager.Lobby_State;
import de.sw.main.Main;
import de.sw.manager.InventoryManager;
import de.sw.manager.ItemBuilderAPI;
import de.sw.manager.UtilsManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.io.File;

public class PlayerConnectionEvent implements Listener {

    private Main instance;

    private static File file = new File("plugins/SkyWars", "Config.yml");

    private static YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);

    private static InventoryManager inventoryManager;

    @EventHandler
    public void onJoin(org.bukkit.event.player.PlayerJoinEvent event) {
        if(!(Main.instance.getGameStateManager().getCurrentGame_State() instanceof Lobby_State)) return;
        Player player = event.getPlayer();
        Main.instance.players.add(player);

        Integer MAX_PLAYERS = yamlConfiguration.getInt("maxPlayers");
        Integer MIN_PLAYERS = yamlConfiguration.getInt("minplayers");

        player.getInventory().setItem(0, new ItemBuilderAPI(Material.BED).setDisplayName("§8» §bTeams").build());
        player.getInventory().setItem(3, new ItemBuilderAPI(Material.CHEST).setDisplayName("§8» §eKits").build());
        player.getInventory().setItem(5, new ItemBuilderAPI(Material.NETHER_STAR).setDisplayName("§8» §dAchievements").build());
        player.getInventory().setItem(8, new ItemBuilderAPI(Material.MAGMA_CREAM).setDisplayName("§8» §cVerlassen").build());

        event.setJoinMessage(Main.prefix + "§8» §e" + player.getDisplayName() + " §7hat das Spiel betreten! §7[§a"
                + Main.instance.players.size() + "§7/§c" + MAX_PLAYERS + "§7]");

        Lobby_State lobbyState = (Lobby_State) Main.instance.getGameStateManager().getCurrentGame_State();
        LobbyCountdown countdown = lobbyState.getCountdown();
        player.setLevel(60);
        if(Main.instance.players.size() >= MIN_PLAYERS) {
            if(!countdown.isRunning()) {
                countdown.stopIdle();
                countdown.start();
            }
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        if(!(Main.instance.getGameStateManager().getCurrentGame_State() instanceof Lobby_State)) return;
        Player player = event.getPlayer();
        Main.instance.players.remove(player);

        Integer MAX_PLAYERS = yamlConfiguration.getInt("maxPlayers");
        Integer MIN_PLAYERS = yamlConfiguration.getInt("minplayers");

        event.setQuitMessage(Main.prefix + "§8» §e" + player.getDisplayName() + " §7hat das Spiel verlassen! §7[§a"
                + Main.instance.players.size()+ "§7/§c" + MAX_PLAYERS + "§7]");

        Lobby_State lobbyState = (Lobby_State) Main.instance.getGameStateManager().getCurrentGame_State();
        LobbyCountdown countdown = lobbyState.getCountdown();
        if(Main.instance.players.size() < MIN_PLAYERS) {
            if(countdown.isRunning()) {
                countdown.stop();
                countdown.startIdle();
            }
        }
    }
}
