package de.lobby.listener;

import de.lobby.inventories.ConnectionInventory;
import de.lobby.main.Main;
import de.lobby.mysql.PremiumLobbyAutoConnect;
import de.lobby.mysql.SilentLobbyAutoConnect;
import de.lobby.mysql.TeamLobbyAutoConnect;
import de.lobby.utils.ItemAPI;
import de.lobby.utils.SpigotProxyAPI;
import de.magnus.coinsapi.util.CoinsAPI;
import de.ticketapi.util.TicketAPI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;

public class InventoryClickListener implements Listener {



    @EventHandler
    public void onClickInv(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        try {
            if (event.getCurrentItem().getItemMeta().getDisplayName() == "§8• §r§bLobby§7-§b1 §8•") {
                player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 3.0F, 2.0F);
                player.sendMessage(Main.prefix + "§r§7Du wirst mit der §r§bLobby§7-§b1 §r§everbunden§7...");
                try {
                    SpigotProxyAPI.sendPlayer(player, "Lobby");
                } catch (Exception ex) {
                    player.sendMessage(Main.prefix + "§r§7Diese §r§eLobby §r§7ist derzeit nicht erreichbar...");
                }
            }
        } catch (Exception exception) {}

        try {
            if (event.getCurrentItem().getItemMeta().getDisplayName() == "§8• §r§ePremiumLobby§7-§e1 §8•") {
                player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 3.0F, 2.0F);
                player.sendMessage(Main.prefix + "§r§7Du wirst mit der §r§ePremiumLobby§7-§e1 §r§everbunden§7...");
                try {
                    SpigotProxyAPI.sendPlayer(player, "PremiumLobby-1");
                } catch (Exception ex) {
                    player.sendMessage(Main.prefix + "§r§7Diese §r§ePremiumLobby§7-§e1 §r§7ist derzeit nicht erreichbar...");
                }
            }
        } catch (Exception exception) {}

        try {
            if (event.getCurrentItem().getItemMeta().getDisplayName() == "§8• §r§5TeamLobby§7-§r§51 §8•") {
                player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 3.0F, 2.0F);
                player.sendMessage(Main.prefix + "§r§7Du wirst mit der §r§5TeamLobby§7-§r§51 §r§everbunden§7...");
                try {
                    SpigotProxyAPI.sendPlayer(player, "TeamLobby-1");
                } catch (Exception ex) {
                    player.sendMessage(Main.prefix + "§r§7Diese §r§5TeamLobby§7-§r§51 §r§7ist derzeit nicht erreichbar...");
                }
            }
        } catch (Exception exception) {}

        if (event.getClickedInventory().getName().equals("§8• §r§9Einstellungen §8•")) {
            if (event.getCurrentItem().getType() == Material.NETHER_STAR) {
                if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8• §r§bServer Settings §8•")) {
                    player.openInventory(ConnectionInventory.serverSettingsInventory);
                    ConnectionInventory.serverFill(player);
                    player.playSound(player.getLocation(), Sound.CLICK, 1, 1);
                }
            }
        }

        if (event.getClickedInventory().getName().equals("§8• §r§bLobbySwitcher §8•")) {
            if (event.getCurrentItem().getType() == Material.REDSTONE_COMPARATOR) {
                if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8• §r§5ServerSettings §8•")) {
                    player.openInventory(ConnectionInventory.serverSettingsInventory);
                    ConnectionInventory.serverFill(player);
                    player.playSound(player.getLocation(), Sound.CLICK, 1, 1);
                }
            }
        }

        if(event.getCurrentItem() == null) {
            event.setCancelled(true);
        }

        if(event.getCurrentItem().getItemMeta().getDisplayName() == "§eSpawn") {
            player.performCommand("warp Main");
        }

        if (event.getCurrentItem().getItemMeta().getDisplayName() == "§r§eAuto§7-§r§eConnect§7: §r§ePremiumLobby §8[§c✘§8]") {
            if (player.hasPermission("server.premium")) {
                player.closeInventory();
                player.playSound(player.getLocation(), Sound.LEVEL_UP, 3.0F, 2.0F);
                PremiumLobbyAutoConnect.create(player.getUniqueId().toString(), player.getName(), 1);
                PremiumLobbyAutoConnect.setSetting(player.getUniqueId().toString(), 1);
                player.sendMessage(Main.prefix + "Du hast den §r§eAuto§7-§r§eConnect §7zur §r§ePremiumLobby §r§aaktiviert§7.");

            } else {
                player.sendMessage(Main.prefix + Main.buyRank);
            }
        } else if (event.getCurrentItem().getItemMeta().getDisplayName() == "§r§eAuto§7-§r§eConnect§7: §r§ePremiumLobby §8[§a✔§8]") {
            player.closeInventory();
            player.playSound(player.getLocation(), Sound.LEVEL_UP, 1, 1);
            PremiumLobbyAutoConnect.setSetting(player.getUniqueId().toString(), 0);
            player.sendMessage(Main.prefix + "Du hast den §r§eAuto§7-§r§eConnect §7zur §r§ePremiumLobby §r§cdeaktiviert§7.");
        }

        if (event.getCurrentItem().getItemMeta().getDisplayName() == "§r§eAuto§7-§r§eConnect§7: §r§4SilentLobby §8[§c✘§8]") {
            if (player.hasPermission("server.premium")) {
                player.closeInventory();
                player.playSound(player.getLocation(), Sound.LEVEL_UP, 3.0F, 2.0F);
                SilentLobbyAutoConnect.create(player.getUniqueId().toString(), player.getName(), 1);
                SilentLobbyAutoConnect.setSetting(player.getUniqueId().toString(), 1);
                player.sendMessage(Main.prefix + "Du hast den §r§eAuto§7-§r§eConnect §7zur §r§4SilentLobby §r§aaktiviert§7.");

            } else {
                player.sendMessage(Main.prefix + Main.buyRank);
            }
        } else if (event.getCurrentItem().getItemMeta().getDisplayName() == "§r§eAuto§7-§r§eConnect§7: §r§4SilentLobby §8[§a✔§8]") {
            player.closeInventory();
            player.playSound(player.getLocation(), Sound.LEVEL_UP, 1, 1);
            SilentLobbyAutoConnect.setSetting(player.getUniqueId().toString(), 0);
            player.sendMessage(Main.prefix + "Du hast den §r§eAuto§7-§r§eConnect §7zur §r§4SilentLobby §r§cdeaktiviert§7.");
        }

        if (event.getInventory().getName().equals("") &&
                event.getCurrentItem().getType() == Material.GOLD_INGOT) {
            player.performCommand("reward");
            player.closeInventory();
            player.playSound(player.getLocation(),Sound.LEVEL_UP, 1, 1);
        }

        if (event.getCurrentItem().getItemMeta().getDisplayName() == "§r§eAuto§7-§r§eConnect§7: §r§5TeamLobby §8[§c✘§8]") {
            if (player.hasPermission("server.premium")) {
                player.closeInventory();
                player.playSound(player.getLocation(), Sound.LEVEL_UP, 3.0F, 2.0F);
                TeamLobbyAutoConnect.create(player.getUniqueId().toString(), player.getName(), 1);
                TeamLobbyAutoConnect.setSetting(player.getUniqueId().toString(), 1);
                player.sendMessage(Main.prefix + "Du hast den §r§eAuto§7-§r§eConnect §7zur §r§5TeamLobby §r§aaktiviert§7.");

            } else {
                player.sendMessage(Main.prefix + Main.buyRank);
            }
        } else if (event.getCurrentItem().getItemMeta().getDisplayName() == "§r§eAuto§7-§r§eConnect§7: §r§5TeamLobby §8[§a✔§8]") {
            player.closeInventory();
            player.playSound(player.getLocation(), Sound.LEVEL_UP, 1, 1);
            TeamLobbyAutoConnect.setSetting(player.getUniqueId().toString(), 0);
            player.sendMessage(Main.prefix + "Du hast den §r§eAuto§7-§r§eConnect §7zur §r§5TeamLobby §r§cdeaktiviert§7.");
        }
    }
}






