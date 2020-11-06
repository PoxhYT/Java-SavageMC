package de.sw.listener;

import com.rosemite.services.helper.Log;
import de.sw.main.Main;
import de.sw.manager.*;
import me.lucko.luckperms.common.api.LuckPermsApiProvider;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.cacheddata.CachedMetaData;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TeamListener implements Listener {

    private static File fileSkyWars = new File("plugins/SkyWars", "MapData.yml");
    private static YamlConfiguration yamlConfigurationSkyWars = YamlConfiguration.loadConfiguration(fileSkyWars);
    public static HashMap<String, Integer> kills = new HashMap<>();
    private static Map<UUID, String> teamManagerMap = new HashMap<>();
    private LuckPerms luckPerms;

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
                    String displayName = teams[i].getTeamName();
                    if (event.getCurrentItem().getItemMeta().getDisplayName().equals(displayName)) {
                        boolean team = teams[i].isInTeam(player);

                        String t = teamManagerMap.get(player.getUniqueId());

                        if (!team) {
                            if (t != null) {
                                removePlayerFromTeam(player, t);
                                Log.d("Du wurdest entfernt vom " + t);
                            }

                            Log.d("Du bist im " + teams[i].getTeamName());
                            String teamDisplayName = teams[i].getTeamName();
                            player.sendMessage(Main.prefix + "Du bist im " + teams[i].getTeamName());
                            player.setDisplayName(teamDisplayName);
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
        }catch (NullPointerException e){}
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        try {
            if (event.getEntity() instanceof Player) {
                Player player = (Player) event.getEntity();
                if (event.getDamager() instanceof Player) {
                    Player damager = (Player) event.getDamager();
                    String TeamDamager = getExactTeam(damager);
                    String TeamPlayer = getExactTeam(player);
                    if (TeamDamager.equalsIgnoreCase(TeamPlayer)) {
                        event.setCancelled(true);
                    } else {
                        event.setCancelled(false);
                    }
                }
            }
        }catch (NullPointerException e){}
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        try {
            Player d = event.getEntity();
            Player k = d.getKiller();
            event.setDeathMessage(Main.prefix + d.getDisplayName() + " §7ist gestorben!");
            Main.alivePlayers.remove(d);

            CachedMetaData metaData = luckPerms.getPlayerAdapter(Player.class).getMetaData(event.getEntity());
            String prefix = metaData.getPrefix();

            if (k != null) {
                event.setDeathMessage(Main.prefix + prefix + " §7❘ " + d.getName() + " §7wurde von " + prefix + " §7❘ " + k.getDisplayName() + " §cgetötet§7!");
            }
        }catch (NullPointerException e){}
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
        }catch (NullPointerException e) {}
    }

    private void removePlayerFromTeam(Player player, String teamname) {
        for (int i = 0; i < teams.length; i++) {
            if (teams[i].getTeamName() == teamname) {
                teams[i].removePlayer(player);
            }
        }
    }

    public String getExactTeam(Player player) {
        return teamManagerMap.get(player.getUniqueId());
    }

    // [2/4]
    public void openTeamInventory(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 9, "§eTeamauswahl");

        String size = data.gameSize;
        int teams1 = data.maxTeamCount;

        Bukkit.broadcastMessage(size);
        if(size.equals("8x1")) {
            for (int i = 0; i < teams1; i++) {
                inventory.setItem(i, new ItemBuilderAPI(Material.WOOL).setDisplayName("§eTeam" + (i + 1)).build());
                player.openInventory(inventory);
            }
        }
    }

    private void getTeams(Player player) {
        for (int i = 0; i < teams.length; i++) {

        }
    }
}
