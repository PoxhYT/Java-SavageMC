package de.bungee.commands;

import de.bungee.main.Main;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.Calendar;
import java.util.Date;

public class Command_getDate extends Command {
    public Command_getDate(String name) {
        super(name);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender instanceof ProxiedPlayer) {
            if(args.length == 0) {
                ProxiedPlayer player = (ProxiedPlayer) sender;
                Date date = new Date();

                Calendar c = Calendar.getInstance();
                c.setTime(date);

                c.add(Calendar.DAY_OF_MONTH, 5);

                player.sendMessage(new TextComponent(Main.prefix + "§eHeute ist der: " + c.getTime()));
            }
        }
    }
}
