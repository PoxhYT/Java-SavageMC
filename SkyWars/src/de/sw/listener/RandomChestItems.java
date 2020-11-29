package de.sw.listener;

import com.rosemite.services.helper.Log;
import de.sw.main.Main;
import net.minecraft.server.v1_8_R3.World;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class RandomChestItems implements Listener {

    private static File file = new File("plugins/SkyWars", "Items.yml");

    private static FileConfiguration cfg = (FileConfiguration) YamlConfiguration.loadConfiguration(file);

    public static void saveCfg() {
        try {
            cfg.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private HashMap<Location, Inventory> chestInventory = new HashMap<>();

    //Creating random items in a chest
    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        try {
            Player player = event.getPlayer();

            if(event.getClickedBlock().getType() == Material.REDSTONE_BLOCK) {
                if(chestInventory.containsKey(event.getClickedBlock().getLocation())) {
                    player.openInventory(chestInventory.get(event.getClickedBlock().getLocation()));
                } else {
                    //Creating a new random
                    Random random = new Random();
                    int n = 1;
                    n = random.nextInt(6);

                    Inventory inventory = Bukkit.createInventory(null, InventoryType.CHEST);
                    Location location = event.getClickedBlock().getLocation();


                    for (String all : cfg.getStringList("Items")) {

                        Random r2 = new Random();
                        int n2 = r2.nextInt(27);

                        Random r3 = new Random();
                        int n3 = r3.nextInt(Main.items.size());

                        inventory.setItem(n2, Main.items.get(n3));
                    }

                    chestInventory.put(location, inventory);
                    player.openInventory(chestInventory.get(location));
                    return;
                }
            }

        }catch (NullPointerException e){}
    }
}
