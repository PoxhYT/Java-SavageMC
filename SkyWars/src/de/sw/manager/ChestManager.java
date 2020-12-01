package de.sw.manager;

import de.sw.manager.SkyWarsMapData;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.List;

public class ChestManager {
    private final Location locationOfMiddlePoint;
    private final SkyWarsMapData map;

    public ChestManager(Location locationOfMiddlePoint, SkyWarsMapData map) {
        this.locationOfMiddlePoint = locationOfMiddlePoint;
        this.map = map;
    }

    public void onGameStart() {
        getAllChestsInRadius(this.locationOfMiddlePoint, map.getRadius());
    }

    private List<Block> getAllChestsInRadius(Location locationOfMiddlePoint, int radius){
        if (radius < 0) {
            return new ArrayList<>(0);
        }

        List<Block> blocks = new ArrayList<>();

        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    Block b = locationOfMiddlePoint.getBlock().getRelative(x, y, z);
                    if (b.getType() == Material.CHEST) {
                        blocks.add(b);
                    }
                }
            }
        }

        return blocks;
    }
}
