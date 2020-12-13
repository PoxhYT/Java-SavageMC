package com.poxh.party.commands;

import com.poxh.party.Main;
import com.poxh.party.manager.PartyManager;
import com.poxh.party.manager.PlayerParty;
import com.sun.xml.internal.ws.wsdl.writer.document.Part;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class Command_party extends Command {
    public Command_party(String name) {
        super(name);
    }

    private static PartyManager manager = Main.getInstance().getManager();

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender instanceof ProxiedPlayer) {
            ProxiedPlayer player = (ProxiedPlayer) sender;
            if(args.length == 0) {
                Main.getInstance().getManager().sendHelp(player);
            } else if(args.length == 1) {
                if(args[0].equalsIgnoreCase("info")) {
                    PlayerParty party = PartyManager.getParty(player);
                    if(!(party == null)) {
                        if (!party.isInParty(player)) {
                            player.sendMessage(new TextComponent(Main.Prefix + "§cDu bist in keine Party!"));
                        } else {
                            party.getPartyInfo(player);
                        }
                    } else {
                        player.sendMessage(new TextComponent(Main.Prefix + "§cDu bist in keine Party!"));
                    }
                }
            }else if(args.length == 2) {
                if(args[0].equalsIgnoreCase("invite")) {
                    ProxiedPlayer target = BungeeCord.getInstance().getPlayer(args[1]);
                    if(target == null) {
                        player.sendMessage(new TextComponent(Main.Prefix + "§cDieser Spieler ist derzeit nicht online!"));
                        return;
                    }

                    if(target != player) {
                        PlayerParty party;
                        if (PartyManager.getParty(player) != null && !(party = PartyManager.getParty(player)).isLeader(player)) {
                            player.sendMessage(new TextComponent(Main.Prefix + "§cDu bist nicht der Leiter der Party!"));
                            return;
                        }

                        if(PartyManager.getParty(player) != null && (party = PartyManager.getParty(player)).hasRequest(target)) {
                            player.sendMessage(new TextComponent(Main.Prefix + "§cDer Spieler hat bereits eine Einladung von deiner Party"));
                            player.sendMessage(new TextComponent("§cerhalten!"));
                            return;
                        }

                        if(PartyManager.getParty(target) != null) {
                            player.sendMessage(new TextComponent(Main.Prefix + "§cDieser Spieler ist bereits in einer Party!"));
                            return;
                        }

                        if(PartyManager.getParty(player) != null) {
                            party = PartyManager.getParty(player);
                            if(party.isInParty(target)) {
                                player.sendMessage(new TextComponent(Main.Prefix + "§cDieser Spieler ist bereits in einer Party!"));
                                return;
                            } else {
                                player.sendMessage(new TextComponent(Main.Prefix + "Du hast " + target.getDisplayName() + " §7in deine §5Party §7eingeladen!"));
                                party.invite(target);
                            }
                        } else {
                            PartyManager.createParty(player);
                            player.sendMessage(new TextComponent(Main.Prefix + "Du hast " + target.getDisplayName() + " §7in deine §5Party §7eingeladen!"));
                            party = PartyManager.getParty(player);
                            party.invite(target);
                        }

                    } else {
                        player.sendMessage(new TextComponent(Main.Prefix + "§cDu kannst dich nicht selber einladen!"));
                        return;

                    }

                } else if (args[0].equalsIgnoreCase("accept")) {
                    if (PartyManager.getParty(player) != null) {
                        player.sendMessage(new TextComponent(Main.Prefix + "§cDu bist bereits in einer Party!"));
                        return;
                    }
                    ProxiedPlayer target = BungeeCord.getInstance().getPlayer(args[1]);
                    if (target == null) {
                        player.sendMessage(new TextComponent(Main.Prefix + "§c" + args[1] + " §c ist nicht online!"));
                        return;
                    }
                    if (PartyManager.getParty(target) == null) {
                        player.sendMessage(new TextComponent(Main.Prefix + target.getDisplayName() + " §cleitet aktuell keine Party!"));
                        return;
                    }

                    PlayerParty party = PartyManager.getParty(target);
                    if (!party.hasRequest(player)) {
                        player.sendMessage(new TextComponent(Main.Prefix + "§cDu hast keine Einladung von " + target.getDisplayName() + " §cerhalten!"));
                    } else {
                        if(target != player && target != null) {
                            player.sendMessage(new TextComponent(Main.Prefix + "Du hast die §5Partyeinladung §7von " + party.getLeader().getDisplayName() + " §aangenommen§7!"));
                            party.addPlayer(player);
                            party.getLeader().sendMessage(new TextComponent(Main.Prefix + player.getDisplayName() + " §7ist der §5Party §7beigetreten."));
                        }
                    }
                } else if(args[0].equalsIgnoreCase("deny")) {
                    ProxiedPlayer target = BungeeCord.getInstance().getPlayer(args[1]);
                    if (target == null) {
                        player.sendMessage(new TextComponent(Main.Prefix + "§c" + args[1] + " §c ist nicht online!"));
                        return;
                    }
                    if (PartyManager.getParty(target) == null) {
                        player.sendMessage(new TextComponent(Main.Prefix + "§cDu bist nicht in der Party von " + args[1] + "§c!"));
                        return;
                    }

                    PlayerParty party = PartyManager.getParty(target);
                    if (!party.hasRequest(player)) {
                        player.sendMessage(new TextComponent(Main.Prefix + "§cDu hast keine Einladung von " + target.getDisplayName() + " §cerhalten!"));
                    } else {
                        party.removeInvite(player);
                        player.sendMessage(new TextComponent(Main.Prefix + "§cDu hast die Einladung von " + target.getDisplayName() + " §cabgelehnt!"));
                        target.sendMessage(new TextComponent(Main.Prefix + player.getDisplayName() + " §chat deine Einladung abgelehnt!"));
                    }
                } else if(args[0].equalsIgnoreCase("kick")){
                    ProxiedPlayer target = BungeeCord.getInstance().getPlayer(args[1]);
                    PlayerParty party = PartyManager.getParty(target);
                    if(party.isLeader(player)) {
                        if (party.isInParty(target)) {
                            party.removePlayer(target);
                            for (ProxiedPlayer players : party.getMembers()) {
                                players.sendMessage(new TextComponent(Main.Prefix + target.getDisplayName() + " §cwurde aus der Party geworfen!"));
                            }
                            target.sendMessage(new TextComponent(Main.Prefix + "§cDu wurdest aus der Party geworfen!"));
                            party.getLeader().sendMessage(new TextComponent(Main.Prefix + "§cDu hast " + target.getDisplayName() + " §caus der Party geworfen!"));
                        } else {
                            player.sendMessage(new TextComponent(Main.Prefix + "§cDieser Spieler ist nicht in deiner Party!"));
                        }
                    } else {
                        player.sendMessage(new TextComponent(Main.Prefix + target.getDisplayName() + " §cist nicht in deine Party!"));
                    }
                } else if(args[0].equalsIgnoreCase("leave")) {
                    ProxiedPlayer target = BungeeCord.getInstance().getPlayer(args[1]);
                    if (PartyManager.getParty(target) == null) {
                        player.sendMessage(new TextComponent(Main.Prefix + "§cDu bist nicht in der Party von " + args[1] + "§c!"));
                        return;
                    }
                    PlayerParty party = PartyManager.getParty(target);
                    if(party.isInParty(player)) {
                        party.removePlayer(player);
                    }
                }
            } else {
                PartyManager.sendHelp(player);
            }
        } else {
            sender.sendMessage(new TextComponent(Main.Prefix + Main.noConsole));
        }
    }
}
