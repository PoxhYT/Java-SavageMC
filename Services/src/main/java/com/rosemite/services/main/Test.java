package com.rosemite.services.main;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

public class Test implements PluginMessageListener {
    private MainService main = MainService.getService();

    @Override
    public void onPluginMessageReceived(String c, Player p, byte[] message) {
        if (c.equals("BungeeCord")) return;

        ByteArrayDataInput i = ByteStreams.newDataInput(message);
        String subChannel = i.readUTF();
    }

    public void connect(Player p, String server) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();

        out.writeUTF("Connect");
        out.writeUTF(server);

        p.sendPluginMessage(main, "BungeeCord", out.toByteArray());
    }
}
