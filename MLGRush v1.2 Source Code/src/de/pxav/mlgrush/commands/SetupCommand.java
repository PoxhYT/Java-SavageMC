package de.pxav.mlgrush.commands;

import de.pxav.mlgrush.MLGRush;
import de.pxav.mlgrush.enums.LocationType;
import de.pxav.mlgrush.utils.PlayerManager;
import org.apache.commons.lang.ObjectUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class SetupCommand implements CommandExecutor, TabCompleter {

    public SetupCommand(final String command) {
        Bukkit.getPluginCommand(command).setExecutor(this);
    }

    @Override
    public boolean onCommand(final CommandSender sender, final Command command, final String s, final String[] args) {

        if(sender instanceof Player) {
            final Player player = (Player) sender;
            final PlayerManager playerManager = new PlayerManager(player);
            if (player.hasPermission("mlgrush.setup")) {

                if(args.length == 1) {
                    boolean found = false;
                    for (LocationType locationType : LocationType.values()) {
                        if(args[0].equalsIgnoreCase(locationType.name())) {
                            if(locationType == LocationType.SPECTATOR) {
                                if(MLGRush.getInstance().getLocationHandler().getLocationByFile(LocationType.POS_1) != null
                                        && MLGRush.getInstance().getLocationHandler().getLocationByFile(LocationType.POS_2) != null) {
                                    if(MLGRush.getInstance().getRegionManager().isInRegion(player.getLocation())) {
                                        MLGRush.getInstance().getLocationHandler().addLocation(locationType.name(), player.getLocation());
                                        player.sendMessage(MLGRush.getInstance().getPrefix() + "§7Du hast die Location §a" + locationType.name() + " §7gesetzt!");
                                        found = true;
                                        break;
                                    } else {
                                        player.sendMessage(MLGRush.getInstance().getPrefix() + "§cDer Spectator Spawn muss sich in der gesetzten Region befinden.");
                                        return false;
                                    }
                                } else {
                                    player.sendMessage(MLGRush.getInstance().getPrefix() + "§7Bevor du den Spectator Spawn setzen kannst, musst du alle Positionen der Region setzen.");
                                    return false;
                                }
                            }
                            MLGRush.getInstance().getLocationHandler().addLocation(locationType.name(), player.getLocation());
                            player.sendMessage(MLGRush.getInstance().getPrefix() + "§7Du hast die Location §a" + locationType.name() + " §7gesetzt!");
                            found = true;
                            break;
                        }
                    }

                    if(!found)
                        player.sendMessage(MLGRush.getInstance().getPrefix() + "§cDiese Location existiert nicht.");

                } else if (args.length == 0) {
                    player.sendMessage(MLGRush.getInstance().getPrefix() + "§8§m-------------------");
                    player.sendMessage(MLGRush.getInstance().getPrefix() + "§7");
                    for (LocationType locationType : LocationType.values()) {

                        if(!locationType.toString().contains("BED")) {
                            player.sendMessage(MLGRush.getInstance().getPrefix() + "§a/setup " + locationType.name());
                            player.sendMessage(MLGRush.getInstance().getPrefix() + "§8➥ §7" + locationType.getDescription());
                            player.sendMessage(MLGRush.getInstance().getPrefix() + "");
                        }

                    }

                    player.sendMessage(MLGRush.getInstance().getPrefix() + "§a/setup setBed <T1 | T2> ");
                    player.sendMessage(MLGRush.getInstance().getPrefix() + "§8➥ §7Setzt das Bett für ein Team");
                    player.sendMessage(MLGRush.getInstance().getPrefix() + "");
                    player.sendMessage(MLGRush.getInstance().getPrefix() + "§8§m-------------------");
                } else if (args.length == 2 && args[0].equalsIgnoreCase("setBed")) {
                    if (args[1].equalsIgnoreCase("T1")) {
                        playerManager.setCurrentSetupBed(LocationType.BED_TOP_1);
                        player.playSound(player.getLocation(), Sound.ORB_PICKUP, 3, 1);
                        player.sendMessage(MLGRush.getInstance().getPrefix() + "§7Bitte schlage den §aoberen §7Teil des Bettes von Team" +
                                " §a#1§7.");
                    } else if(args[1].equalsIgnoreCase("T2")) {
                        playerManager.setCurrentSetupBed(LocationType.BED_TOP_2);
                        player.playSound(player.getLocation(), Sound.ORB_PICKUP, 3, 1);
                        player.sendMessage(MLGRush.getInstance().getPrefix() + "§7Bitte schlage den §aoberen §7Teil des Bettes von Team" +
                                " §a#2§7.");
                    }
                } else
                    player.sendMessage(MLGRush.getInstance().getPrefix() + "");
            } else
                player.sendMessage(MLGRush.getInstance().getPrefix() + "§cDazu hast du keine Rechte!");
        } else
            sender.sendMessage(MLGRush.getInstance().getPrefix() + "§cDu musst ein Spieler sein!");

        return true;
    }

    /**
     * Handles the tab completion
     */
    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {

        final List<String> completeOptions = new ArrayList<>();
        for (LocationType current : LocationType.values()) {
            if(!this.isBedLocation(current))
                completeOptions.add(current.toString());
        }

        return completeOptions;
    }

    private boolean isBedLocation(final LocationType locationType) {
        return locationType == LocationType.BED_BOTTOM_1 || locationType == LocationType.BED_BOTTOM_2
                || locationType == LocationType.BED_TOP_1 || locationType == LocationType.BED_TOP_2;
    }
}
