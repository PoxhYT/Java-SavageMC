package de.poxh.friends.commands;

import com.rosemite.models.friends.FriendsInfo;
import com.rosemite.models.friends.ResponseCode;
import com.rosemite.models.service.common.IService;
import com.rosemite.models.service.player.PlayerInfo;
import de.poxh.friends.listener.TextComponentBuilder;
import de.poxh.friends.main.Main;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.Map;

public class Command_friend extends Command {

    private IService service;

    public Command_friend(String name, IService service) {
        super(name);
        this.service = service;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        ProxiedPlayer player = (ProxiedPlayer) sender;

        if (args.length == 2) {
            PlayerInfo target = service.getPlayerService().getPlayerInfoByName(args[1]);
                if(target != null) {
                    if (args[0].equalsIgnoreCase("add")) {

                        TextComponent component = new TextComponentBuilder("§7" + player.getName() + " §7hat dir eine Anfrage gesendet ").
                                addHover("§7Entscheide dich").build();
                        TextComponent component1 = new TextComponentBuilder("§7[§aAnnehmen§7] ").addHover("§aAnnehmen").
                                addClickEvent(ClickEvent.Action.RUN_COMMAND, "/friend accept " + player.getName()).build();
                        TextComponent component2 = new TextComponentBuilder("§7[§cAblehnen§7] ").addHover("§cAblehnen").
                                addClickEvent(ClickEvent.Action.RUN_COMMAND, "/gamemode 1 " + player.getName()).build();
                        component.addExtra(component1);
                        component.addExtra(component2);

                        if(!service.getFriendsService().getPlayerFriendsInfo(target.getUuid()).getValue().openFriendRequests.contains(player.getUniqueId().toString())) {
                            if (ProxyServer.getInstance().getPlayer(target.getUuid()) != null) {
                                Main.sendMessage(Main.prefix + "Deine §aAnfrage §7an " + target.getPlayername() + " §7wurde §aerfolgreich §7versendet.", player);
                                service.getFriendsService().makeFriendRequest(player.getUniqueId().toString(), target.getUuid());
                                ProxyServer.getInstance().getPlayer(target.getPlayername()).sendMessage(new TextComponent(Main.prefix + "Du hast eine §aAnfrage §7von " + player.getDisplayName() + " §7erhalten."));
                                ProxyServer.getInstance().getPlayer(target.getPlayername()).sendMessage(component);
                            } else {
                                Main.sendMessage(Main.prefix + "Deine §aAnfrage §7an " + target.getPlayername() + " §7wurde §aerfolgreich", player);
                                service.getFriendsService().makeFriendRequest(player.getUniqueId().toString(), target.getUuid());
                            }
                        } else {
                            Main.sendMessage(Main.prefix + "§7Du hast bereits " + target.getPlayername() + " §7eine §eAnfrage §7gesendet.", player);
                        }

                        ResponseCode code = service.getFriendsService().makeFriendRequest(player.getUniqueId().toString(), target.getUuid());
                        if (code != ResponseCode.Successful) {
                            switch (code) {
                                case IsUnderDeniedFriends:
                                    break;
                                case AreAlreadyFriends:
                                    Main.sendMessage(Main.prefix + "Du bist bereits mit " + target.getPlayername() + " §7befreundet.", player);
                                    break;
                                case RequestStillPending:
                                    break;
                                case AddedYourSelf:
                                    Main.sendMessage(Main.prefix + "§cDu kannst dir selber keine Anfragen schicken", player);
                                    break;
                            }
                            return;
                        }


                    } else if (args[0].equalsIgnoreCase("accept")) {
                        String uuid = target.getUuid();
                        Map.Entry<ResponseCode, FriendsInfo> friendsInfo = service.getFriendsService().getPlayerFriendsInfo(player.getUniqueId().toString());
                        if(friendsInfo.getKey() != ResponseCode.Successful) {
                            return;
                        }
                        boolean hasRequested = false;
                        if (friendsInfo.getValue().openFriendRequests.contains(uuid)) {
                            hasRequested = true;
                        }
                        if (hasRequested) {
                            service.getFriendsService().acceptFriendRequest(player.getUniqueId().toString(), uuid);
                            Main.sendMessage(Main.prefix + "Du bist nun mit " + target.getPlayername() + " §7befreundet.", player);
                            ProxyServer.getInstance().getPlayer(args[1]).sendMessage(new TextComponent(Main.prefix + "Du bist nun mit " + player.getDisplayName() + " §7befreundet."));
                        }
                    }
                } else {
                    Main.sendMessage(Main.prefix + "§cDieser Spieler ist nicht in der Datenbank registriert.", player);
                }
        } else {
            Main.sendMessage(Main.prefix + "§bFreundeSystem §7- §bBefehle", player);
            Main.sendMessage("/§bfriend add <Spieler> §7Fügt einen Freund hinzu", player);
            Main.sendMessage("/§bfriend remove <Spieler> §7Entfernt einen Freund", player);
            Main.sendMessage("/§bfriend info <Spieler> §7Zeigt den Status eurer Freundschaft an", player);
            Main.sendMessage("/§bfriend jump <Spieler> §7Springe deinen Freund nach", player);
            Main.sendMessage("/§bmsg <Spieler> §7Sende einen Freund eine Nachricht", player);
        }
    }
}
