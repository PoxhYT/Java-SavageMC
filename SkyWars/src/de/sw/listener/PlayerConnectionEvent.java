package de.sw.listener;

import com.rosemite.services.helper.Log;
import com.rosemite.services.main.MainService;
import de.sw.countdown.LobbyCountdown;
import de.sw.enums.Path;
import de.sw.gameManager.Lobby_State;
import de.sw.main.Main;
import de.sw.manager.*;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.cacheddata.CachedMetaData;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.io.File;
import java.util.List;

public class PlayerConnectionEvent implements Listener {

    private Main instance;
    private LuckPerms luckPerms;
    private MainService service;

    private static File file = new File("plugins/SkyWars", "Config.yml");
    private static YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);
    private static InventoryManager inventoryManager;
    private static boolean joinMessage = false;

    public PlayerConnectionEvent(Main instance, LuckPerms luckPerms) {
        this.instance = instance;
        this.luckPerms = luckPerms;
    }

    @EventHandler
    public void onJoin(org.bukkit.event.player.PlayerJoinEvent event) {
        if(!(Main.instance.getGameStateManager().getCurrentGame_State() instanceof Lobby_State)) return;
        Player player = event.getPlayer();
        Main.instance.players.add(player);
        service = MainService.getService(service);

        Integer MAX_PLAYERS = yamlConfiguration.getInt("maxPlayers");
        Integer MIN_PLAYERS = yamlConfiguration.getInt("minplayers");

        CachedMetaData metaData = luckPerms.getPlayerAdapter(Player.class).getMetaData(player);
        String prefix = metaData.getPrefix();
        Main.getInstance().getInventoryManager().setLobbyInventory(player);

        Main.scoreCD();
        Main.alivePlayers.add(player);

        event.setJoinMessage(Main.prefix + "§8» §e" + prefix + " §7❘ " + player.getName() + " §7hat das Spiel betreten! §7[§a"
                + Main.instance.players.size() + "§7/§c" + MAX_PLAYERS + "§7]");
        Main.getInstance().sbManager.setLobbyBoard(player);

        player.sendMessage(Main.prefix + "§7Aktuelle Map: §e" + Main.MapName1.get(Path.MapName.toString()));
        player.sendMessage(Main.prefix + "§7Spielvariante: §8(§e" + Main.MapName1.get(Path.GameSize.toString()) + "§8)");

        Lobby_State lobbyState = (Lobby_State) Main.instance.getGameStateManager().getCurrentGame_State();
        LobbyCountdown countdown = lobbyState.getCountdown();
        player.setLevel(60);
        player.setFoodLevel(20);
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
