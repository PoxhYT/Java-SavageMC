package de.cb.poxh.commands;

import de.cb.poxh.main.Main;
import de.cb.poxh.manager.auction.AuctionManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.text.ParseException;

public class Command_ah implements CommandExecutor {

    private AuctionManager manager;
    public Command_ah(AuctionManager manager) {
        this.manager = manager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player) {
            Player player = ((Player) sender).getPlayer();


            if(args.length == 0) {
                manager.openInventory(player);
            }

            if(args.length == 3) {
                if(args[0].equalsIgnoreCase("add")) {
                    int days = Integer.valueOf(args[1]);
                    int price = Integer.valueOf(args[2]);

                    if(args[1].equalsIgnoreCase(String.valueOf(days))) {
                        if(days > 5) {
                            player.sendMessage(Main.instance.prefix + "Du kannst deine Items für maximal 5 Tage anbiten.");
                        } else {
                            try {
                                if(player.getItemInHand() != null) {
                                    manager.addItemToAuction(player.getDisplayName(), days, player, price);
                                } else {
                                    return true;
                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }else {
            sender.sendMessage(Main.instance.prefix + "§cBefehl kann über die Konsole nicht ausgeführt werden!");
        }


        return false;
    }
}
