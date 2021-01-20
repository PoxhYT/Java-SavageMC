package de.cb.poxh.manager.teleport;

import de.cb.poxh.commands.countdowns.Countdown_teleport;
import de.cb.poxh.main.Main;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;

public class TeleportManager {

    private static HashMap<UUID, NavigableMap<String, TeleportUser>> newRequests = new HashMap<>();

    public static void sendRequest(Player sender, Player getter) {

        if(sender == getter) {
            sender.sendMessage(Main.instance.prefix + "§cDu kannst dir selber keine Anfrage schicken!");
            return;
        }

        if(newRequests.containsKey(getter.getUniqueId())) {
            NavigableMap<String, TeleportUser> list = newRequests.get(getter.getUniqueId());
            if (list.containsKey(sender.getName())) {
                sender.sendMessage(Main.instance.prefix + "§cDu hast " + getter.getName() + " bereits eine Anfrage geschickt");
                return;
            }
        }

        newRequests.put(getter.getUniqueId(), addToList(sender.getName(), new TeleportUser(sender.getName()), newRequests.get(getter.getUniqueId())));
        sender.sendMessage(Main.instance.prefix + "Du hast eine §b§lTeleportAnfrage §7an §b§l" + getter.getName() + " §7gesendet");
        getter.spigot().sendMessage(getMessage(sender));


    }

    public static void acceptRequest(Player getter, String acceptName) {

        if(newRequests.containsKey(getter.getUniqueId())) {
            NavigableMap<String, TeleportUser> list = newRequests.get(getter.getUniqueId());
            if(list.containsKey(acceptName)) {
                TeleportUser teleportUser = list.get(acceptName);
                Player sender = Bukkit.getPlayer(teleportUser.getRequesterName());
                if(sender != null) {
//                    Main.instance.countdown_teleport.start(sender, getter);
                    getter.sendMessage(Main.instance.prefix + "Du hast die §b§lTeleportAnfrage §7von §b§l" + sender.getName() + " §7angenommen.");
                    sender.sendMessage(Main.instance.prefix + "§b§l" + getter.getName() + " §7hat deine §b§lTeleportAnfrage §7angemommen. §c§lBewege dich nicht damit du teleportierst wirst!");
                    list.remove(acceptName);
                    newRequests.put(getter.getUniqueId(), list);
                } else {
                    list.remove(acceptName);
                    getter.sendMessage(Main.instance.prefix + "§b§l" + sender.getName() + " §7ist nicht online!");
                    newRequests.put(getter.getUniqueId(), list);
                }
            } else {
                getter.sendMessage(Main.instance.prefix + "Du hast von §b§l" + acceptName + " §7keine §b§lTeleportAnfrage §7erhalten.");
            }
        } else {
            getter.sendMessage(Main.instance.prefix + "Du hast von §b§l" + acceptName + " §7keine §b§lTeleportAnfrage §7erhalten.");
        }
    }

    private static NavigableMap<String, TeleportUser> addToList(String name, TeleportUser teleportUser, NavigableMap<String, TeleportUser> list){
        if(list != null){
            list.put(name, teleportUser);
        }else{
            list = new TreeMap<>();
            list.put(name, teleportUser);
        }
        return list;
    }

    private static TextComponent getClickableMessage(String message,  String hover, String command){
        TextComponent textComponent = new TextComponent(message);
        textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, command));
        textComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(hover).create()));
        return textComponent;
    }

    private static TextComponent getMessage(Player sender){

        TextComponent textComponent = new TextComponent(Main.instance.prefix + "Du hast eine §b§lTeleportAnfrage §7von §b§l" + sender.getName() + " §7erhalten. ");
        textComponent.addExtra(getClickableMessage( "§8[§a§lAnnehmen§8]", "§8[§7Klicken zum §a§lAnnehmen§8]", "/tpa accept " + sender.getName()));
        textComponent.addExtra(getClickableMessage( " §8[§c§lAblehnen§8]", "§8[§7Klicken zum §c§lAblehnen§8]", "/tpadeny " + sender.getName()));
        return textComponent;
    }

//    private static TextComponent getMessage(Player sender){
//
//        TextComponent textComponent = new TextComponent(Main.prefix + "§b§l" + sender.getName() + " §7möchte sich zu dir §b§lteleportieren ");
//        textComponent.addExtra(getClickableMessage( "§8[§a§lAkzeptieren§8]]", "§8[§7Klicke zum §a§lakzeptieren§8]", "/tpaccept " + sender.getName()));
//        textComponent.addExtra(" §7um §7die §7anfrage §7anzunehmen.");
//
//        sender.sendMessage(String.valueOf(textComponent));
//
//        return textComponent;
//    }
}
