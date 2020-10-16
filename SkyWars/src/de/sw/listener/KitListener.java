package de.sw.listener;

import com.rosemite.services.helper.Log;
import com.rosemite.services.main.MainService;
import de.sw.main.Main;
import de.sw.manager.ItemBuilderAPI;
import de.sw.manager.KitManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.List;

public class KitListener implements Listener {

    private static ArrayList<Player> players = new ArrayList<>();
    private MainService service;

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        try {
            if(event.getItem().getType() == Material.CHEST) {
                openKitInventory(player);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        try{
            KitManager[] kits = getKits(player);

            for (int i = 0; i < kits.length; i++)
            {
                Inventory inventory = Bukkit.createInventory(null, 9, "§e" + kits[i].getKitName());
                inventory.setItem(4, new ItemBuilderAPI(kits[i].getKitIcon()).setDisplayName("§e" + kits[i].getKitName()).setLore(kits[i].getKitDescription()).build());
                inventory.setItem(0, new ItemBuilderAPI(Material.BARRIER).setDisplayName("§cZurück").build());
                inventory.setItem(8, new ItemBuilderAPI(Material.EMERALD).setDisplayName("§aKit auswählen").build());

                if(event.getCurrentItem().getType() == Material.BARRIER) {
                    openKitInventory(player);
                    player.playSound(player.getLocation(), Sound.CHICKEN_EGG_POP, 1, 1);
                }

                if (event.getCurrentItem().getType() == kits[i].getKitIcon()) {
                    if (kits[i].getHasKit()) {
                        player.sendMessage(Main.prefix + "Du hast das " + kits[i].getKitNameLiteralStringColored() + " §eKit §7ausgewählt!");
                        player.playSound(player.getLocation(), Sound.LEVEL_UP, 1, 1);
                        player.getInventory().setItem(8, new ItemBuilderAPI(kits[i].getKitIcon()).setDisplayName(kits[i].getKitNameLiteralStringColored()).build());
                        player.closeInventory();
                    } else {
                        player.sendMessage(Main.prefix + ChatColor.RED + "Du hast das " + kits[i].getKitNameLiteralStringColored() + ChatColor.RED + " Kit §7nicht gekauft!");
                        player.playSound(player.getLocation(), Sound.CHICKEN_EGG_POP, 1, 1);
                        player.closeInventory();
                    }
                }
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
    
    public void openKitInventory(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 36, "§eKits");
        KitManager[] kits = getKits(player);

        for (int i = 0; i < kits.length; i++)
        {
            try {
                inventory.setItem(i, new ItemBuilderAPI(kits[i].getKitIcon()).setDisplayName(kits[i].getKitName()).setLore(kits[i].getKitDescription()).build());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        player.openInventory(inventory);
    }

    private KitManager[] getKits(Player player) {
        service = MainService.getService(service);

        List<KitManager> kits = service.getSkywarsService().getEveryKit();
        kits = service.getSkywarsService().verifyKits(kits, player.getUniqueId());

        return kits.toArray(new KitManager[0]);
    }

    private void setIngameItems(Player player) {
        if(players.contains(player)) {
        }
    }
}

