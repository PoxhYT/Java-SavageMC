package de.soup.commands;

import de.soup.main.Main;
import de.soup.manager.LocationManager;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;

public class Command_setlocation implements CommandExecutor {

    private static File file = new File("plugins/SoupTraining", "Locations.yml");

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;
            TextComponent component = new TextComponent(Main.prefix + "§7Setze die §eLobby§7, ");
            TextComponent clickme = new TextComponent("§8[§aKLICKE HIER§8]");
            clickme.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText("§7Setze den §eSpawn §7für die §eLobby§7!")));
            clickme.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/setlocation lobby"));
            component.addExtra(clickme);
            if(args.length == 0 && args.length != 1) {
                player.spigot().sendMessage(component);
                player.playSound(player.getLocation(), Sound.ANVIL_BREAK, 1, 1);
                return true;
            }
            if (args[0].equalsIgnoreCase("lobby") || args[0].equalsIgnoreCase("shop") || args[0].equalsIgnoreCase("spawn")) {
                switch (args[0].toLowerCase()) {
                    case "lobby":
                        if(file.exists()) {
                            player.sendMessage(Main.prefix + "Die §eLocation §7existiert bereits!");
                            return false;
                        }
                        player.sendMessage(Main.prefix + "Du hast die §eLobby §7gesetzt!");
                        player.playSound(player.getLocation(), Sound.LEVEL_UP, 1, 1);
                        LocationManager.setSpawn("Lobby", player.getLocation());
                        break;
                }
            }
        }
        return false;
    }
}
