package de.cb.poxh.commands.clan;

import de.cb.poxh.main.Main;
import de.cb.poxh.manager.clan.ClanManager;
import de.cb.poxh.manager.clan.PlayerClans;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Command_Clan implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;

        if(args.length == 1) {
            PlayerClans clans;
            if(ClanManager.getClan(player) != null) {
                clans = ClanManager.getClan(player);
                if(clans.isInClan(player)) {
                    if(args[0].equalsIgnoreCase("info")) {
                        clans.getClanInfo(player);
                    }
                }
            } else {
                player.sendMessage(Main.instance.prefix + "§cDu bist in kein Clan");
            }
        }

        if(args.length == 2) {
            String name = args[1];
            PlayerClans clans;
            if(args[0].equalsIgnoreCase("create")) {
                player.sendMessage(name);
                if(args[1].equalsIgnoreCase(name)) {
                    if(ClanManager.getClan(player) == null) {
                        if(name.length() == 5) {
                            ClanManager.createClan(player, name);
                            player.sendMessage(Main.instance.prefix + "Du hast den §cClan §8[§e" + name + "§8] §7erstellt");
                        } else {
                            player.sendMessage(Main.instance.prefix + "Der §cClanname §7muss maximal §e5 Zeichen §7beinhalten!");
                            return true;
                        }
                    } else {
                        player.sendMessage(Main.instance.prefix + "§cDu bist bereits in einem Clan");
                    }
                }
            }

            if(args[0].equalsIgnoreCase("invite")) {
                Player target = Bukkit.getPlayer(args[1]);
                if (args[1].equalsIgnoreCase(target.getName())) {
                    if (ClanManager.getClan(player) != null) {
                        clans = ClanManager.getClan(player);
                        if(!clans.isInClan(target)) {
                            if (target != null) {
                                clans.invite(target);
                                player.sendMessage(Main.instance.prefix + "Du hast " + target.getName() + " §7in deinem §cClan §7eingeladen");
                                target.sendMessage(Main.instance.prefix + player.getName() + " §7hat dich in seinem Clan eingeladen.");
                                target.sendMessage(Main.instance.prefix + "Benutze den Befehl /§eclan accept " + player.getName() + " §7um den Clan zu betreten");
                            } else {
                                player.sendMessage(Main.instance.prefix + "§cDieser Spieler ist offline");
                            }
                        } else {
                            player.sendMessage(Main.instance.prefix + target.getName() + " §cist bereits in einem Clan");
                        }
                    }
                }
            }

            if(args[0].equalsIgnoreCase("accept")) {
                if (ClanManager.getClan(player) != null) {
                    player.sendMessage(Main.instance.prefix + "§cDu bist bereits in einer Party!");
                }

                Player target = Bukkit.getPlayer(args[1]);
                if (target == null) {
                    player.sendMessage(Main.instance.prefix + "§c" + args[1] + " §c ist nicht online!");
                }

                if (ClanManager.getClan(target) == null) {
                    player.sendMessage(Main.instance.prefix + target.getDisplayName() + " §cleitet aktuell keine Party!");
                }

                PlayerClans clan = ClanManager.getClan(target);
                if (!clan.hasRequest(player)) {
                    player.sendMessage(Main.instance.prefix + "§cDu hast keine Einladung von " + target.getDisplayName() + " §cerhalten!");
                } else {
                    if(target != player && target != null) {
                        player.sendMessage(Main.instance.prefix + "Du hast die §cClaneinladung §7von " + clan.getLeader().getDisplayName() + " §aangenommen§7!");
                        clan.addPlayer(player);
                        clan.getLeader().sendMessage(Main.instance.prefix + player.getDisplayName() + " §7hat den §cClan §7beigetreten.");
                    }
                }
            }

            if(args[0].equalsIgnoreCase("info")) {
                Player target = Bukkit.getPlayer(args[1]);
                if (target != null) {
                    if (ClanManager.getClan(target) != null) {
                        clans = ClanManager.getClan(target);
                        if(clans.isInClan(target)) {
                            clans.getClanInfo(player);
                        } else {
                            player.sendMessage(Main.instance.prefix + target.getName() + " §cbefindet sich aktuell in keinem Clan");
                        }
                    } else {
                        player.sendMessage(Main.instance.prefix + target.getName() + " §cbefindet sich aktuell in keinem Clan");
                    }
                } else {
                    player.sendMessage(Main.instance.prefix + "§cDieser Spieler ist nicht online!");
                }
            }


        } else {
            player.sendMessage(Main.instance.prefix + "Bitte benutze /clan create <name>");
        }

        return false;
    }
}
