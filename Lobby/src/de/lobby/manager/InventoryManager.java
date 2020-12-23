package de.lobby.manager;

import com.mongodb.client.MongoDatabase;
import com.rosemite.services.main.MainService;
import com.rosemite.services.services.coin.CoinService;
import com.rosemite.services.services.ticket.TicketService;
import de.lobby.api.ItemBuilderAPI;
import de.lobby.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.UUID;

public class InventoryManager {

    private MainService services;
    private MongoDatabase mongoDatabase;
    private TicketService ticketService;
    private CoinService coinService;

    public InventoryManager(MongoDatabase mongoDatabase, MainService services) {
        this.services = services;
        this.mongoDatabase = mongoDatabase;
    }

    public void openNavigatorInventory(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 45, "§eNavigator");
        for(int i = 0; i < 45; i++)
        inventory.setItem(i, new ItemBuilderAPI(Material.STAINED_GLASS_PANE).setDisplayName("§1 ").build());
        inventory.setItem(12, new ItemBuilderAPI(Material.GRASS).setDisplayName("§bSkyWars").build());
        inventory.setItem(14, new ItemBuilderAPI(Material.BED).setDisplayName("§cBedWars").build());
        inventory.setItem(20, new ItemBuilderAPI(Material.MUSHROOM_SOUP).setDisplayName("§9Soup-Training").build());
        inventory.setItem(22, new ItemBuilderAPI(Material.NETHER_STAR).setDisplayName("§eSpawn").build());
        inventory.setItem(24, new ItemBuilderAPI(Material.STICK).setDisplayName("§2MLG-RUSH").build());
        inventory.setItem(32, new ItemBuilderAPI(Material.GOLDEN_APPLE).setDisplayName("§eUHC").build());
        inventory.setItem(30, new ItemBuilderAPI(Material.GOLDEN_CARROT).setDisplayName("§6SPEED-UHC").build());
        player.openInventory(inventory);
    }

    public void setLobbyInventory(Player player) {
        player.getInventory().setItem(0, new ItemBuilderAPI(Material.COMPASS).setDisplayName("§eNavigator").build());
    }

    public void setLotteryInventory(Player player) {

        //Init TicketServiceMap
        HashMap<TicketService, UUID> ticketServiceMap = new HashMap<>();
        services = MainService.getService(services);
        ticketService = services.getTicketService();
        coinService = services.getCoinService();

        if (ticketService == null) {
            ticketService = new TicketService(mongoDatabase, services);
        }
        ticketServiceMap.put(ticketService, player.getUniqueId());

        //Init CoinsServiceMap
        HashMap<CoinService, UUID> coinServiceMap = new HashMap<>();
        if(coinService == null) {
            coinService = new CoinService(mongoDatabase, services);
        }
        coinServiceMap.put(coinService, player.getUniqueId());

        Inventory inventory = Bukkit.createInventory(null, InventoryType.HOPPER, "§eLottery");
        inventory.setItem(0, new ItemBuilderAPI(Material.CHEST).setDisplayName("§2Einlösen").setLore("", "§8Anzahl: §6"
                + ticketService.getTicketAmount(player.getUniqueId().toString()), "§c§oZum öffnen klicken!").build());
        inventory.setItem(4, new ItemBuilderAPI(Material.PAPER).setDisplayName("§cKaufen").setLore("", "§8Kosten: §61000 Coins", "§c§oZum kaufen klicken!").build());
        player.openInventory(inventory);
    }

    public void setSelectInventory(Player player) {
        Inventory inv = Bukkit.createInventory(null, 9*6, "§3Wähle 5 Lose");
        for(int i = 0; i < 54; i++){
            inv.setItem(i, new ItemBuilderAPI(Material.ENDER_CHEST).setDisplayName("§6Ticket").build());
        }
        player.openInventory(inv);
    }

    public void openRewardInventory(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 27, "§eBelohnungen");
        for (int i = 0; i < inventory.getSize(); i++) {
            inventory.setItem(i, new ItemBuilderAPI(Material.STAINED_GLASS_PANE,(short)7).setDisplayName("§e").build());
        }
        player.openInventory(inventory);
    }
}
