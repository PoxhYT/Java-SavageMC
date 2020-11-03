package de.sw.listener;

import com.rosemite.services.helper.Log;
import com.rosemite.services.models.skywars.PlayerSkywarsStats;
import de.sw.enums.Path;
import de.sw.main.Main;
import de.sw.manager.*;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class TeamListener implements Listener {

    private static File fileSkyWars = new File("plugins/SkyWars", "MapData.yml");
    private static YamlConfiguration yamlConfigurationSkyWars = YamlConfiguration.loadConfiguration(fileSkyWars);
    public static HashMap<String, Integer> kills = new HashMap<>();
    private static Map<UUID, String> teamManagerMap = new HashMap<>();

    public TeamManager[] teams;
    private final SkyWarsMapData data;

    public TeamListener(SkyWarsMapData data) {
        this.data = data;
        teams = new TeamManager[data.maxTeamCount];

        for (int i = 0; i < teams.length; i++) {
                teams[i] = new TeamManager("§eTeam" + (i+1), "§eTeam", Material.WOOL, data.maxPlayersInTeam);
            }
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        try {
            Player player = (Player) event.getWhoClicked();
            for (int i = 0; i < teams.length; i++) {
                if (event.getInventory().getTitle().equals("§eTeamauswahl")) {
                    String displayName = "§e" + teams[i].getPlayers().size();
                    if (event.getCurrentItem().getItemMeta().getDisplayName().equals(displayName)) {
                        for (int j = 0; j < teams[i].getPlayers().size(); j++) {
                            Log.d(teams[i].getPlayers().get(j).getDisplayName());
                        }
                        boolean team = teams[i].isInTeam(player);

                        String t = teamManagerMap.get(player.getUniqueId());

                        if(!team) {
                            if(t != null) {
                                removePlayerFromTeam(player, t);
                                Log.d("Du wurdest entfernt vom " + t);
                            }

                            Log.d("Du bist im " + teams[i].getTeamName());
                            player.sendMessage(Main.prefix + "Du bist im " + teams[i].getTeamName());
                            teams[i].addPlayer(player);
                            teamManagerMap.put(player.getUniqueId(), teams[i].getTeamName());
                            player.closeInventory();
                        }

                        if (team) {
                            Log.d("Du bist schon in dem Team");
                            player.sendMessage(Main.prefix + "Du bist bereits im " + teams[i].getTeamName());
                        } else {
                            if (!teams[i].isFull()) {
                                Log.d("Du hast das Team: " + teams[i].getTeamName() + " idk...");
                                break;
                            }
                            break;
                        }
                    }
                }
            }
        } catch (NullPointerException e) {}
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        try {
            if (event.getAction().equals(Action.RIGHT_CLICK_AIR)) {
                if(event.getItem().getItemMeta().getDisplayName().equals("§8» §bTeams"))
                {
                    PlayerSkywarsStats stats = Main.roundKills.get(player.getUniqueId());

                    if (stats == null) {
                        stats = new PlayerSkywarsStats(player.getDisplayName(), player.getUniqueId().toString());
                    }

                    Main.roundKills.put(player.getUniqueId(), stats);
                }
                openTeamInventory(event.getPlayer());
            }
        }catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private void removePlayerFromTeam(Player player, String teamname) {
        for (int i = 0; i < teams.length; i++) {
            if (teams[i].getTeamName() == teamname) {
                teams[i].removePlayer(player);
            }
        }
    }


    // [2/4]
    public void openTeamInventory(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 9, "§eTeamauswahl");

        String size = data.gameSize;
        int teams1 = data.maxTeamCount;

        Bukkit.broadcastMessage(size);
        if(size.equals("8x1")) {
            for (int i = 0; i < teams1; i++) {
                inventory.setItem(i, new ItemBuilderAPI(Material.WOOL).setDisplayName("§eTeam" + (i + 1) + "§e" + teams[i].getPlayers().size()).build());
                player.openInventory(inventory);
            }
        }
    }

    private void getTeams() {

    }
}
