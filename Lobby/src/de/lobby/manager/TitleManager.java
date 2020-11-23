package de.lobby.manager;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class TitleManager {

    public static void setTitle(Player p, Integer fadeIn, Integer stay, Integer fadeOut, String title, String subtitle){
        PlayerConnection connection = ((CraftPlayer)p).getHandle().playerConnection;
        IChatBaseComponent Ttitle = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + title + "\"}");
        IChatBaseComponent Stitle = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + subtitle + "\"}");
        PacketPlayOutTitle Ttime = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TIMES, Ttitle, fadeIn, stay, fadeOut);
        PacketPlayOutTitle Stime = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TIMES, Stitle);
        PacketPlayOutTitle Tpacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, Ttitle);
        PacketPlayOutTitle Spacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, Stitle);
        connection.sendPacket(Ttime);
        connection.sendPacket(Stime);
        connection.sendPacket(Tpacket);
        connection.sendPacket(Spacket);
    }

    public static void setBar(Player p, String msg){
        PlayerConnection connection = ((CraftPlayer)p).getHandle().playerConnection;
        IChatBaseComponent chat = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + msg + "\"}");
        PacketPlayOutChat Cpacket = new PacketPlayOutChat(chat, (byte) 2);
        connection.sendPacket(Cpacket);
    }
}
