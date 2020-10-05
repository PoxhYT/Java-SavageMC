package de.pxav.mlgrush.utils;

import de.pxav.mlgrush.MLGRush;
import de.pxav.mlgrush.enums.LocationType;
import de.pxav.mlgrush.enums.TeamType;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

/**
 * @author PXAV
 */

public class PlayerManager {

    private Player player;
    private static HashMap<UUID, LocationType> currentSetupBed = new HashMap<>();
    private static HashMap<UUID, Integer> roundDeaths = new HashMap<>();
    private static HashMap<UUID, Integer> points = new HashMap<>();
    private static HashMap<UUID, ItemStack[]> inventory = new HashMap<>();
    private static ArrayList<Player> buildMode = new ArrayList<>();

    public PlayerManager(Player player) {
        this.player = player;
    }

    public LocationType getCurrentSetupBed() {
        return currentSetupBed.getOrDefault(player.getUniqueId(), null);
    }

    /**
     * Clears all items including the armor of the player's inventory.
     */
    public void resetPlayer() {
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);
    }

    public void saveInventory() {
        inventory.put(player.getUniqueId(), player.getInventory().getContents());
    }

    public void restoreInventory() {
        player.getInventory().setContents(inventory.get(player.getUniqueId()));
    }

    public boolean isInBuildMode() {
        return buildMode.contains(player);
    }

    public void setBuildMode(final boolean value) {
        if (value)
            buildMode.add(player);
        else
            buildMode.remove(player);
    }

    public int getRoundPoints() {
        return points.getOrDefault(player.getUniqueId(), 0);
    }

    public void addRoundPoint() {
        points.put(player.getUniqueId(), this.getRoundPoints() + 1);
    }

    public void teleportToTeamIsland() {
        if(MLGRush.getInstance().getTeamHandler().getPlayerTeam(player) == TeamType.TEAM_1)
            if(MLGRush.getInstance().getConfigManager().isCacheLoader())
                player.teleport(MLGRush.getInstance().getLocationHandler().getLocationFromCache(LocationType.SPAWN_1));
            else player.teleport(MLGRush.getInstance().getLocationHandler().getLocationByFile(LocationType.SPAWN_1));
        if(MLGRush.getInstance().getTeamHandler().getPlayerTeam(player) == TeamType.TEAM_2)
            if(MLGRush.getInstance().getConfigManager().isCacheLoader())
                player.teleport(MLGRush.getInstance().getLocationHandler().getLocationFromCache(LocationType.SPAWN_2));
            else player.teleport(MLGRush.getInstance().getLocationHandler().getLocationByFile(LocationType.SPAWN_2));
    }

    public int getRoundDeaths() {
        return roundDeaths.getOrDefault(player.getUniqueId(), 0);
    }

    public void addRoundDeath() {
        roundDeaths.put(player.getUniqueId(), this.getRoundDeaths() + 1);
    }

    public String getRival() {
        for (Player current : MLGRush.getInstance().getTeamHandler().playing) {
            if(!current.getName().equalsIgnoreCase(player.getName()))
                return current.getName();
        }
        return "§cFehler";
    }

    /**
     * Sets the bed the player has to setup now.
     * @param locationType The type of the location. (Must be a bed location!)
     */
    public void setCurrentSetupBed(final LocationType locationType) {
        if(locationType == null)
            currentSetupBed.remove(player.getUniqueId());
        currentSetupBed.put(player.getUniqueId(), locationType);
    }

    /**
     * Sends an action bar to the player.
     * @param message The message which should appear.
     */
    public void sendActionBar(final String message){
        final IChatBaseComponent iChatBaseComponent = IChatBaseComponent.ChatSerializer
                .a("{\"text\": \"" + ChatColor.translateAlternateColorCodes('&', message) + "\"}");
        final PacketPlayOutChat packet = new PacketPlayOutChat(iChatBaseComponent, (byte) 2);
        this.sendPacket(packet);
    }

    public void sendPacket(final Packet packet) {
        ((CraftPlayer)player).getHandle().playerConnection.sendPacket(packet);
    }

    /**
     * Sends a title to player.
     * The values like <code>fadeIn, stay, fadeOut</code> have
     * a default value of:
     * -> <tt>fadeIn</tt>: 20
     * -> <tt>stay</tt>: 60
     * -> <tt>fadeOut</tt>: 20
     * @param mainTitle The upper title.
     * @param subTitle The lower title.
     */
    public void sendTitle(final String mainTitle, final String subTitle){
        final IChatBaseComponent titleComponent = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + mainTitle + "\"}");
        final IChatBaseComponent subComponent = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + subTitle + "\"}");
        final PacketPlayOutTitle title = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TIMES, titleComponent, 20, 60, 20);
        final PacketPlayOutTitle subtitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TIMES, subComponent);
        final PacketPlayOutTitle titlePacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, titleComponent);
        final PacketPlayOutTitle subtitlePacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, subComponent);

        this.sendPacket(titlePacket);
        this.sendPacket(subtitlePacket);
        this.sendPacket(titlePacket);
        this.sendPacket(subtitlePacket);
    }

    /**
     * Sends a title to player.
     * @param mainTitle The upper title.
     * @param subTitle The lower title.
     * @param fadeIn The time in ticks, how long it should take until the title's
     *               opacity is 100%
     * @param stay The time in ticks, how long the title should stay in the screen.
     * @param fadeOut The time in ticks, how long it should take until the entire title is faded out.
     */
    public void sendTitle(final String mainTitle, final String subTitle, final int fadeIn, final int stay, final int fadeOut){
        final IChatBaseComponent titleComponent = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + mainTitle + "\"}");
        final IChatBaseComponent subComponent = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + subTitle + "\"}");
        final PacketPlayOutTitle title = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TIMES, titleComponent, fadeIn, stay, fadeOut);
        final PacketPlayOutTitle subtitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TIMES, subComponent);
        final PacketPlayOutTitle titlePacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, titleComponent);
        final PacketPlayOutTitle subtitlePacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, subComponent);

        this.sendPacket(title);
        this.sendPacket(subtitle);
        this.sendPacket(titlePacket);
        this.sendPacket(subtitlePacket);
    }

    public Player getPlayer() {
        return player;
    }

}
