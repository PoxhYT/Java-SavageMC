package com.poxh.party.manager;

import com.poxh.party.Main;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PartyManager {

    private int id;
    private HashMap<Integer, ProxiedPlayer> owner;
    private HashMap<ProxiedPlayer, Integer> members;
    private HashMap<ProxiedPlayer, Integer> invites;

    public PartyManager() {
        this.id = 0;
        this.owner = new HashMap<>();
        this.members = new HashMap<>();
        this.invites = new HashMap<>();
    }

    public void createParty(ProxiedPlayer player) {
        this.owner.put(id, player);
        this.members.put(player, id);
        getPlayers(id);
        sendMessage(id, "Du hast §aerfolgreich §7eine §5Party §7erstellt!");
        id++;
    }

    public void invite(ProxiedPlayer player, ProxiedPlayer owner) {
        this.invites.put(player, getID(owner));
    }

    public void deny(ProxiedPlayer player) {
        if(this.invites.containsKey(player)) {
            this.id = this.invites.get(player);
            this.members.put(player, id);
            sendMessage(id, player.getDisplayName() + " §chat die Einladung abgeleht!");
            this.invites.remove(player);
        }
    }

    public boolean hasInvited(ProxiedPlayer player, int id) {
        if(this.invites.containsKey(player)) {
            return (this.invites.get(player) == id);
        }

        return false;
    }

    public void accept(ProxiedPlayer player) {
        if(this.invites.containsKey(player)) {
            this.id = this.invites.get(player);
            sendMessage(id, player.getDisplayName() + " §ahat die Einladung abgeleht!");
            this.invites.remove(player);
        }
    }


    public int getID(ProxiedPlayer player) {
        return (this.members.get(player));
    }

    public boolean isInParty (ProxiedPlayer player) {
        return (this.members.containsKey(player));
    }

    public boolean isOwner(ProxiedPlayer player, int id) {
        if(isInParty(player)) {
            if(this.owner.containsKey(getID(player)));
                return (getID(player) == id);
        }
        return false;
    }

    public List<ProxiedPlayer> getPlayers(int id) {
        List<ProxiedPlayer> players = new ArrayList<>();
        for (ProxiedPlayer player : this.members.keySet()) {
            if (getID(player) == id) {
                players.add(player);
            }
        }
        return players;
    }

    public void sendMessage(int id, String msg) {
        for (ProxiedPlayer player : getPlayers(id)) {
            player.sendMessage(new TextComponent(Main.Prefix + msg));
        }
    }

    public void leave(ProxiedPlayer player) {
        if(isInParty(player)) {
            if(isOwner(player, getID(player))) {
                if(!getPlayers(getID(player)).isEmpty()) {
                    List<ProxiedPlayer> players = getPlayers(getID(player));
                    players.remove(player);
                    if(players.isEmpty()) {
                        this.members.remove(player);
                        player.sendMessage(new TextComponent(Main.Prefix + "§cDie Party wurde aufgelöst!"));
                    } else {
                        this.owner.put(getID(player), players.get(0));
                        sendMessage(getID(player), player.getDisplayName() + " §chat die Party verlassen!");
                        players.remove(player);
                        sendMessage(getID(player), players.get(0).getDisplayName() + " §7ist nun der neue §5Partyleiter§7!");
                    }
                }
            }
        }
    }

    public void sendPlayers(ProxiedPlayer player) {
        if(isOwner(player, getID(player))) {
            for (ProxiedPlayer players : getPlayers(getID(player))) {
                if(player != players) {
                    players.sendMessage(new TextComponent("Die §5Party §7betritt einen §e" + player.getServer().getInfo().getName() + " §eServer§7!"));
                    players.connect(player.getServer().getInfo());
                }
            }
        }
    }

    public void senHelp(ProxiedPlayer player) {
        player.sendMessage(new TextComponent("------------------------------------------"));
        player.sendMessage(new TextComponent(""));
        player.sendMessage(new TextComponent(Main.Prefix + "§5Party Commands"));
        player.sendMessage(new TextComponent(Main.Prefix + "§e/party create"));
        player.sendMessage(new TextComponent(Main.Prefix + "§e/party invite <player>"));
        player.sendMessage(new TextComponent(Main.Prefix + "§e/party accept"));
        player.sendMessage(new TextComponent(Main.Prefix + "§e/party deny"));
        player.sendMessage(new TextComponent(Main.Prefix + "§e/party kick <player>"));
        player.sendMessage(new TextComponent(Main.Prefix + "§e/party leave"));
        player.sendMessage(new TextComponent(Main.Prefix + "§e/party list"));
        player.sendMessage(new TextComponent(Main.Prefix + "§e/party chat"));
        player.sendMessage(new TextComponent(""));
        player.sendMessage(new TextComponent("------------------------------------------"));
    }
}
