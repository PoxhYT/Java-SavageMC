package de.mlgrush.maps;


import de.mlgrush.enums.LocationType;
import de.mlgrush.main.Main;
import org.bukkit.Location;

public class RegionManager {

    public boolean isInRegion(final Location location) {
        // X
        Double maxX = (this.getPos1().getX() > this.getPos2().getX() ? this.getPos1().getX() : this.getPos2().getX());
        Double minX = (this.getPos1().getX() < this.getPos2().getX() ? this.getPos1().getX() : this.getPos2().getX());

        // Y
        Double maxY = (this.getPos1().getY() > this.getPos2().getY() ? this.getPos1().getY() : this.getPos2().getY());
        Double minY = (this.getPos1().getY() < this.getPos2().getY() ? this.getPos1().getY() : this.getPos2().getY());

        // Z
        Double maxZ = (this.getPos1().getZ() > this.getPos2().getZ() ? this.getPos1().getZ() : this.getPos2().getZ());
        Double minZ = (this.getPos1().getZ() < this.getPos2().getZ() ? this.getPos1().getZ() : this.getPos2().getZ());

        if(location.getX() <= maxX && location.getX() >= minX) {
            if(location.getY() <= maxY && location.getY() >= minY) {
                return location.getZ() <= maxZ && location.getZ() >= minZ;
            }
        }
        return false;
    }

    private Location getPos1() {
        if(Main.getInstance().getConfigManager().isCacheLoader())
            return Main.getInstance().getLocationHandler().getLocationFromCache(LocationType.POS_1);
        else return Main.getInstance().getLocationHandler().getLocationByFile(LocationType.POS_1);
    }

    private Location getPos2() {
        if(Main.getInstance().getConfigManager().isCacheLoader())
            return Main.getInstance().getLocationHandler().getLocationFromCache(LocationType.POS_2);
        else return Main.getInstance().getLocationHandler().getLocationByFile(LocationType.POS_2);
    }

}

