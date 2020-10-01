package de.mlgrush.maps;

import de.mlgrush.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public class MapResetHandler {

    private List<Location> placedBlocks = new ArrayList<>();

    public void addPlacedBlock(final Location location) {
        this.placedBlocks.add(location);
    }

    public boolean canBreak(final Location location) {
        return placedBlocks.contains(location);
    }

    public void resetMap(final boolean removeOld) {
        int index = 0;
        List<Location> toRemove = new ArrayList<>();
        if(!this.placedBlocks.isEmpty()) {
            for (Location current : this.placedBlocks) {
                current.getWorld().getBlockAt(current).setType(Material.AIR);
                if(removeOld)
                    toRemove.add(current);
                index++;
            }
        }
        if(removeOld)
            this.placedBlocks.removeAll(toRemove);
        Bukkit.getConsoleSender().sendMessage(Main.getInstance().getPrefix() + "§7Reset successful -> §a" + index + " block(s).");
    }

}

