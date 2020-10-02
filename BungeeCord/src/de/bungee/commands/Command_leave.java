package de.bungee.commands;

import de.bungee.main.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Command_leave implements CommandExecutor {

    private Main instance;

    public Command_leave(Main instance) {
        this.instance = instance;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;

            ByteArrayOutputStream b = new ByteArrayOutputStream();
            DataOutputStream out = new DataOutputStream(b);
            try{
                out.writeUTF("Connect");
                out.writeUTF("lobby");
            }catch (IOException e) {
                e.printStackTrace();
            }
            player.sendPluginMessage(instance, "BungeeCord", b.toByteArray());
        }
        return false;
    }
}

