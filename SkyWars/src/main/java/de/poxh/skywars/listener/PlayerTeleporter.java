package de.sw.listener;

import de.sw.api.ItemBuilderAPI;
import de.sw.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.Collections;

public class PlayerTeleporter implements Listener {

    private static ArrayList<Player> deathPlayers = new ArrayList<>();
    private static ArrayList<Player> alivePlayers = new ArrayList<>();
    private Main instance;


    public PlayerTeleporter(Main instance) {
        this.instance = instance;
    }

    @EventHandler
    private void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        alivePlayers.add(player);
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();

        alivePlayers.remove(player);
        deathPlayers.add(player);

        Bukkit.getScheduler().scheduleSyncDelayedTask(instance, () -> {
            player.spigot().respawn();
            player.getInventory().setItem(0, new ItemBuilderAPI(Material.COMPASS).setDisplayName("§eTeleporter").build());
            changeName(player, "§8[§c✘§8] §7");
            player.setDisplayName("§8[§c✘§8] §7" + player.getName());
        }, 2L);

        for (Player alive : alivePlayers) {
            for (Player dead : deathPlayers) {
                alive.hidePlayer(dead);
            }
        }
    }

    @EventHandler
    private void onDamage(EntityDamageByEntityEvent event) {
        try {
            Player player = (Player) event.getDamager();
            if (deathPlayers.contains(player)) {
                event.setCancelled(true);
            }
        }catch (ClassCastException e){}
    }


    @EventHandler
    private void onClick(InventoryClickEvent event) {
        if(event.getInventory().getTitle().equalsIgnoreCase("§bSpieler Teleporter")) {
            event.setCancelled(true);
            Player player = (Player) event.getWhoClicked();
            if(event.getSlotType() == InventoryType.SlotType.CONTAINER) {
                teleport(player, event.getCurrentItem());
            }
        }
    }

    @EventHandler
    private void onInteract(PlayerInteractEvent event) {
        try {
            if (event.getItem().getType() == Material.COMPASS) {
                if(event.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("§eTeleporter")) {
                    Inventory inventory = Bukkit.createInventory(null, 36, "§bSpieler Teleporter");
                    for (Player players : alivePlayers) {
                        inventory.addItem(getSkull(players.getDisplayName()));
                    }
                    event.getPlayer().openInventory(inventory);
                }
            }
        } catch (NullPointerException e){}
    }

    private static void teleport(Player player, ItemStack itemStack) {
        if(itemStack.getType() != Material.AIR && itemStack != null) {
            SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();

            if(skullMeta.getOwner() != null) {
                if(Bukkit.getPlayer(skullMeta.getOwner()) != null) {
                    Player target = Bukkit.getPlayer(skullMeta.getOwner());
                    player.teleport(target.getLocation());
                }
            }
        }
    }

    private static ItemStack getSkull(String name) {
        ItemStack skull = new ItemStack(397, 1, (short) 3);
        SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
        skullMeta.setDisplayName("§7" + name + " §a✔");
        skullMeta.setLore(Collections.singletonList("§8➥ §eLinksklick zum teleportieren"));
        skullMeta.setOwner(name);
        skull.setItemMeta(skullMeta);
        return  skull;
    }

    private static void changeName(Player player, String prefix) {
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
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
