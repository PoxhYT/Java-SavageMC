package de.sw.commands;

import de.sw.main.Main;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Command_enchantAll implements CommandExecutor {




    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player player = (Player) sender;

        try {
            if (sender instanceof Player) {
                if (args.length == 2) {
                    if (args[0].equalsIgnoreCase("sharpness")) {
                        if (args[1].equalsIgnoreCase("1")) {
                            for (ItemStack i : player.getInventory().getContents()) {
                                i.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 1);
                            }
                            player.sendMessage(Main.prefix + "§eDu hast deine Items erfolgreich verzaubert!");
                            player.playSound(player.getLocation(), Sound.LEVEL_UP, 1, 1);
                        } else if (args[1].equalsIgnoreCase("2")) {
                            for (ItemStack i : player.getInventory().getContents()) {
                                i.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 2);
                            }
                            player.sendMessage(Main.prefix + "§eDu hast deine Items erfolgreich verzaubert!");
                            player.playSound(player.getLocation(), Sound.LEVEL_UP, 1, 1);
                        }
                    } else if (args[0].equalsIgnoreCase("unbreaking")) {
                        if (args[1].equalsIgnoreCase("1")) {
                            for (ItemStack i : player.getInventory().getContents()) {
                                i.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
                            }
                            player.sendMessage(Main.prefix + "§eDu hast deine Items erfolgreich verzaubert!");
                            player.playSound(player.getLocation(), Sound.LEVEL_UP, 1, 1);
                        } else if (args[1].equalsIgnoreCase("2")) {
                            for (ItemStack i : player.getInventory().getContents()) {
                                i.addUnsafeEnchantment(Enchantment.DURABILITY, 2);
                            }
                            player.sendMessage(Main.prefix + "§eDu hast deine Items erfolgreich verzaubert!");
                            player.playSound(player.getLocation(), Sound.LEVEL_UP, 1, 1);
                        }
                    }
                } else {
                    player.sendMessage(Main.prefix + "§cBitte benutze /enchantAll <enchantment> <level>");
                    player.sendMessage(Main.prefix + "§cBitte benutze diesen Befehl nur an Schwerter!");
                }
            }
        }catch (NullPointerException e) {}
        return false;
    }
}
