package com.poxh.party.manager;

import com.poxh.party.Main;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.chat.*;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class PlayerParty {

    private ProxiedPlayer leader;
    private List<ProxiedPlayer> members;
    private List<ProxiedPlayer> invites;

    public PlayerParty(ProxiedPlayer leader) {
        this.leader = leader;
        this.invites = new ArrayList<>();
        this.members = new ArrayList<>();
    }

    public boolean isLeader(ProxiedPlayer player) {
        if(this.leader == player) {
            return true;
        }
        return false;
    }

    public List<ProxiedPlayer> getInvites() {
        return invites;
    }

    public List<ProxiedPlayer> getMembers() {
        return members;
    }

    public ProxiedPlayer getLeader() {
        return leader;
    }

    public boolean hasRequest(ProxiedPlayer player) {
        if(this.invites.contains(player)) {
            return true;
        }
        return false;
    }

    public boolean isInParty(ProxiedPlayer player) {
        if(isLeader(player)) {
            return true;
        }
        if(this.members.contains(player)) {
            return true;
        }
        return false;
    }

    public boolean getPartyInfo(ProxiedPlayer player) {

        int partySize = members.size() + 1;

        player.sendMessage(new TextComponent("§7§m--------------§5PartySystem§7§m-----------------"));
        player.sendMessage(new TextComponent("§7Die §5Party §7wird von " + getLeader().getDisplayName() + " §7geleitet!"));
        player.sendMessage(new TextComponent("§7Die Anzahl §7der §5Mitglieder §7lautet: " + partySize));
        player.sendMessage(new TextComponent("§7Die §5Party §7ist aktuell auf dem §e" + getServerInfo(player) + " §7Server!"));
        player.sendMessage(new TextComponent("§7§m-----------------------------------------------"));
        return true;
    }

    public boolean addPlayer(ProxiedPlayer player) {
        if(this.invites.contains(player)) {
            this.members.add(player);
            for (ProxiedPlayer players : getMembers()) {
                players.sendMessage(new TextComponent(Main.Prefix + player.getDisplayName() + " §7hat die §5Party §7betreten!"));
            }
            removeInvite(player);
            return true;
        }
        return false;
    }

    public boolean removePlayer(ProxiedPlayer player) {
        if(this.members.contains(player)) {
            this.members.remove(player);
            return true;
        }
        return  false;
    }

    public boolean removeInvite(ProxiedPlayer player) {
        if(this.invites.contains(player)) {
            this.invites.remove(player);
            return true;
        }
        return false;
    }

    public ServerInfo getServerInfo(ProxiedPlayer player) {
        return this.leader.getServer().getInfo();
    }

    public void invite(final ProxiedPlayer player) {
        this.invites.add(player);
        player.sendMessage(new TextComponent(Main.Prefix + getLeader().getDisplayName() + " §7hat dich in seine §5Party §7eingeladen!"));
        TextComponent accept = new TextComponent(Main.Prefix + "§a§lAnnehmen §7mit: §e/party accept " + getLeader().getName());
        accept.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/party accept " + getLeader().getName()));
        accept.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, (new ComponentBuilder("§a§lAnnehmen")).create()));
        TextComponent deny = new TextComponent(Main.Prefix + "§c§lAblehnen §7mit: §e/party deny " + getLeader().getName());
        deny.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/party deny " + getLeader().getName()));
        deny.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, (new ComponentBuilder("§c§lAblehnen")).create()));
        player.sendMessage(accept);
        player.sendMessage(deny);
        BungeeCord.getInstance().getScheduler().schedule(Main.getInstance(), new Runnable() {
            public void run() {
                if (PlayerParty.this.hasRequest(player)) {
                    PlayerParty.this.invites.remove(player);
                    player.sendMessage(new TextComponent(Main.Prefix + "§cDeine Einladung ist abgelaufen!"));
                    PlayerParty.this.getLeader().sendMessage(new TextComponent(Main.Prefix + "§cDeine Einladung an " + player.getDisplayName() + " §cist abgelaufen!"));
                }
                PlayerParty.this.start(player);
            }
        },  5L, TimeUnit.MINUTES);
    }

    private void start(final ProxiedPlayer p) {
        BungeeCord.getInstance().getScheduler().schedule(Main.getInstance(), new Runnable() {
            public void run() {
                PlayerParty party = PartyManager.getParty(p);
                if (party != null && party.getMembers().size() == 1) {
                    PartyManager.deleteParty(p);
                    party.getLeader().sendMessage(new TextComponent(Main.Prefix + "§cParty wird wegen zu wenig Mitgliedern aufgelöst!"));
                    p.sendMessage(new TextComponent(Main.Prefix + "§cDie Party wurde aufgelöst"));
                }
            }
        },  2L, TimeUnit.MINUTES);
    }
}
