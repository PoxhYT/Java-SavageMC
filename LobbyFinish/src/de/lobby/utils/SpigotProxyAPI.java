package de.lobby.utils;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import de.lobby.main.Main;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class SpigotProxyAPI extends JavaPlugin {
    public static void sendPlayer(Player player, String server) {
        ByteArrayDataOutput output = ByteStreams.newDataOutput();
        output.writeUTF("Connect");
        output.writeUTF(server);
        player.sendPluginMessage((Plugin)Main.getInstance(), "BungeeCord", output.toByteArray());
    }

    public static void sendLobby(Player player, String server) {
        ByteArrayDataOutput output = ByteStreams.newDataOutput();
        output.writeUTF("Connect");
        output.writeUTF(server);
        player.sendPluginMessage((Plugin) Main.getInstance(), "BungeeCord", output.toByteArray());
    }

    public static void checkPlayerStatus(String server) {
        ByteArrayDataOutput output = ByteStreams.newDataOutput();
        output.writeUTF("PlayerCount");
        output.writeUTF(server);
    }
}
