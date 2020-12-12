package com.poxh.party.manager;


import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.ArrayList;
import java.util.List;

public class PartyManager {

    private static List<PlayerParty> partyFromPlayers = new ArrayList<>();

    public static PlayerParty getParty(ProxiedPlayer p) {
        for (PlayerParty party : partyFromPlayers) {
            if (!party.isInParty(p))
                continue;
            return party;
        }
        return null;
    }

    public static boolean createParty(ProxiedPlayer p) {
        if (getParty(p) == null) {
            partyFromPlayers.add(new PlayerParty(p));
            return true;
        }
        return false;
    }

    public static boolean deleteParty(ProxiedPlayer p) {
        if (getParty(p) != null) {
            if (getParty(p).isLeader(p)) {
                for (ProxiedPlayer pp : getParty(p).getMembers())
                    getParty(p).removePlayer(pp);
                partyFromPlayers.remove(getParty(p));
                return true;
            }
            return false;
        }
        return false;
    }

    public static List<PlayerParty> getPartys() {
        return partyFromPlayers;
    }

    public static void sendHelp(ProxiedPlayer player) {
        player.sendMessage(new TextComponent("§7§m-----------------------------"));
        player.sendMessage(new TextComponent(""));
        player.sendMessage(new TextComponent("§e/party invite <player>"));
        player.sendMessage(new TextComponent("§e/party accept <player>"));
        player.sendMessage(new TextComponent("§e/party deny <player>"));
        player.sendMessage(new TextComponent("§e/party chat <player>"));
        player.sendMessage(new TextComponent("§e/party info"));
    }

}
