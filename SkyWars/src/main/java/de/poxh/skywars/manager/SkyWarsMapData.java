package de.sw.manager;

import java.util.Map;
import java.util.HashMap;
import de.sw.enums.Path;
import org.bukkit.Location;

public class SkyWarsMapData {
    public final String mapName;
    public final int maxTeamCount;
    public final int maxPlayersInTeam;
    public final String gameSize;
    public boolean stillUnderDevelopment;
    public Location[] locations;
    public final int id;
    private final Location locationOfMiddlePoint;

    private final int defaultRadius = 25;
    private int radius = defaultRadius;

    public SkyWarsMapData(int id, String mapName, String gameSize, Location[] locations, int maxTeamCount, int maxPlayersInTeam, boolean stillUnderDevelopment) {
        this.id = id;
        this.mapName = mapName;
        this.locations = locations;
        this.maxTeamCount = maxTeamCount;
        this.maxPlayersInTeam = maxPlayersInTeam;
        this.gameSize = gameSize;
        this.stillUnderDevelopment = stillUnderDevelopment;

        this.radius = defaultRadius;

        if(locations.length == 0) {
            locationOfMiddlePoint = null;
            return;
        }

        // Calculate MiddlePoint Of Map
        int[] pIndex = new int[4];
        pIndex[0] = 0;
        pIndex[1] = locations.length / 2;
        pIndex[2] = locations.length / 4;
        pIndex[3] = pIndex[2] + pIndex[1];

        Location[] points = new Location[4];
        for (int i = 0; i < points.length; i++) {
            points[i] = locations[pIndex[i]];
        }

        // Calculate X
        double x = 0;
        x += m(points[0].getX(), points[2].getX());
        x += m(points[1].getX(), points[3].getX());

        // Calculate Z
        double z = 0;
        z += m(points[0].getZ(), points[2].getZ());
        z += m(points[1].getZ(), points[3].getZ());

        this.locationOfMiddlePoint = new Location(locations[0].getWorld(), x, locations[0].getY(), z);
    }

    public SkyWarsMapData(int id, String mapName, String gameSize, Location[] locations, int maxTeamCount, int maxPlayersInTeam, boolean stillUnderDevelopment, int radius, Location locationOfMiddlePoint) {
        this.id = id;
        this.mapName = mapName;
        this.locations = locations;
        this.maxTeamCount = maxTeamCount;
        this.maxPlayersInTeam = maxPlayersInTeam;
        this.gameSize = gameSize;
        this.stillUnderDevelopment = stillUnderDevelopment;
        this.radius = radius;

        this.locationOfMiddlePoint = locationOfMiddlePoint;
    }

    public static Map<String, Object> toMap(SkyWarsMapData data) {
        Map<String, Object> map = new HashMap<>();
        map.put(Path.MapName.toString(), data.mapName);
        map.put(Path.GameSize.toString(), data.gameSize);
        map.put(Path.Locations.toString(), data.locations);
        map.put(Path.MaxTeamCount.toString(), data.maxTeamCount);
        map.put(Path.MaxPlayersInTeam.toString(), data.maxPlayersInTeam);
        map.put(Path.StillUnderDevelopment.toString(), data.stillUnderDevelopment);
        map.put(Path.Radius.toString(), data.radius);
        map.put(Path.Id.toString(), data.id);
        return (map);
    }

    private double m(double value1, double value2) {
        return (value1 + value2) / 2;
    }


    public Location getLocationOfMiddlePoint() {
        return locationOfMiddlePoint;
    }

    public int getRadius() {
        return radius;
    }
}
