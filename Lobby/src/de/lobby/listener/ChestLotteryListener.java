package de.lobby.listener;

import com.mongodb.client.MongoDatabase;
import com.rosemite.services.helper.Log;
import com.rosemite.services.main.MainService;
import com.rosemite.services.services.coin.CoinService;
import com.rosemite.services.services.ticket.TicketService;
import de.lobby.api.ItemBuilderAPI;
import de.lobby.main.Main;
import de.lobby.manager.TitleManager;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

public class ChestLotteryListener implements Listener {

    private MainService services;
    private MongoDatabase mongoDatabase;
    private TicketService ticketService;
    private CoinService coinService;
    private Main plugin;

    public ChestLotteryListener(MainService services, MongoDatabase mongoDatabase) {
        this.services = services;
        this.mongoDatabase = mongoDatabase;
        this.ticketService = services.getTicketService();
        this.coinService = services.getCoinService();
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        try {
            Player player = event.getPlayer();
            if(event.getClickedBlock().getType() == Material.ENDER_CHEST) {
                event.setCancelled(true);
                if(event.getAction() == Action.RIGHT_CLICK_BLOCK) {

                    //Init TicketServiceMap
                    HashMap<TicketService, UUID> ticketServiceMap = new HashMap<>();
                    services = MainService.getService(services);

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


                    Main.getInstance().inventoryManager.setLotteryInventory(player);
                }
            }
        }catch (NullPointerException e){}
    }

    @EventHandler
    public void onClick(InventoryClickEvent e){
        try {
            final Player p = (Player) e.getWhoClicked();
            if (e.getView().getTitle().equalsIgnoreCase("§eLottery")) {
                e.setCancelled(true);
                if (e.getCurrentItem().getData().getItemType().equals(Material.CHEST)) {
                    if (opendticket.get(p) == null || opendticket.get(p) == 5) {
                        if (ticketService.getTicketAmount(p.getUniqueId().toString()) >= 1) {
                            Main.getInstance().inventoryManager.setSelectInventory(p);
                            ticketService.removeTickets(p.getUniqueId().toString(), 1);
                            opendticket.put(p, 0);
                        } else {
                            p.sendMessage(Main.prefix + " §7Du hast nicht genügent Lotterietickets");
                        }
                    } else {
                        p.sendMessage(Main.prefix + " §7Du öffnest zur Zeit ein Lotterieticket");
                    }
                } else if (e.getCurrentItem().getData().getItemType().equals(Material.PAPER)) {
                    if (coinService.getCoinAmount(p.getUniqueId().toString()) >= 1000) {
                        ticketService.addTickets(p.getUniqueId().toString(), 1);
                        coinService.removeCoins(p.getUniqueId().toString(), 1000);
                        p.closeInventory();
                        Main.getInstance().inventoryManager.setLotteryInventory(p);
                    } else {
                        p.sendMessage(Main.prefix + " §7Du hast nicht genügent Money");
                    }
                }

                if (e.getCurrentItem().getData().getItemType().equals(Material.BARRIER)) {
                    p.closeInventory();
                    Main.getInstance().inventoryManager.setLotteryInventory(p);
                }
            }
        }catch (NullPointerException e1){}
    }

    private HashMap<Player, Integer> opendticket = new HashMap<Player, Integer>();
    private HashMap<Player, Integer> endgewinn1 = new HashMap<Player, Integer>();
    private HashMap<Player, Integer> endgewinn2 = new HashMap<Player, Integer>();
    private HashMap<Player, Integer> endgewinn3 = new HashMap<Player, Integer>();
    private HashMap<Player, Integer> endgewinn4 = new HashMap<Player, Integer>();
    private HashMap<Player, Integer> endgewinn5 = new HashMap<Player, Integer>();
    private HashMap<Player, Integer> endgewinn = new HashMap<Player, Integer>();

