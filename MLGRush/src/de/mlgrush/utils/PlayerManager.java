package de.mlgrush.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import de.mlgrush.enums.LocationType;
import de.mlgrush.enums.TeamType;
import de.mlgrush.main.Main;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PlayerManager {
    private Player player;

    private static HashMap<UUID, LocationType> currentSetupBed = new HashMap<>();

    private static HashMap<UUID, Integer> roundDeaths = new HashMap<>();

    private static HashMap<UUID, Integer> points = new HashMap<>();

    private static HashMap<UUID, ItemStack[]> inventory = (HashMap)new HashMap<>();

    private static ArrayList<Player> buildMode = new ArrayList<>();

    public PlayerManager(Player player) {
        this.player = player;
    }

    public LocationType getCurrentSetupBed() {
        return currentSetupBed.getOrDefault(this.player.getUniqueId(), null);
    }

    public void resetPlayer() {
        this.player.getInventory().clear();
        this.player.getInventory().setArmorContents(null);
    }

    public void saveInventory() {
        inventory.put(this.player.getUniqueId(), this.player.getInventory().getContents());
    }

    public void restoreInventory() {
        this.player.getInventory().setContents(inventory.get(this.player.getUniqueId()));
    }

    public boolean isInBuildMode() {
        return buildMode.contains(this.player);
    }

    public void setBuildMode(boolean value) {
        if (value) {
            buildMode.add(this.player);
        } else {
            buildMode.remove(this.player);
        }
    }

    public int getRoundPoints() {
        return ((Integer)points.getOrDefault(this.player.getUniqueId(), Integer.valueOf(0))).intValue();
    }

    public void addRoundPoint() {
        points.put(this.player.getUniqueId(), Integer.valueOf(getRoundPoints() + 1));
    }

    public void teleportToTeamIsland() {
        if (Main.getInstance().getTeamHandler().getPlayerTeam(this.player) == TeamType.TEAM_1)
            if (Main.getInstance().getConfigManager().isCacheLoader()) {
                this.player.teleport(Main.getInstance().getLocationHandler().getLocationFromCache(LocationType.SPAWN_1));
            } else {
                this.player.teleport(Main.getInstance().getLocationHandler().getLocationByFile(LocationType.SPAWN_1));
            }
        if (Main.getInstance().getTeamHandler().getPlayerTeam(this.player) == TeamType.TEAM_2)
            if (Main.getInstance().getConfigManager().isCacheLoader()) {
                this.player.teleport(Main.getInstance().getLocationHandler().getLocationFromCache(LocationType.SPAWN_2));
            } else {
                this.player.teleport(Main.getInstance().getLocationHandler().getLocationByFile(LocationType.SPAWN_2));
            }
    }

    public int getRoundDeaths() {
        return ((Integer)roundDeaths.getOrDefault(this.player.getUniqueId(), Integer.valueOf(0))).intValue();
    }

    public void addRoundDeath() {
        roundDeaths.put(this.player.getUniqueId(), Integer.valueOf(getRoundDeaths() + 1));
    }

    public String getRival() {
        for (Player current : (Main.getInstance().getTeamHandler()).playing) {
            if (!current.getName().equalsIgnoreCase(this.player.getName()))
                return current.getName();
        }
        return "§cFehler";
    }

    public void setCurrentSetupBed(LocationType locationType) {
        if (locationType == null)
            currentSetupBed.remove(this.player.getUniqueId());
        currentSetupBed.put(this.player.getUniqueId(), locationType);
    }

    public void sendActionBar(String message) {
        IChatBaseComponent iChatBaseComponent = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + ChatColor.translateAlternateColorCodes('&', message) + "\"}");
        PacketPlayOutChat packet = new PacketPlayOutChat(iChatBaseComponent, (byte)2);
        sendPacket((Packet)packet);
    }

    public void sendPacket(Packet packet) {
        (((CraftPlayer)this.player).getHandle()).playerConnection.sendPacket(packet);
    }

    public void sendTitle(String mainTitle, String subTitle) {
        IChatBaseComponent titleComponent = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + mainTitle + "\"}");
        IChatBaseComponent subComponent = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + subTitle + "\"}");
        PacketPlayOutTitle title = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TIMES, titleComponent, 20, 60, 20);
        PacketPlayOutTitle subtitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TIMES, subComponent);
        PacketPlayOutTitle titlePacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, titleComponent);
        PacketPlayOutTitle subtitlePacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, subComponent);
        sendPacket((Packet)titlePacket);
        sendPacket((Packet)subtitlePacket);
        sendPacket((Packet)titlePacket);
        sendPacket((Packet)subtitlePacket);
    }

    public void sendTitle(String mainTitle, String subTitle, int fadeIn, int stay, int fadeOut) {
        IChatBaseComponent titleComponent = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + mainTitle + "\"}");
        IChatBaseComponent subComponent = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + subTitle + "\"}");
        PacketPlayOutTitle title = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TIMES, titleComponent, fadeIn, stay, fadeOut);
        PacketPlayOutTitle subtitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TIMES, subComponent);
        PacketPlayOutTitle titlePacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, titleComponent);
        PacketPlayOutTitle subtitlePacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, subComponent);
        sendPacket((Packet)title);
        sendPacket((Packet)subtitle);
        sendPacket((Packet)titlePacket);
        sendPacket((Packet)subtitlePacket);
    }

    public Player getPlayer() {
        return this.player;
    }
}


