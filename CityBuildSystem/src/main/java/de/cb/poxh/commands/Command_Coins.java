package de.cb.poxh.commands;

import com.rosemite.models.citybuild.CityBuildInfos;
import com.rosemite.models.service.player.PlayerInfo;
import de.cb.poxh.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Command_Coins implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player player = (Player) sender;

        if(sender instanceof Player) {
            if(args.length == 3) {
                CityBuildInfos target = Main.instance.service.getCityBuildService().getPlayerInfoByName(args[2]);

                player.sendMessage(String.valueOf(args[2]));
                player.sendMessage(String.valueOf(args[2]));
                player.sendMessage(String.valueOf(args[2]));
                player.sendMessage(String.valueOf(target));
                player.sendMessage(String.valueOf(target));
                player.sendMessage(String.valueOf(target));
                player.sendMessage(String.valueOf(target));


                int playerAmount = Main.instance.service.getCityBuildService().getCoinAmount(player.getUniqueId().toString());
                int targetAmount = Main.instance.service.getCityBuildService().getCoinAmount(target.getUUID());
                if (player.hasPermission("citybuild.admin")) {
                    if (args[0].equalsIgnoreCase("add")) {
                        if (target != null) {
                            if(Bukkit.getPlayer(args[2]) != player) {
                                try {
                                    int amount = Integer.valueOf(args[1]);
                                    int addResult = targetAmount + amount;
                                    if (args[1].equalsIgnoreCase(String.valueOf(amount))) {
                                        Main.instance.service.getCityBuildService().addCoins(target.getUUID(), amount);
                                        player.sendMessage(Main.instance.prefix + "Du hast " + target.getPlayerName() + " §e" + amount + " Coins §7hinzugefügt");
                                        player.sendMessage(target.getPlayerName());
                                        if (Bukkit.getPlayer(target.getPlayerName()) != null) {
                                            Bukkit.getPlayer(target.getPlayerName()).sendMessage(Main.instance.prefix + "Du hast §e" + amount + " Coins §7gut geschrieben bekommen.");
                                            Bukkit.getPlayer(target.getPlayerName()).sendMessage(Main.instance.prefix + "Dein Kontostand lautet nun: §e" + addResult + " Coins§7.");
                                            Bukkit.getPlayer(target.getPlayerName()).playSound(Bukkit.getPlayer(target.getPlayerName()).getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
                                        }
                                    }
                                } catch (NumberFormatException e) {}
                            } else {
                                try {
                                    int amount = Integer.valueOf(args[1]);
                                    int addResult = playerAmount + amount;
                                    Main.instance.service.getCityBuildService().addCoins(player.getUniqueId().toString(), amount);
                                    player.sendMessage(Main.instance.prefix + "Du hast dir §e" + amount + " Coins §7hinzugefügt.");
                                    player.sendMessage(Main.instance.prefix + "Dein Kontostand lautet nun: §e" + addResult + " Coins§7.");
                                    player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
                                } catch (NumberFormatException e) {}
                            }
                        } else {
                            player.sendMessage(Main.instance.prefix + "§cDieser Spieler ist nicht in der Datenbank registriert.");
                        }
                    }

                    if(args[0].equalsIgnoreCase("remove")) {
                        if (target != null) {
                            if (Bukkit.getPlayer(args[2]) != player) {
                                try {
                                    int amount = Integer.valueOf(args[1]);
                                    int removeResult = targetAmount - amount;

                                    if(removeResult < 0) {
                                        player.sendMessage(Main.instance.prefix + "§cDie Anzahl die du entfernen möchtest ist zu groß!");
                                        player.sendMessage(Main.instance.prefix + "§7Mögliche §eAnzahl §7die du entfernen könntest: §e" + target + " Coins§7.");
                                    } else {
                                        Main.instance.service.getCityBuildService().removeCoins(target.getUUID(), amount);
                                        player.sendMessage(Main.instance.prefix + "Du hast " + target.getPlayerName() + " §e" + amount + " Coins §7entfernt.");
                                        if(Bukkit.getPlayer(target.getPlayerName()).isOnline()) {
                                            Bukkit.getPlayer(target.getPlayerName()).sendMessage(Main.instance.prefix + "§cDir wurden " + amount + " Coins entfernt");
                                            Bukkit.getPlayer(target.getPlayerName()).sendMessage(Main.instance.prefix + "Dein §eKontostand §7lautet nun: §e" + targetAmount + " Coins§7.");
                                        }
                                    }
                                } catch (NumberFormatException e) {}
                            } else {
                                try {
                                    int amount = Integer.valueOf(args[1]);
                                    int removeResult = playerAmount - amount;

                                    if(removeResult < 0) {
                                        if(playerAmount == 0) {
                                            player.sendMessage(Main.instance.prefix + "§cDu kannst dir keine weiteren Coins entfernen da du keine besitzt!");
                                        } else {
                                            player.sendMessage(Main.instance.prefix + "§cDie Anzahl die du entfernen möchtest ist zu groß!");
                                            player.sendMessage(Main.instance.prefix + "§7Mögliche §eAnzahl §7die du entfernen könntest: §e" + playerAmount + " Coins§7.");
                                        }
                                    } else {
                                        Main.instance.service.getCityBuildService().removeCoins(player.getUniqueId().toString(), amount);
                                        player.sendMessage(Main.instance.prefix + "§cDu hast dir " + amount + " Coins entfernt!");

                                        if(playerAmount == 0) {
                                            player.sendMessage(Main.instance.prefix + "Dein §eKontostand §7lautet nun: §e0 Coins§7.");
                                        } else {
                                            player.sendMessage(Main.instance.prefix + "Dein §eKontostand §7lautet nun: §e" + playerAmount + " Coins§7.");
                                        }
                                    }
                                } catch (NumberFormatException e) {}
                            }
                        } else {
                            player.sendMessage(Main.instance.prefix + "§cDieser Spieler ist nicht in der Datenbank registriert.");
                        }
                    }

                    if (args[0].equalsIgnoreCase("get")) {
                        if(target != null) {
                            if(Bukkit.getPlayer(args[0]) != player) {
                                player.sendMessage(Main.instance.prefix + target.getDisplayName() + " §7besitzt §e" + targetAmount + " Coins§7.");
                            } else {
                                player.sendMessage(Main.instance.prefix + "Du besitzt §e" + playerAmount + " Coins§7.");
                            }
                        } else {
                            player.sendMessage(Main.instance.prefix + "§cDieser Spieler ist nicht in der Datenbank registriert.");
                        }
                    }
                } else {
                    player.sendMessage("§7§m----------§cCoinsSystem§7§m----------");
                    player.sendMessage("§7/§ccoins spend §7<§cAnzahl§7> <§cSpieler§7>");
                    player.sendMessage("§7➥ Spende Coins an Spieler");
                    player.sendMessage("§7/§ccoins get <§cSpieler§7> cb");
                    player.sendMessage("§7➥ Zeigt die Coins der Spieler an");
                    player.sendMessage("§7§m-----------------------------------");
                }
            }
        } else {
            sender.sendMessage(Main.instance.prefix + "§cDiesen Befhel kann nur ein Spieler ausführen");
        }

        return false;
    }
}
