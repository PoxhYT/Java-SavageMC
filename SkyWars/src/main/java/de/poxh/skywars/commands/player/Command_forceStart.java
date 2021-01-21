package de.sw.commands.player;

import de.sw.main.Main;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Command_forceStart implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if(sender instanceof Player) {
            Player player = (Player) sender;
            if(player.hasPermission("server.ultra")) {
                if (Main.alivePlayers.size() >= 1) {
                    player.sendMessage(Main.prefix + "Der §b§lForceStart §7war §a§lerfolgreich§7!");
                    player.playSound(player.getLocation(), Sound.LEVEL_UP, 1, 1);
                    Main.countdown.seconds = 5;
                } else {
                    player.sendMessage(Main.prefix + "§4§lEs sind nicht genügende Spieler in der Runde vorhanden. Bitte warte...");
                    player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1, 1);
                }
            } else {
                player.spigot().sendMessage(getMessage());
            }
        }

        return false;
    }

    private static TextComponent getClickableMessage(String message, String hover, String command){
        TextComponent textComponent = new TextComponent(message);
        textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, command));
        textComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(hover).create()));
        return textComponent;
    }

    private static TextComponent openWebsite(String message, String hover, String url){
        TextComponent textComponent = new TextComponent(message);
        textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, url));
        textComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(hover).create()));
        return textComponent;
    }

    private static TextComponent getMessage(){
        TextComponent textComponent = new TextComponent(Main.instance.prefix + "Dafür benötigst du den §6§lPremium §7Rang. ");
        textComponent.addExtra(openWebsite( "§7Besuche §8§l§nSavageMC.net/shop", "§7Unser Shop", "https://google.com"));
        textComponent.addExtra(" §7um einen §7§lRang §7zu bekommen.");
        return textComponent;
    }
}
