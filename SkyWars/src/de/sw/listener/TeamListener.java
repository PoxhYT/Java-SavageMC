package de.sw.listener;

import de.sw.api.LocationAPI;
import de.sw.main.Main;
import de.sw.manager.ItemBuilderAPI;
import de.sw.manager.KitManager;
import de.sw.manager.TeamManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.scoreboard.Scoreboard;

import java.io.File;
import java.util.ArrayList;

public class TeamListener implements Listener {

    private static Scoreboard sb;

    private static File file = new File("plugins/SkyWars", "Config.yml");
    private static YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);

    private static TeamManager[] manager = new TeamManager[8];

    @EventHandler
    private void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if(event.getItem().getType() == Material.BED) {
            open8x1Inventory(player);
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        try {
            TeamManager[] teams = get8x1Teams();

            for (int i = 0; i < teams.length; i++) {
                if (event.getCurrentItem().getItemMeta().getDisplayName().equals(teams[i].getTeamName())) {
                    player.sendMessage(Main.prefix + "Du bist dem " + teams[i].getTeamName() + " §7beigetreten!");
                    player.setDisplayName(teams[i].getTeamPrefix() + " §8| §7" + player.getName());
                    player.playSound(player.getLocation(), Sound.LEVEL_UP, 1, 1);
                    player.closeInventory();

                }
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        sb = Bukkit.getScoreboardManager().getNewScoreboard();

        sb.registerNewTeam("00000Team1");
        sb.registerNewTeam("00001Team2");
        sb.registerNewTeam("00002Team3");
        sb.registerNewTeam("00003Team4");
        sb.registerNewTeam("00004Team5");
        sb.registerNewTeam("00005Team6");
        sb.registerNewTeam("00006Team7");
        sb.registerNewTeam("00007Team8");

        sb.getTeam("00000Team1").setPrefix("§eTeam1 §7❘ ");
        sb.getTeam("00001Team2").setPrefix("§9Mod §7❘ ");
        sb.getTeam("00002Team3").setPrefix("§3Sup §7❘ ");
        sb.getTeam("00003Team4").setPrefix("§bDev §7❘ ");
        sb.getTeam("00004Team5").setPrefix("§aBuilder §7❘ ");
        sb.getTeam("00005Team6").setPrefix("§5YouTuber §7❘ ");
        sb.getTeam("00006Team7").setPrefix("§5Savage §7❘ ");
        sb.getTeam("00007Team8").setPrefix("§dUltra §7❘ ");

        String team = "";

        if (event.getCurrentItem().getItemMeta().getDisplayName().equals("§eTeam1")) {
            team = "00000Team1";
        }
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        event.setFormat(player.getDisplayName() + " §7» §7" + event.getMessage());
    }

    private void openTeamInventory(Player player) {
        try {
            String size = yamlConfiguration.getString("gameSize");
            if(size.equals("8x1")) {
                open8x1Inventory(player);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private void open8x1Inventory(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 9, "§eTeamauswahl");
        TeamManager[] teams = get8x1Teams();
        for (int i = 0; i < teams.length; i++)
        {
            inventory.setItem(i, new ItemBuilderAPI(teams[i].getMaterial()).setDisplayName(teams[i].getTeamName()).build());
        }
        player.openInventory(inventory);
    }

    private TeamManager[] get8x1Teams() {
        manager[0] = (new TeamManager("§eTeam1", "§eTeam1", 4, Material.WOOL));
        manager[1] = (new TeamManager("§eTeam2", "§eTeam2", 1, Material.WOOL));
        manager[2] = (new TeamManager("§eTeam3", "§eTeam3", 1, Material.WOOL));
        manager[3] = (new TeamManager("§eTeam4", "§eTeam4", 1, Material.WOOL));
        manager[4] = (new TeamManager("§eTeam5", "§eTeam5", 1, Material.WOOL));
        manager[5] = (new TeamManager("§eTeam6", "§eTeam6", 1, Material.WOOL));
        manager[6] = (new TeamManager("§eTeam7", "§eTeam7", 1, Material.WOOL));
        manager[7] = (new TeamManager("§eTeam8", "§eTeam8", 1, Material.WOOL));
        return manager;
    }
}
