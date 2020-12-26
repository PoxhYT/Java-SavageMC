package de.sw.listener;

import com.rosemite.services.main.MainService;
import com.rosemite.services.models.skywars.PlayerSkywarsStats;
import de.sw.api.ItemBuilderAPI;
import de.sw.enums.Path;
import de.sw.main.Main;
import de.sw.manager.*;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.cacheddata.CachedMetaData;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class TeamListener implements Listener {

    private static File fileSkyWars = new File("plugins/SkyWars", "MapData.yml");
    private static YamlConfiguration yamlConfigurationSkyWars = YamlConfiguration.loadConfiguration(fileSkyWars);
    private HashMap<UUID, PlayerSkywarsStats> stats = new HashMap<>();
    private LuckPerms luckPerms;
    private Main instance;
    private MainService services;
    private final SkyWarsMapData data;

    public TeamListener(SkyWarsMapData data, LuckPerms luckPerms) {
        this.data = data;
        this.luckPerms = luckPerms;
        Main.instance.teams = new TeamManager[data.maxTeamCount];
        this.services = MainService.getService(services);

        for (int i = 0; i < Main.instance.teams.length; i++) {
            if (data.locations == null) {
                return;
            }
            Main.instance.teams[i] = new TeamManager("§eTeam" + (i+1), "§eTeam", data.locations[i], Material.WOOL, data.maxPlayersInTeam);
        }
    }

//    private String teamItemDisplayName = "§8[§a" + Main.instance.teams.length + "§7/§c" + (data != null).maxTeamCount;


    @EventHandler
    public void onClick(InventoryClickEvent event) {
        try {
            Player player = (Player) event.getWhoClicked();
            for (int i = 0; i < Main.instance.teams.length; i++) {
                if (event.getInventory().getTitle().equals("§eTeamauswahl")) {
                    String displayName = Main.instance.teams[i].getTeamName() + " §8[§a" + Main.instance.teams[i].getPlayers().size() + "§7/§c"
                            + Main.instance.teams[i].maxPlayerCount + "§8]";
                    if (event.getCurrentItem().getItemMeta().getDisplayName().equals(displayName)) {
                        boolean team = Main.instance.teams[i].isInTeam(player);

                        String t = Main.teamManagerMap.get(player.getUniqueId());

                        if (!team) {
                            if (t != null) {
                                removePlayerFromTeam(player, t);
                            }

                            String teamDisplayName = Main.instance.teams[i].getTeamName();
                            player.sendMessage(Main.prefix + "Du bist im " + Main.instance.teams[i].getTeamName());
                            player.setDisplayName(teamDisplayName);
                            Main.instance.teams[i].addPlayer(player);
                            Main.teamManagerMap.put(player.getUniqueId(), Main.instance.teams[i].getTeamName());
                            player.closeInventory();
                        }

                        if (team) {
                            player.sendMessage(Main.prefix + "Du bist bereits im " + Main.instance.teams[i].getTeamName());
                        } else {
                            if (!Main.instance.teams[i].isFull()) {
                                break;
                            }
                            break;
                        }
                    }
                }
            }
        } catch (NullPointerException e){}
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        try {
            Player d = event.getEntity();
            Player k = d.getKiller();

            if (k != null) {
                event.setDeathMessage(Main.prefix + d.getName() + " §7wurde von " + k.getName() + " §cgetötet§7!");
                Main.alivePlayers.remove(d);
                Bukkit.broadcastMessage(Main.prefix + "Es leben noch " + Main.alivePlayers.size() + "§7!");

                PlayerSkywarsStats stats = Main.stats.get(k.getUniqueId());

                if (stats == null) {
                    stats = new PlayerSkywarsStats(k.getName(), k.getUniqueId().toString());
                }

                stats.addKills(1);
                Main.stats.put(k.getUniqueId(), stats);

                Main.instance.sbManager.setIngameBoard(k);
            } else {
                event.setDeathMessage(Main.prefix + d.getName() + " §7ist gestorben!");
                Main.alivePlayers.remove(d);
            }


            for (int i = 0; i < Main.instance.teams.length; i++) {
                int finalI = i;
                Main.instance.teams[i].getPlayers().forEach(player -> {
                    if(player.getUniqueId() == d.getUniqueId()) {
                        PlayerSkywarsStats stats = Main.stats.get(d.getUniqueId());

                        if (stats == null) {
                            stats = new PlayerSkywarsStats(d.getName(), d.getUniqueId().toString());
                        }
                        stats.addDeaths(1);
                        stats.addPlayedGames(1);

                        Main.stats.put(UUID.fromString(stats.getUuid()), stats);
                        Main.instance.teams[finalI].spectators.add(player.getUniqueId());
                    }
                });
            }

            for (int i = 0; i < Main.instance.teams.length; i++) {
                if(Main.instance.teams[i].getSpectators().size() == Main.instance.teams[i].getPlayers().size()) {
                    Main.instance.teams[i].setAlive(false);
                }
            }

            int aliveTeams = 0;
            int winnerTeam = -1;
            for (int i = 0; i < Main.instance.teams.length; i++) {
                if(Main.instance.teams[i].getAlive()) {
                    winnerTeam = i;
                    ++aliveTeams;
                }
            }

            if(aliveTeams == 1) {
                for (Player all : Bukkit.getOnlinePlayers()) {
                    CachedMetaData metaData = luckPerms.getPlayerAdapter(Player.class).getMetaData(all);
                    String prefix = metaData.getPrefix();

                    if (k != null) {
                        all.sendTitle(prefix + " §7❘ " + all.getName(), "§ahat das Spiel gewonnen");
                    } else {
                        all.sendTitle(Main.alivePlayers.get(0).getDisplayName(), "§ahat das Spiel gewonnen");
                    }

                    Main.instance.teams[winnerTeam].players.forEach(p -> {
                        PlayerSkywarsStats stats = Main.stats.get(p.getUniqueId());

                        if (stats == null) {
                            stats = new PlayerSkywarsStats(p.getName(), p.getUniqueId().toString());
                            stats.addWins(1);
                            if (stats.getPlayedGames() != 1) {
                                stats.addPlayedGames(1);
                            }
                            stats.addPoints(100);
                        }

                        Main.stats.put(UUID.fromString(stats.getUuid()), stats);
                    });
                }

                if(!Main.endingCountdown.isRunning()) {
                    Main.endingCountdown.start();

                    Main.stats.forEach((uuid, playerSkywarsStats) -> services.getSkywarsService().addPlayerStats(uuid.toString(), playerSkywarsStats));
                }
            }
        } catch (NullPointerException e) { }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        try {
            if(event.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8» §bTeams")) {
                openTeamInventory(event.getPlayer());
            } else if(event.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8» §dAchievements")) {
                player.sendMessage(Main.prefix + "§cIst noch in bearbeitung!");
                player.playSound(player.getLocation(), Sound.VILLAGER_DEATH, 1, 1);
            }
        } catch (NullPointerException e) { }
    }

    private void removePlayerFromTeam(Player player, String teamname) {
        for (int i = 0; i < Main.instance.teams.length; i++) {
            if (Main.instance.teams[i].getTeamName() == teamname) {
                Main.instance.teams[i].removePlayer(player);
            }
        }
    }


    // [2/4]
    public void openTeamInventory(Player player) {



        String size = (String) Main.MapName1.get(Path.GameSize.toString());
        Integer maxTeams = (Integer) Main.MapName1.get(Path.MaxTeamCount.toString());
        Integer maxPlayersInTeam = (Integer) Main.MapName1.get(Path.MaxPlayersInTeam.toString());

        int chestSize = Integer.valueOf(size.split("x")[0]) > 8 ? 18:9;
        Inventory inventory = Bukkit.createInventory(null, chestSize, "§eTeamauswahl");

        if (size.equals(size)) {
            for (int i = 0; i < maxTeams; i++) {
                List<Player> players = Main.instance.teams[i].getPlayers();
                String[] names = new String[players.size()];

                // Set names
                for (int j = 0; j < names.length; j++) {
                    names[j] = players.get(j).getName();
                }

                // Add Players to TeamList
                String[] playersInTeam = new String[players.size()];
                for (int j = 0; j < names.length; j++) {
                    playersInTeam[j] = "§7- §f" + names[j];
                }

                inventory.setItem(i, new ItemBuilderAPI(Material.WOOL).setDisplayName("§eTeam" + (i + 1) + " §8[§a" + Main.instance.teams[i].getPlayers().size() + "§7/§c"
                        + maxPlayersInTeam + "§8]").setLore((playersInTeam)).build());
                player.openInventory(inventory);
            }
        }
    }
}
