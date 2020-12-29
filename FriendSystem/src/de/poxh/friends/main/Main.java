package de.poxh.friends.main;

import com.rosemite.helper.Log;
import com.rosemite.models.service.common.IService;
import com.rosemite.server.main.ServerService;
import de.poxh.friends.commands.Command_friend;
import de.poxh.friends.commands.Command_msg;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;

public class Main extends Plugin {

    public static String prefix = "§bFreundeSystem §7❘ §7» ";

    private static IService service;

    @Override
    public void onEnable() {
        init();
    }

    @Override
    public void onDisable() {

    }

    private void init() {
        this.service = ServerService.getService(service);
        registerCommands();
    }

    private void registerCommands() {
        PluginManager pluginManager = ProxyServer.getInstance().getPluginManager();
        pluginManager.registerCommand(this, new Command_friend("friend", service));
        pluginManager.registerCommand(this, new Command_msg("msg"));
    }

    public static String sendMessage(String message, ProxiedPlayer player) {
        player.sendMessage(new TextComponent(message));
        return message;
    }
}
