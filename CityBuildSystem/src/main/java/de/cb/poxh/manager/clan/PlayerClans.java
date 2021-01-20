package de.cb.poxh.manager.clan;

import de.cb.poxh.main.Main;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class PlayerClans {

    private Player leader;
    private String name;
    private List<Player> members;
    private List<Player> invites;

    public PlayerClans(Player leader, String name) {
        this.leader = leader;
        this.invites = new ArrayList<>();
        this.members = new ArrayList<>();
        this.name = name;
    }

    public boolean isLeader(Player player) {
        if(this.leader == player) {
            return true;
        }
        return false;
    }

    public List<Player> getInvites() {
        return invites;
    }

    public List<Player> getMembers() {
        return members;
    }

    public Player getLeader() {
        return leader;
    }

    public String getName() {
        return name;
    }

    public boolean hasRequest(Player player) {
        if(this.invites.contains(player)) {
            return true;
        }
        return false;
    }

    public boolean isInClan(Player player) {
        if(!this.name.equalsIgnoreCase(name)) {
            return false;
        }
        if(isLeader(player)) {
            return true;
        }
        if(this.members.contains(player)) {
            return true;
        }
        return false;
    }

    public boolean getClanInfo(Player player) {


        String[] names = new String[members.size()];

        // Set names
        for (int j = 0; j < names.length; j++) {
            names[j] = members.get(j).getName();
        }

        // Add Players to TeamList
        String[] playersInTeam = new String[members.size()];
        for (int j = 0; j < names.length; j++) {
            playersInTeam[j] = "§7- §f" + names[j];
        }

        int clanSize = members.size() + 1;

        player.sendMessage("§7§m--------------§cClanSystem§7§m-----------------");
        player.sendMessage("§7Der §eClan §7wird von " + getLeader().getDisplayName() + " §7geleitet!");
        player.sendMessage("§7Die Anzahl §7der §5Mitglieder §7lautet: " + clanSize);
        player.sendMessage("§7Name vom §cClan§7: §e" + getName());
        player.sendMessage("§e" + (Arrays.asList(playersInTeam.clone())));
        player.sendMessage("§7§m-----------------------------------------------");
        return true;
    }

    public boolean addPlayer(Player player) {
        if(this.invites.contains(player)) {
            this.members.add(player);
            for (Player players : getMembers()) {
                players.sendMessage(Main.instance.prefix + player.getDisplayName() + " §7hat den §eClan §7betreten!");
            }
            removeInvite(player);
            return true;
        }
        return false;
    }

    public boolean removePlayer(Player player) {
        if(this.members.contains(player)) {
            this.members.remove(player);
            for (Player players : getMembers()) {
                players.sendMessage(Main.instance.prefix + player.getDisplayName() + " §chat den Clan verlassen!");
            }
            leader.sendMessage(Main.instance.prefix + player.getDisplayName() + " §chat die Party verlassen!");
            player.sendMessage(Main.instance.prefix + "§cDu hast die Party verlassen!");
            return true;
        }
        return  false;
    }

    public void invite (Player player) {
        this.invites.add(player);
    }

    public boolean removeInvite(Player player) {
        if(this.invites.contains(player)) {
            this.invites.remove(player);
            return true;
        }
        return false;
    }
}
