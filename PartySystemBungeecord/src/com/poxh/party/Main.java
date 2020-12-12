package com.poxh.party;

import com.poxh.party.commands.Command_leave;
import com.poxh.party.commands.Command_party;
import com.poxh.party.commands.Command_partyChat;
import com.poxh.party.listener.ServerSwitch;
import com.poxh.party.manager.PartyManager;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;

public class Main extends Plugin {

    //Strings
    private static Main instance;
    public static String Prefix = "§8[§5Party§8] §7";
    public static String noConsole = "§cDu musst ein Spieler sein um diesen Command auszuführen!";
    private PartyManager manager;

    //Objects
    public static PartyManager partyManager;


    @Override
    public void onEnable() {
        init();
        registerCommands();
        BungeeCord.getInstance().getPluginManager().registerListener(this, (Listener)new ServerSwitch());
    }

    @Override
    public void onDisable() {
        manager = null;
        instance = null;
    }

    private void init() {
        manager = new PartyManager();
        instance = this;
        BungeeCord.getInstance().getConsole().sendMessage(new TextComponent(Prefix + "§5PartySystem §7erfolgreich aktiviert!"));
    }

    private void registerCommands() {
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new Command_party("party"));
        BungeeCord.getInstance().getPluginManager().registerCommand(this, (Command)new Command_partyChat());
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new Command_leave("leave"));
    }

    public static Main getInstance() {
        return instance;
    }

    public static PartyManager getPartyManager() {
        return partyManager;
    }

    public PartyManager getManager() {
        return manager;
    }
}
