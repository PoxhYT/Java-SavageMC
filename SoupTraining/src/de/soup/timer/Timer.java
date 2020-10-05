package de.soup.timer;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Timer {

    private ScheduledExecutorService executorService = Executors.newScheduledThreadPool(0);
    private int elapsedTime;

    public void start(Player player) {
        executorService.scheduleAtFixedRate(() -> {
            elapsedTime += 100;
            int hours  = (elapsedTime / 3600000);
            int minutes = (elapsedTime / 60000) % 60;
            int seconds = (elapsedTime / 1000) % 60;
            int milliseconds = (elapsedTime / 10) % 100;

            StringBuilder message = new StringBuilder("§7Timer§8: §e§l");
            message.append(String.format("%02d", hours)).append(":");
            message.append(String.format("%02d", minutes)).append(":");
            message.append(String.format("%02d", seconds)).append(".");
            message.append(String.format("%02d", milliseconds));

            sendActionBar(player, message.toString());
        }, 0, 100, TimeUnit.MILLISECONDS);
    }

    public void stop() {
        executorService.shutdownNow();
    }

    private void sendActionBar(Player player, String message) {
        PacketPlayOutChat packet = new PacketPlayOutChat(IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + message + "\"}"), (byte) 2);
        ((CraftPlayer)player).getHandle().playerConnection.sendPacket(packet);
    }
}