    @EventHandler
    public void onClickTicket(InventoryClickEvent e){
        try {
            Player p = (Player) e.getWhoClicked();
            if (e.getView().getTitle().equalsIgnoreCase("§3Wähle 5 Lose")) {
                e.setCancelled(true);
                if (e.getCurrentItem().getType().equals(Material.ENDER_CHEST) && e.getCurrentItem().getItemMeta().getDisplayName().equals("§6Ticket")) {
                    if (opendticket.get(p) != 5) {
                        Integer endopen = opendticket.get(p);
                        endopen++;
                        opendticket.put(p, endopen);

                        Random rm = new Random();
                        float chance = rm.nextFloat() * 100.0F;

                        float normal = 82.0F;
                        float epic = 87.0F;
                        float legendary = 99.9F;

                        Integer gewinn = 0;

                        if ((chance > 0.0F) && chance <= normal) {
                            Random r = new Random();
                            Integer gewinnchance = r.nextInt(500 - 0 + 1) + 0;
                            gewinn = gewinnchance;
                            if (opendticket.get(p) == 1) {
                                endgewinn1.put(p, gewinn);
                            } else if (opendticket.get(p) == 2) {
                                endgewinn2.put(p, gewinn);
                            } else if (opendticket.get(p) == 3) {
                                endgewinn3.put(p, gewinn);
                            } else if (opendticket.get(p) == 4) {
                                endgewinn4.put(p, gewinn);
                            } else if (opendticket.get(p) == 5) {
                                endgewinn5.put(p, gewinn);
                            }

                            ItemStack i = e.getCurrentItem();
                            ItemMeta m = i.getItemMeta();
                            i.setType(Material.CHEST);
                            m.setDisplayName("§e" + gewinnchance + " §8[§eNormal§8]");
                            m.addEnchant(Enchantment.LUCK, 1, true);
                            i.setItemMeta(m);
                            coinService.addCoins(p.getUniqueId().toString(), gewinnchance);
                            p.sendMessage(Main.prefix + " §7Du hast §e" + gewinnchance + " §7Money gewonnen");
                            p.playSound(p.getLocation(), Sound.LEVEL_UP, 1.0f, 1.5f);

                            endgewinn.put(p, endgewinn1.get(p) + endgewinn2.get(p) + endgewinn3.get(p) + endgewinn4.get(p) + endgewinn5.get(p));
                        } else if ((chance > normal) && chance <= epic) {
                            Random r = new Random();
                            Integer gewinnchance = r.nextInt(7500 - 500 + 1) + 500;
                            gewinn = gewinnchance;
                            if (opendticket.get(p) == 1) {
                                endgewinn1.put(p, gewinn);
                            } else if (opendticket.get(p) == 2) {
                                endgewinn2.put(p, gewinn);
                            } else if (opendticket.get(p) == 3) {
                                endgewinn3.put(p, gewinn);
                            } else if (opendticket.get(p) == 4) {
                                endgewinn4.put(p, gewinn);
                            } else if (opendticket.get(p) == 5) {
                                endgewinn5.put(p, gewinn);
                            }

                            ItemStack i = e.getCurrentItem();
                            ItemMeta m = i.getItemMeta();
                            i.setType(Material.IRON_INGOT);
                            m.setDisplayName("§6§l" + gewinnchance + " §8[§6§lEpic§8]");
                            m.addEnchant(Enchantment.LUCK, 2, true);
                            i.setItemMeta(m);
                            coinService.addCoins(p.getUniqueId().toString(), gewinnchance);
                            p.sendMessage(Main.prefix + " §7Du hast §6§l" + gewinnchance + " §7Money gewonnen");
                            p.playSound(p.getLocation(), Sound.NOTE_PLING, 1.0f, 1.5f);

                            endgewinn.put(p, endgewinn1.get(p) + endgewinn2.get(p) + endgewinn3.get(p) + endgewinn4.get(p) + endgewinn5.get(p));
                        } else if ((chance > epic) && chance <= legendary) {
                            Random r = new Random();
                            Integer gewinnchance = r.nextInt(15000 - 7500 + 1) + 7500;
                            gewinn = gewinnchance;
                            if (opendticket.get(p) == 1) {
                                endgewinn1.put(p, gewinn);
                            } else if (opendticket.get(p) == 2) {
                                endgewinn2.put(p, gewinn);
                            } else if (opendticket.get(p) == 3) {
                                endgewinn3.put(p, gewinn);
                            } else if (opendticket.get(p) == 4) {
                                endgewinn4.put(p, gewinn);
                            } else if (opendticket.get(p) == 5) {
                                endgewinn5.put(p, gewinn);
                            }

                            ItemStack i = e.getCurrentItem();
                            ItemMeta m = i.getItemMeta();
                            i.setType(Material.EMERALD);
                            m.setDisplayName("§d§l" + gewinnchance + " §8[§d§lLegendär§8]");
                            m.addEnchant(Enchantment.LUCK, 3, true);
                            i.setItemMeta(m);
                            coinService.addCoins(p.getUniqueId().toString(), gewinnchance);
                            p.sendMessage(Main.prefix + " §7Du hast §d§l" + gewinnchance + " §7Money gewonnen");
                            p.playSound(p.getLocation(), Sound.EXPLODE, 1.0f, 1.5f);

                            endgewinn.put(p, endgewinn1.get(p) + endgewinn2.get(p) + endgewinn3.get(p) + endgewinn4.get(p) + endgewinn5.get(p));
                        } else if ((chance > legendary) && (chance <= 100.0F)) {
                            Random r = new Random();
                            Integer gewinnchance = 1000000;
                            gewinn = gewinnchance;
                            if (opendticket.get(p) == 1) {
                                endgewinn1.put(p, gewinn);
                            } else if (opendticket.get(p) == 2) {
                                endgewinn2.put(p, gewinn);
                            } else if (opendticket.get(p) == 3) {
                                endgewinn3.put(p, gewinn);
                            } else if (opendticket.get(p) == 4) {
                                endgewinn4.put(p, gewinn);
                            } else if (opendticket.get(p) == 5) {
                                endgewinn5.put(p, gewinn);
                            }

                            ItemStack i = e.getCurrentItem();
                            ItemMeta m = i.getItemMeta();
                            i.setType(Material.NETHER_STAR);
                            m.setDisplayName("§5§l" + gewinnchance + " §8[§5§lJackpot§8]");
                            m.addEnchant(Enchantment.LUCK, 4, true);
                            i.setItemMeta(m);
                            coinService.addCoins(p.getUniqueId().toString(), gewinnchance);
                            for (Player all : Bukkit.getOnlinePlayers()) {
                                all.playSound(all.getLocation(), Sound.EXPLODE, 1.0f, 1.5f);
                                all.playSound(all.getLocation(), Sound.ENDERDRAGON_GROWL, 1.0f, 1.5f);
                                all.playSound(all.getLocation(), Sound.AMBIENCE_THUNDER, 1.0f, 1.5f);
                                TitleManager.setBar(all, "§6" + p.getName() + " §7hat den Jackpot von §5§l" + gewinnchance + " §7gezogen");
                                TitleManager.setTitle(all, 20, 50, 20, "§6" + p.getName() + " §7hat den Jackpot gezogen!", "§7Dieser beträgt §5§l" + gewinnchance);
                                createFrieWork(all);
                            }

                            endgewinn.put(p, endgewinn1.get(p) + endgewinn2.get(p) + endgewinn3.get(p) + endgewinn4.get(p) + endgewinn5.get(p));
                        }
                    } else {
                        p.closeInventory();
                        opendticket.remove(p);
                    }
                }
            }
        }catch (NullPointerException e1){}
    }

