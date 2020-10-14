package de.sw.listener;

import com.rosemite.services.helper.Log;
import com.rosemite.services.main.MainService;
import de.sw.main.Main;
import de.sw.manager.ItemBuilderAPI;
import de.sw.manager.KitManager;
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
import java.util.List;

public class KitListener implements Listener {

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
            try {
                inventory.setItem(i, new ItemBuilderAPI(kits[i].getKitIcon()).setDisplayName(kits[i].getKitName()).setLore(kits[i].getKitDescription()).build());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        player.openInventory(inventory);
    }

    private KitManager[] getKits() {
        MainService service = MainService.getService(null);
        List<KitManager> kits = service.getSkywarsServices().getEveryKit();

        return kits.toArray(new KitManager[kits.size()]);
    }

    private void setIngameItems(Player player) {
        if(players.contains(player)) {
        }
    }
}

