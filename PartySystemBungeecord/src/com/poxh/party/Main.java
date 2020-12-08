package com.poxh.party;

import com.poxh.party.commands.Command_leave;
import com.poxh.party.commands.Command_party;
import com.poxh.party.manager.PartyManager;
import com.rosemite.services.helper.Log;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;

public class Main extends Plugin {

    //Strings
    private static Main instance;
    public static String Prefix = "§8[§5Party§8] §7";
    public static String noConsole = "§cDu musst ein Spieler sein um diesen Command auszuführen!";
    private PartyManager manager;


    @Override
    public void onEnable() {
        init();
        registerCommands();
    }

    @Override
    public void onDisable() {
        manager = null;
        instance = null;
    }

    private void init() {
        manager = new PartyManager();
        instance = this;
    }

    private void registerCommands() {
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new Command_party("party"));
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new Command_leave("leave"));
    }

    public static Main getInstance() {
        return instance;
    }

    public PartyManager getManager() {
        return manager;
    }
}