    @EventHandler
    public void onCloseTicket(final InventoryCloseEvent e){
        try {
            final Player p = (Player) e.getPlayer();
            if (e.getInventory().getTitle().equalsIgnoreCase("§3Wähle 5 Lose") && opendticket.get(p) != 5) {
                Bukkit.getScheduler().runTaskLater(Main.getInstance(), new Runnable() {
                    @Override
                    public void run() {

                        Main.getInstance().inventoryManager.setSelectInventory(p);
                    }
                }, 5);
            } else if (e.getInventory().getTitle().equalsIgnoreCase("§3Wähle 5 Lose")) {
                p.sendMessage(Main.prefix + " §7Dein Endbetrag beträgt §6" + endgewinn.get(p) + " §7Money");
                endgewinn.remove(p);
                endgewinn1.remove(p);
                endgewinn2.remove(p);
                endgewinn3.remove(p);
                endgewinn4.remove(p);
                endgewinn5.remove(p);
                Bukkit.getScheduler().runTaskLater(Main.getInstance(), new Runnable() {
                    @Override
                    public void run() {

                        Main.getInstance().inventoryManager.setLotteryInventory(p);
                    }
                }, 1);
            }
        }catch (NullPointerException e1){}
    }

    private void createFrieWork(Player p){
        Firework firework1 = p.getWorld().spawn(p.getLocation(), Firework.class);
        FireworkEffect effect1 = FireworkEffect.builder()
                .withColor(Color.BLUE)
                .withColor(Color.YELLOW)
                .withColor(Color.GREEN)
                .withColor(Color.RED)
                .flicker(true)
                .trail(true)
                .withFade(Color.AQUA)
                .withFade(Color.OLIVE)
                .with(FireworkEffect.Type.STAR)
                .build();
        FireworkMeta meta1 = firework1.getFireworkMeta();
        meta1.addEffect(effect1);
        meta1.setPower(1);
        firework1.setFireworkMeta(meta1);

        Firework firework2 = p.getWorld().spawn(p.getLocation(), Firework.class);
        FireworkEffect effect2 = FireworkEffect.builder()
                .withColor(Color.BLUE)
                .withColor(Color.YELLOW)
                .withColor(Color.GREEN)
                .withColor(Color.RED)
                .flicker(true)
                .trail(true)
                .withFade(Color.AQUA)
                .withFade(Color.OLIVE)
                .with(FireworkEffect.Type.BALL)
                .build();
        FireworkMeta meta2 = firework2.getFireworkMeta();
        meta2.addEffect(effect2);
        meta2.setPower(1);
        firework2.setFireworkMeta(meta2);

        Firework firework3 = p.getWorld().spawn(p.getLocation(), Firework.class);
        FireworkEffect effect3 = FireworkEffect.builder()
                .withColor(Color.BLUE)
                .withColor(Color.YELLOW)
                .withColor(Color.GREEN)
                .withColor(Color.RED)
                .flicker(true)
                .trail(true)
                .withFade(Color.AQUA)
                .withFade(Color.OLIVE)
                .with(FireworkEffect.Type.BALL_LARGE)
                .build();
        FireworkMeta meta3 = firework3.getFireworkMeta();
        meta3.addEffect(effect2);
        meta3.setPower(1);
        firework3.setFireworkMeta(meta3);

        Firework firework4 = p.getWorld().spawn(p.getLocation(), Firework.class);
        FireworkEffect effect4 = FireworkEffect.builder()
                .withColor(Color.BLUE)
                .withColor(Color.YELLOW)
                .withColor(Color.GREEN)
                .withColor(Color.RED)
                .flicker(true)
                .trail(true)
                .withFade(Color.AQUA)
                .withFade(Color.OLIVE)
                .with(FireworkEffect.Type.BURST)
                .build();
        FireworkMeta meta4 = firework4.getFireworkMeta();
        meta4.addEffect(effect4);
        meta4.setPower(1);
        firework4.setFireworkMeta(meta4);
    }
}
