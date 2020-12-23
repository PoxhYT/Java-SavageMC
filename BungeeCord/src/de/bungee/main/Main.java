package de.bungee.main;

import com.rosemite.services.main.MainService;
import com.rosemite.services.models.player.PlayerInfo;
import de.bungee.commands.Command_banPlayer;
import de.bungee.commands.Command_getDate;
import de.bungee.enums.BanReasons;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class Main extends Plugin {

    public static Main instance;

    public static String prefix = "§8[§eSavageMC§8] §7";

    @Override
    public void onEnable() {
        init();

    }

    public void init() {
        BungeeCord.getInstance().getConsole().sendMessage(new TextComponent(prefix + "§eLoaded BungeeSystem Successfully"));
        registerCommands();
    }

    public static void sendMessage(ProxiedPlayer sender, Object message) {
        ProxiedPlayer player = (ProxiedPlayer) sender;
        player.sendMessage(new TextComponent(prefix + message));
    }

    public static void banPlayer(String uuid, int amount, int banAmount) {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, amount);
        MainService.getService(null).getPlayerService().setPlayerBan(uuid, calendar.getTime().toString());
        MainService.getService(null).getPlayerService().addBans(uuid, banAmount);
    }

    @Override
    public void onDisable() {
        registerCommands();
    }

    public void registerCommands() {
        PluginManager pluginManager = ProxyServer.getInstance().getPluginManager();
        pluginManager.registerCommand(this, new Command_getDate("date"));
        pluginManager.registerCommand(this, new Command_banPlayer("banPlayer"));
    }
}
