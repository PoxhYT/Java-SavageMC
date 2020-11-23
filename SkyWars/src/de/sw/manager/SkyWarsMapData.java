package de.sw.manager;

import java.util.Map;
import java.util.HashMap;
import com.google.gson.Gson;
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

    public SkyWarsMapData(int id, String mapName, String gameSize, Location[] locations, int maxTeamCount, int maxPlayersInTeam, boolean stillUnderDevelopment) {
        this.id = id;
        this.mapName = mapName;
        this.locations = locations;
        this.maxTeamCount = maxTeamCount;
        this.maxPlayersInTeam = maxPlayersInTeam;
        this.gameSize = gameSize;
        this.stillUnderDevelopment = stillUnderDevelopment;
    }

    public static Map<String, Object> toMap(SkyWarsMapData data) {
        Map<String, Object> map = new HashMap<>();
        map.put(Path.MapName.name(), data.mapName);
        map.put(Path.MaxTeamCount.name(), data.maxTeamCount);
        map.put(Path.MaxPlayersInTeam.name(), data.maxPlayersInTeam);
        map.put(Path.GameSize.name(), data.gameSize);
        map.put(Path.StillUnderDevelopment.name(), data.stillUnderDevelopment);
        map.put(Path.Id.toString(), data.id);

        return map;
    }
}
