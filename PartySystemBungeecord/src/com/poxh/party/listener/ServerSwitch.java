package com.poxh.party.listener;
import java.util.concurrent.TimeUnit;

import com.poxh.party.Main;
import com.poxh.party.manager.PartyManager;
import com.poxh.party.manager.PlayerParty;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerSwitchEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ServerSwitch implements Listener {
    @EventHandler
    public void onSwitch(ServerSwitchEvent e) {
        ProxiedPlayer p = e.getPlayer();
        final PlayerParty party;
        if (PartyManager.getParty(p) != null && (party = PartyManager.getParty(p)).isLeader(p)) {
            if (party.getLeader().getServer().getInfo().getName().contains("Lobby") || party.getLeader().getServer().getInfo().getName().contains("Lobby01") || party.getLeader().getServer().getInfo().getName().contains("Lobby-01")) {
                return;
            } else {
                party.getLeader().sendMessage(new TextComponent(Main.Prefix + "Die §5Party §7trettet einen §e" + party.getLeader().getServer().getInfo().getName() + " Server §7bei!"));
                for (ProxiedPlayer pp : party.getMembers()) {
                    pp.sendMessage(new TextComponent(Main.Prefix + "Die §5Party §7trettet einen §e" + party.getLeader().getServer().getInfo().getName() + " Server §7bei!"));
                    BungeeCord.getInstance().getScheduler().schedule(Main.getInstance(), new Runnable() {
                    public void run() {
                        pp.connect(party.getServerInfo(party.getLeader()));
                        }
                    },  2L, TimeUnit.SECONDS);
                }
            }
        }
    }
}

