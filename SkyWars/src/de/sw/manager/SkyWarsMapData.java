package de.sw.manager;

import com.rosemite.services.helper.Log;
import org.bukkit.Location;

import static java.lang.System.*;

public class SkyWarsMapData {
    public final String mapName;
    public final int maxTeamCount;
    public final int maxPlayersInTeam;
    public final String gameSize;
    public final Location[] location;

    public SkyWarsMapData(String mapName, String gameSize, Factors factors, Location[] location, int maxTeamCount, int maxPlayersInTeam) {
        this.location = finishLocationArr(location, factors);
        this.mapName = mapName;
        this.maxTeamCount = maxTeamCount;
        this.maxPlayersInTeam = maxPlayersInTeam;
        this.gameSize = gameSize;
    }

    private Location[] finishLocationArr(Location[] inputLs, Factors factors) {
        Location[] l = new Location[inputLs.length*2];

        arraycopy(inputLs, 0, l, 0, inputLs.length);

        for (int i = inputLs.length; i < l.length; i++) {
            l[i] = inputLs[i-l.length/2];

            l[i].setX(l[i].getX()+factors.x);
            l[i].setZ(l[i].getZ()+factors.z);
        }

        return inputLs;
    }
}
