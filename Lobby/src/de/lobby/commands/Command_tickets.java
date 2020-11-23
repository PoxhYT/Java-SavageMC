package de.lobby.commands;

import com.mongodb.client.MongoDatabase;
import com.rosemite.services.helper.Log;
import com.rosemite.services.main.MainService;
import com.rosemite.services.services.coin.CoinService;
import com.rosemite.services.services.ticket.TicketService;
import de.lobby.main.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class Command_tickets implements CommandExecutor {

    private MainService services;
    private MongoDatabase mongoDatabase;

    public Command_tickets(MongoDatabase mongoDatabase, MainService services) {
        this.services = services;
        this.mongoDatabase = mongoDatabase;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;
            HashMap<TicketService, UUID> ticketServiceMap = new HashMap<>();
            services = MainService.getService(services);
            if(args.length == 0) {
                TicketService ticketService = services.getTicketService();

                if(ticketService == null) {
                    ticketService = new TicketService(mongoDatabase, services);
                    Log.d("NICE");
                }

                ticketServiceMap.put(ticketService, player.getUniqueId());
                Log.d("WELL");
                player.sendMessage(Main.prefix + "Du besitzt aktuell §e" + ticketService.getTicketAmount(player.getUniqueId().toString()) + " Tickets");

            } else
                player.sendMessage(Main.prefix + "Bitte benutze den Befehl /§ecoins");
        } else
            sender.sendMessage("§cNÖ");
        return false;
    }
}
