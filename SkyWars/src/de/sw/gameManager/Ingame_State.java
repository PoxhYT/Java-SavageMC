package de.sw.gameManager;

import de.sw.listener.TeamListener;
import de.sw.manager.TeamManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Ingame_State extends Game_State {

    private static TeamManager team1 = new TeamManager("§eTeam1", "§eTeam1",2);
    private static TeamManager team2 = new TeamManager("§eTeam2", "§eTeam2",2);
    private static TeamManager team3 = new TeamManager("§eTeam3", "§eTeam3",2);
    private static TeamManager team4 = new TeamManager("§eTeam4", "§eTeam4",2);

    @Override
    public void start() {
    }

    @Override
    public void stop() {

    }
}
