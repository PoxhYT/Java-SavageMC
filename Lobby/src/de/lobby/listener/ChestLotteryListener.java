package de.lobby.listener;

import com.mongodb.client.MongoDatabase;
import com.rosemite.services.helper.Log;
import com.rosemite.services.main.MainService;
import com.rosemite.services.services.ticket.TicketService;
import de.lobby.api.ItemBuilderAPI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.UUID;

public class ChestLotteryListener implements Listener {

    private MainService services;
    private MongoDatabase mongoDatabase;

    public ChestLotteryListener(MainService services, MongoDatabase mongoDatabase) {
        this.services = services;
        this.mongoDatabase = mongoDatabase;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        try {
            Player player = event.getPlayer();
            if (event.getItem().getType() == Material.ENDER_CHEST) {

                //Init TicketServiceMap
                HashMap<TicketService, UUID> ticketServiceMap = new HashMap<>();
                services = MainService.getService(services);

                //Create Inventory...
                TicketService ticketService = services.getTicketService();
                if (ticketService == null) {
                    ticketService = new TicketService(mongoDatabase, services);
                    Log.d("NICE");
                }

                ticketServiceMap.put(ticketService, player.getUniqueId());

                Inventory inventory = Bukkit.createInventory(null, InventoryType.HOPPER, "§eLottery");
                inventory.setItem(0, new ItemBuilderAPI(Material.PAPER).setDisplayName("§2Einlösen").setLore("", "§8Anzahl: §6"
                        + ticketService.getTicketAmount(player.getUniqueId().toString()), "§c§oZum öffnen klicken!").build());
                player.openInventory(inventory);
            }
        }catch (NullPointerException e){}
    }
}
