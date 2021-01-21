package de.cb.poxh.manager.clan;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ClanManager {


    private static List<PlayerClans> clansFromPlayers = new ArrayList<>();

    public static PlayerClans getClan(Player p) {
        for (PlayerClans clan : clansFromPlayers) {
            if (!clan.isInClan(p))
                continue;
            return clan;
        }
        return null;
    }

    public static boolean createClan(Player p, String name) {
        if (getClan(p) == null) {
            clansFromPlayers.add(new PlayerClans(p, name));
            return true;
        }
        return false;
    }

    public static boolean deleteClan(Player p, String name) {
        if (getClan(p) != null) {
            if (getClan(p).isLeader(p)) {
                for (Player pp : getClan(p).getMembers())
                    getClan(p).removePlayer(pp);
                clansFromPlayers.remove(getClan(p));
                return true;
            }
            return false;
        }
        return false;
    }

    public static List<PlayerClans> getClans() {
        return clansFromPlayers;
    }
}
