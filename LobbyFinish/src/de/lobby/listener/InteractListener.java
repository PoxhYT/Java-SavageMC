package de.lobby.listener;

import de.lobby.inventories.LobbySwitcherInventory;
import de.lobby.inventories.NavigatorInventory;
import de.lobby.inventories.SettingsInventory;
import de.lobby.main.Main;
import de.lobby.utils.SpigotProxyAPI;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class InteractListener implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getItem() != null) {
            if (event.getItem().getType() == Material.COMPASS) {
                if (event.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8✘ §r§5Navigator §8✘")) {
                    player.openInventory(NavigatorInventory.navigatorInventory);
                    NavigatorInventory.navigatorFill();
                    player.playSound(player.getLocation(), Sound.CHICKEN_EGG_POP, 1, 1);
                }
            }
        }

        if(event.getItem()!= null) {
            if (event.getItem().getType() == Material.NETHER_STAR) {
                if (event.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8✘ §r§bLobbySwitcher §8✘")) {
                    player.openInventory(LobbySwitcherInventory.lobbySwitchInventory);
                    LobbySwitcherInventory.lobbySwitchFill();
                    player.playSound(player.getLocation(), Sound.CHICKEN_EGG_POP, 1, 1);
                }
            }
        }

        if(event.getItem()!= null) {
            if (event.getItem().getType() == Material.CHEST) {
                if (event.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8✘ §r§eGadgets §8✘")) {
                    player.sendMessage(Main.prefix + "§r§cDiese Funktion ist noch in Entwicklung.");
                    player.playSound(player.getLocation(), Sound.ANVIL_BREAK, 1, 1);
                }
            }
        }

        if(event.getItem()!= null) {
            if (event.getItem().getType() == Material.REDSTONE_COMPARATOR) {
                if (event.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8✘ §r§eEinstellungen §8✘")) {
                    player.playSound(player.getLocation(), Sound.ANVIL_BREAK, 1, 1);
                    player.playSound(player.getLocation(), Sound.CHICKEN_EGG_POP, 1, 1);
                    player.openInventory(SettingsInventory.settingsInventory);
                    SettingsInventory.settingsFill();
                }
            }
        }

        try {
            if (event.getItem().getType() == Material.TNT && (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)) {
                player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 3.0F, 2.0F);
                if (!Main.SilentLobby) {
                    player.sendMessage(Main.prefix + "§r§7Du wirst mit der §r§4SilentLobby §r§everbunden§7...");
                    try {
                        SpigotProxyAPI.sendPlayer(player, "SilentLobby-1");
                    } catch (Exception ex) {
                        player.sendMessage(Main.prefix + "§r§7Es konnte keine §r§4SilentLobby §r§7gefunden werden...");
                    }
                } else {
                    player.sendMessage(Main.prefix + "§r§7Du wirst mit der §r§4SilentLobby §r§everbunden§7...");
                    try {
                        SpigotProxyAPI.sendPlayer(player, "SilentLobby-1");
                    } catch (Exception ex) {
                        player.sendMessage("§r§cEs ist ein Fehler aufgetreten");
                    }
                }
            }
        } catch (Exception exception) {}
    }
}
