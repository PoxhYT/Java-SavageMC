package de.sw.listener;

import com.rosemite.services.helper.Log;
import com.rosemite.services.main.MainService;
import de.sw.main.Main;
import de.sw.manager.ItemBuilderAPI;
import de.sw.manager.KitManager;
import javafx.util.Pair;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class KitListener implements Listener {
    public Map<UUID, KitManager> kitMap = new HashMap<>();
    private MainService service;

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        try {
            if(event.getItem().getItemMeta().getDisplayName().equals("§8» §eKits")) {
                openKitInventory(player);
            }
        } catch (NullPointerException e) {}
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        service = MainService.getService(service);

        Player player = event.getPlayer();
        List<KitManager> kits = defaultKits();

        String latestKit = service.getSkywarsService().getLatestSelectedKit(player.getUniqueId().toString());

        kits.forEach(kit -> {
            if (latestKit.equals(kit.getKitNameLiteralString())) {
                player.getInventory().setItem(8, new ItemBuilderAPI(kit.getKitIcon()).setDisplayName(kit.getKitNameLiteralStringColored()).build());
            }
        });
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        KitManager[] kits = getKits(player);

        for (int i = 0; i < kits.length; i++) {
            Inventory inventory = Bukkit.createInventory(null, 9, "§e" + kits[i].getKitName());
            inventory.setItem(4, new ItemBuilderAPI(kits[i].getKitIcon()).setDisplayName("§e" + kits[i].getKitName()).setLore(kits[i].getKitDescription()).build());
            inventory.setItem(0, new ItemBuilderAPI(Material.BARRIER).setDisplayName("§cZurück").build());
            inventory.setItem(8, new ItemBuilderAPI(Material.EMERALD).setDisplayName("§aKit auswählen").build());

            if (event.getInventory().getTitle().equals("§eKits")) {
                if (event.getCurrentItem().getType() == kits[i].getKitIcon()) {
                    if (kits[i].getHasKit()) {
                        Inventory inventory1 = Bukkit.createInventory(null, 9, kits[i].getKitNameLiteralStringColored());
                        inventory1.setItem(8, new ItemBuilderAPI(Material.WOOL, (short)5).setDisplayName("§aAuswählen").setLore("§aKlicke, um das Kit zu wählen").build());
                        inventory1.setItem(4, new ItemBuilderAPI(kits[i].getKitIcon()).setDisplayName(kits[i].getKitNameLiteralStringColored()).setLore(kits[i].getKitDescription()).build());
                        inventory1.setItem(0, new ItemBuilderAPI(Material.WOOL, (short)14).setDisplayName("§cZürück").setLore("§cKlicke, um die Auswahl zu beenden.").build());
                        player.playSound(player.getLocation(), Sound.CHICKEN_EGG_POP, 1, 1);
                        player.openInventory(inventory1);
                    } else {
                        if (event.getInventory().getTitle().equals("§eKits")) {
                            if (event.getCurrentItem().getType() == kits[i].getKitIcon()) {
                                Inventory inventory2 = Bukkit.createInventory(null, 9, "§e" + kits[i].getKitNameLiteralString());
                                inventory2.setItem(8, new ItemBuilderAPI(kits[i].getKitIcon()).setDisplayName("§aKaufen").setLore("§cKlicke, um das Kit zu kaufen.").build());
                                inventory2.setItem(4, new ItemBuilderAPI(Material.GOLD_INGOT).setDisplayName("§cPreis: " + Integer.toString(kits[i].getKitPrice()) + " §cCoins").build());
                                inventory2.setItem(0, new ItemBuilderAPI(Material.WOOL, (short) 14).setDisplayName("§cAbbrechen").setLore("§cKlicke, um den Kauf zu beenden.").build());
                                player.openInventory(inventory2);
                            }
                        }
                    }
                }
            }
            
            if(event.getInventory().getTitle().equals("§e" + kits[i].getKitNameLiteralString())) {
                if (event.getCurrentItem().getItemMeta().getDisplayName().equals("§aKaufen")) {
                    String uuid = player.getUniqueId().toString();
                    int amount = kits[i].getKitPrice();
                    Pair<Integer, Boolean> result = service.getCoinService().removeCoins(uuid, amount);

                    if (result.getValue()) {
                        // Buy Kit
                        service.getSkywarsService().buyKit(uuid, kits[i].getKitNameLiteralString());
                        player.sendMessage(Main.prefix + "Du hast das " + kits[i].getKitNameLiteralStringColored() + " gekauft!");
                        player.playSound(player.getLocation(), Sound.LEVEL_UP, 1, 1);
                        player.closeInventory();
                    } else {
                        // Player does not have enough coins to by this kit
                        player.sendMessage(Main.prefix + ChatColor.RED + "Du hast nicht genügend coins um dir das " + kits[i].getKitNameLiteralStringColored() + ChatColor.RED + " kit zu kaufen!");
                        player.playSound(player.getLocation(), Sound.CHICKEN_EGG_POP, 1, 1);
                    }
                }
            }

            if (event.getInventory().getTitle().equals(kits[i].getKitNameLiteralStringColored())) {
                if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§aAuswählen")) {
                    kitMap.put(player.getUniqueId(), kits[i]);
                    player.sendMessage(Main.prefix + "Du hast das " + kits[i].getKitNameLiteralStringColored() + " §eKit §7ausgewählt!");
                    player.getInventory().setItem(8, new ItemBuilderAPI(kits[i].getKitIcon()).setDisplayName(kits[i].getKitNameLiteralStringColored()).build());
                    player.playSound(player.getLocation(), Sound.LEVEL_UP, 1, 1);
                    player.closeInventory();
                    service.getSkywarsService().updateLatestSelectedKit(player.getUniqueId().toString(), kits[i].getKitNameLiteralString());
                } else {
                    if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§cAbbrechen")) {
                        openSelectInventory(player);
                    }
                }
            }
        }
    }

    private void openBuyInventory(Player player) {
        KitManager[] kits = getKits(player);
        for (int i = 0; i < kits.length; i++)
        {
            try {
                Inventory inventory = Bukkit.createInventory(null, 9, "§e" + kits[i].getKitNameLiteralString());
                inventory.setItem(8, new ItemBuilderAPI(kits[i].getKitIcon()).setDisplayName("§aKaufen").setLore("§cKlicke, um das Kit zu kaufen.").build());
                inventory.setItem(0, new ItemBuilderAPI(Material.WOOL, (short)14).setDisplayName("§cAbbrechen").setLore("§cKlicke, um den Kauf zu beenden.").build());
                player.openInventory(inventory);
            } catch (Exception e) {}
        }
    }

    private void openSelectInventory(Player player) {
        KitManager[] kits = getKits(player);
        for (int i = 0; i < kits.length; i++)
        {
            try {
                Inventory inventory = Bukkit.createInventory(null, 9, "§e" + kits[i].getKitNameLiteralStringColored());
                inventory.setItem(8, new ItemBuilderAPI(Material.WOOL, (short)5).setDisplayName("§aAuswählen").setLore("§cKlicke, um das Kit .").build());
                inventory.setItem(4, new ItemBuilderAPI(kits[i].getKitIcon()).setDisplayName(kits[i].getKitNameLiteralString()).setLore(kits[i].getKitDescription()).build());
                inventory.setItem(0, new ItemBuilderAPI(Material.WOOL, (short)14).setDisplayName("§cZürück").setLore("§cKlicke, um die Auswahl zu beenden.").build());
                player.openInventory(inventory);
            } catch (Exception e) {}
        }
    }

    public void openKitInventory(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 36, "§eKits");
        KitManager[] kits = getKits(player);

        for (int i = 0; i < kits.length; i++)
        {
            try {
                inventory.setItem(i, new ItemBuilderAPI(kits[i].getKitIcon()).setDisplayName(kits[i].getKitName()).setLore(kits[i].getKitDescription()).build());
            } catch (Exception e) {}
        }
        player.openInventory(inventory);
    }

    private List<KitManager> defaultKits() {
        return service.getSkywarsService().getDefaultKits();
    }

    private KitManager[] getKits(Player player) {
        service = MainService.getService(service);
        List<KitManager> kits = service.getSkywarsService().getDefaultKits();

        kits = service.getSkywarsService().verifyKits(kits, player.getUniqueId().toString());

        return kits.toArray(new KitManager[0]);
    }
}
