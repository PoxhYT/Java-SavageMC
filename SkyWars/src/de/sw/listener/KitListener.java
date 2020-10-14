package de.sw.listener;

import de.sw.main.Main;
import de.sw.manager.InventoryManager;
import de.sw.manager.ItemBuilderAPI;
import de.sw.manager.KitManager;
import de.sw.manager.TeamManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;

public class KitListener implements Listener {

    private static KitManager[] arr = new KitManager[3];

    private static ArrayList<Player> players = new ArrayList<>();


    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        try{
            if(event.getItem().getType() == Material.CHEST) {
                openKitInventory(player);
            }
        }catch (NullPointerException e) {}
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        try{
            KitManager[] kits = getKits();

            for (int i = 0; i < kits.length; i++)
            {
                Inventory inventory = Bukkit.createInventory(null, 9, "§e" + kits[i].getKit());
                inventory.setItem(4, new ItemBuilderAPI(kits[i].getKitIcon()).setDisplayName("§e" + kits[i].getKit()).setLore(kits[i].getKitDescription()).build());
                inventory.setItem(0, new ItemBuilderAPI(Material.BARRIER).setDisplayName("§cZurück").build());
                inventory.setItem(8, new ItemBuilderAPI(Material.EMERALD).setDisplayName("§aKit auswählen").build());
                if(event.getCurrentItem().getType() == Material.BARRIER) {
                    openKitInventory(player);
                    player.playSound(player.getLocation(), Sound.CHICKEN_EGG_POP, 1, 1);
                }
                if(event.getCurrentItem().getType() == kits[i].getKitIcon()) {
                    player.sendMessage(Main.prefix + "Du hast das " + kits[i].getKit() + " §eKit §7ausgewählt!");
                    player.playSound(player.getLocation(), Sound.LEVEL_UP, 1, 1);
                    player.getInventory().setItem(8, new ItemBuilderAPI(kits[i].getKitIcon()).setDisplayName(kits[i].getKit()).build());
                    player.closeInventory();

                }
            }
        }catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
    
    public void openKitInventory(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 36, "§eKits");
        KitManager[] kits = getKits();
        for (int i = 0; i < kits.length; i++)
        {
            inventory.setItem(i, new ItemBuilderAPI(kits[i].getKitIcon()).setDisplayName(kits[i].getKitName()).setLore(kits[i].getKitDescription()).build());
        }
        player.openInventory(inventory);
    }

    private KitManager[] getKits() {

        arr[0] = (new KitManager("Standard §8[§aGekauft§8]", new String[]{"§7Du startest mit §e1 Eisenschwert§7,",
                "§e1 Eisenspitzhacke§7, §e1 Eisenaxt"}, Material.IRON_PICKAXE, 0, "Standart",
                "Standard §8[§cNicht gekauft§8]")
        );
        arr[1] = (new KitManager("Maurer §8[§aGekauft§8]",
                new String[] {"§8✘ §eAusrüstung",
                        "§8✘ §764 Ziegelsteine",
                        "§8✘ §7Goldhelm mit Schutz III",
                        "§8✘ §7Goldspitzhacke mit Effizienz III und Haltbarkeit II"},
                Material.BRICK, 10000, "Maurer", "Maurer §8[§cNicht gekauft§8]")
        );
        arr[2] = (new KitManager("Healer §8[§aGekauft§8]",
                new String[] {"§8✘ §eAusrüstung",
                        "§8✘ §73x Heilungstränke mit Heilung II",
                        "§8✘ §71 Regenerationstrank für 1:30 Minuten"},
                Material.GHAST_TEAR, 5000, "Healer", "Heiler §8[§cNicht gekauft§8]")
        );
        return arr;
    }

    private void setIngameItems(Player player) {
        if(players.contains(player)) {
        }
    }
}

