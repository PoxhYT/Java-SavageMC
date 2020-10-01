package de.lobby.inventories;

import de.lobby.main.Main;
import de.lobby.mysql.PremiumLobbyAutoConnect;
import de.lobby.mysql.SilentLobbyAutoConnect;
import de.lobby.mysql.TeamLobbyAutoConnect;
import de.lobby.utils.ItemAPI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class ConnectionInventory {

    public static Inventory serverSettingsInventory = Bukkit.createInventory(null, 27, "§8• §r§bServer§7 Settings §8•");

    public static void serverFill(Player p) {
        for (int i = 0; i < serverSettingsInventory.getSize(); i++)
            serverSettingsInventory.setItem(i, new ItemAPI(Material.STAINED_GLASS_PANE, (short)15).setName("§r").build());

        if (PremiumLobbyAutoConnect.isExists(p.getUniqueId().toString())) {
            if (PremiumLobbyAutoConnect.getSetting(p.getUniqueId().toString()).intValue() == 1) {
                serverSettingsInventory.setItem(11, new ItemAPI(Material.NETHER_STAR).setName("§r§eAuto§7-§r§eConnect§7: §r§ePremiumLobby §8[§a✔§8]").setLore("§r§7Klicke, um §r§eAuto§7-§r§eConnect §r§7zur §r§ePremiumLobby §r§7zu §r§cdeaktivieren§7.").build());
            } else if (PremiumLobbyAutoConnect.getSetting(p.getUniqueId().toString()).intValue() == 0) {
                serverSettingsInventory.setItem(11, new ItemAPI(Material.NETHER_STAR).setName("§r§eAuto§7-§r§eConnect§7: §r§ePremiumLobby §8[§c✘§8]").setLore("§r§7Klicke, um §r§eAuto§7-§r§eConnect §r§7zur §r§ePremiumLobby §r§7zu §r§aaktivieren§7.").build());
            } else {
                p.closeInventory();
                p.sendMessage(Main.prefix + "Es ist ein §r§cFehler §r§7aufgetreten.");
            }
        } else {
            serverSettingsInventory.setItem(11, new ItemAPI(Material.NETHER_STAR).setName("§r§eAuto§7-§r§eConnect§7: §r§ePremiumLobby §8[§c✘§8]").setLore("§r§7Klicke, um §r§eAuto§7-§r§eConnect §r§7zur §r§ePremiumLobby §r§7zu §r§aaktivieren§7.").build());
        }

        if (SilentLobbyAutoConnect.isExists(p.getUniqueId().toString())) {
            if (SilentLobbyAutoConnect.getSetting(p.getUniqueId().toString()).intValue() == 1) {
                serverSettingsInventory.setItem(13, new ItemAPI(Material.NETHER_STAR).setName("§r§eAuto§7-§r§eConnect§7: §r§4SilentLobby §8[§a✔§8]").setLore("§r§7Klicke, um §r§eAuto§7-§r§eConnect §r§7zur §r§4SilentLobby §r§7zu §r§cdeaktivieren§7.").build());
            } else if (SilentLobbyAutoConnect.getSetting(p.getUniqueId().toString()).intValue() == 0) {
                serverSettingsInventory.setItem(13, new ItemAPI(Material.NETHER_STAR).setName("§r§eAuto§7-§r§eConnect§7: §r§4SilentLobby §8[§c✘§8]").setLore("§r§7Klicke, um §r§eAuto§7-§r§eConnect §r§7zur §r§4SilentLobby §r§7zu §r§aaktivieren§7.").build());
            } else {
                p.closeInventory();
                p.sendMessage(Main.prefix + "Es ist ein §r§cFehler §r§7aufgetreten.");
            }
        } else {
            serverSettingsInventory.setItem(13, new ItemAPI(Material.NETHER_STAR).setName("§r§eAuto§7-§r§eConnect§7: §r§4SilentLobby §8[§c✘§8]").setLore("§r§7Klicke, um §r§eAuto§7-§r§eConnect §r§7zur §r§4SilentLobby §r§7zu §r§aaktivieren§7.").build());
        }

        if (TeamLobbyAutoConnect.isExists(p.getUniqueId().toString())) {
            if (TeamLobbyAutoConnect.getSetting(p.getUniqueId().toString()).intValue() == 1) {
                serverSettingsInventory.setItem(15, new ItemAPI(Material.NETHER_STAR).setName("§r§eAuto§7-§r§eConnect§7: §r§5TeamLobby §8[§a✔§8]").setLore("§r§7Klicke, um §r§eAuto§7-§r§eConnect §r§7zur §r§5TeamLobby §r§7zu §r§cdeaktivieren§7.").build());
            } else if (TeamLobbyAutoConnect.getSetting(p.getUniqueId().toString()).intValue() == 0) {
                serverSettingsInventory.setItem(15, new ItemAPI(Material.NETHER_STAR).setName("§r§eAuto§7-§r§eConnect§7: §r§5TeamLobby §8[§c✘§8]").setLore("§r§7Klicke, um §r§eAuto§7-§r§eConnect §r§7zur §r§5TeamLobby §r§7zu §r§aaktivieren§7.").build());
            } else {
                p.closeInventory();
                p.sendMessage(Main.prefix + "Es ist ein §r§cFehler §r§7aufgetreten.");
            }
        } else {
            serverSettingsInventory.setItem(15, new ItemAPI(Material.NETHER_STAR).setName("§r§eAuto§7-§r§eConnect§7: §r§5TeamLobby §8[§c✘§8]").setLore("§r§7Klicke, um §r§eAuto§7-§r§eConnect §r§7zur §r§5TeamLobby §r§7zu §r§aaktivieren§7.").build());
        }
        p.openInventory(ConnectionInventory.serverSettingsInventory);
    }
}
