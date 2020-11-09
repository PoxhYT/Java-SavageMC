package de.sw.gameManager;

import java.util.ArrayList;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;

public class MapReseter {
    public static ArrayList<Location> locations = new ArrayList<>();

    public static void ResetMap() {
        try {
            for (Location loc : locations) {
                if (loc.getBlock().getType() == Material.AIR)
                    return;
                loc.getBlock().setType(Material.AIR);
            }
        } catch (Exception e) {
            Bukkit.broadcastMessage("§cFehler beim Mapreseten...");
        }
    }
}
