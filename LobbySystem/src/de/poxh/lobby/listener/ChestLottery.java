package de.poxh.lobby.listener;

import com.mongodb.client.MongoDatabase;
import com.rosemite.models.service.common.IService;
import com.rosemite.services.coin.CoinService;
import com.rosemite.services.main.MainService;
import com.rosemite.services.ticket.TicketService;
import de.poxh.lobby.api.ItemBuilderAPI;
import de.poxh.lobby.main.Main;
import de.poxh.lobby.manager.TitleManager;
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

public class ChestLottery implements Listener {

    private static IService service;
    private static MongoDatabase database;
    private Main instance;

    public ChestLottery(IService service, MongoDatabase database, Main instance) {
        this.service = service;
        this.database = database;
        this.instance = instance;
    }

    @EventHandler
    public void onInt(PlayerInteractEvent e){
        try {
            Player p = e.getPlayer();

            HashMap<CoinService, UUID> coinServiceMap = new HashMap<>();
            service = MainService.getService(service);

            //Initializing CoinServices
            CoinService coinService = service.getCoinService();
            if (coinService == null) {
                coinService = new CoinService(database, service);
            }
            coinServiceMap.put(coinService, p.getUniqueId());


            //Initializing TicketServices
            service = MainService.getService(service);
            HashMap<TicketService, UUID> ticketServiceMap = new HashMap<>();
            TicketService ticketService = service.getTicketService();
            if (ticketService == null) {
                ticketService = new TicketService(database, service);
            }
            ticketServiceMap.put(ticketService, p.getUniqueId());

            if (e.getClickedBlock().getType() == Material.ENDER_CHEST) {
                e.setCancelled(true);
                if (e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.LEFT_CLICK_BLOCK) {
                    openSelectInv(p);
                }
            }
        }catch (NullPointerException e1){}
    }

    @EventHandler
    public void onClick(InventoryClickEvent e){
        try {
            final Player p = (Player) e.getWhoClicked();

            HashMap<CoinService, UUID> coinServiceMap = new HashMap<>();
            service = MainService.getService(service);

            //Initializing CoinServices
            CoinService coinService = service.getCoinService();
            if (coinService == null) {
                coinService = new CoinService(database, service);
            }
            coinServiceMap.put(coinService, p.getUniqueId());


            //Initializing TicketServices
            service = MainService.getService(service);
            HashMap<TicketService, UUID> ticketServiceMap = new HashMap<>();
            TicketService ticketService = service.getTicketService();
            if (ticketService == null) {
                ticketService = new TicketService(database, service);
            }
            ticketServiceMap.put(ticketService, p.getUniqueId());


            if (e.getView().getTitle().equalsIgnoreCase("§6Lotterie")) {
                e.setCancelled(true);
                if (e.getCurrentItem().getData().getItemType().equals(Material.PAPER)) {
                    if (opendticket.get(p) == null || opendticket.get(p) == 5) {
                        if (ticketService.getTicketAmount(p.getUniqueId().toString()) >= 1) {
                            Inventory inv = Bukkit.createInventory(null, 9 * 6, "§3Wähle 5 Lose");

                            for (int i = 0; i < 54; i++) {
                                inv.setItem(i, new ItemBuilderAPI(Material.ENDER_CHEST).setDisplayName("§6Ticket").build());
                            }

                            ticketService.removeTickets(p.getUniqueId().toString(), 1);
                            p.openInventory(inv);
                            opendticket.put(p, 0);
                        } else {
                            p.sendMessage(Main.prefix + " §7Du hast nicht genügent Lotterietickets");
                        }
                    } else {
                        p.sendMessage(Main.prefix + " §7Du öffnest zur Zeit ein Lotterieticket");
                    }
                } else if (e.getCurrentItem().getData().getItemType().equals(Material.BOOK)) {
                    if (coinService.getCoinAmount(p.getUniqueId().toString()) >= 1000) {
                        ticketService.addTickets(p.getUniqueId().toString(), 1);
                        coinService.removeCoins(p.getUniqueId().toString(), 1000);
                        p.closeInventory();
                        TicketService finalTicketService = ticketService;
                        openSelectInv(p);
                    } else {
                        p.sendMessage(Main.prefix + " §7Du hast nicht genügent Money");
                    }
                } else if (e.getCurrentItem().getData().getItemType().equals(Material.ENDER_CHEST)) {
                    Inventory inv = Bukkit.createInventory(null, 9 * 1, "§7Raritäten");

                    inv.setItem(0, new ItemBuilderAPI(Material.CHEST).setDisplayName("§eNormal").setLore("", "§8» §7Maximal §6500 §7Coins", "").build());
                    inv.setItem(2, new ItemBuilderAPI(Material.IRON_INGOT).setDisplayName("§6§lEpic").setLore("", "§8» §7Maximal §67.500 §7Coins", "").build());
                    inv.setItem(4, new ItemBuilderAPI(Material.EMERALD).setDisplayName("§d§lLegendär").setLore("", "§8» §7Maximal §615.000 §7Coins", "").build());
                    inv.setItem(6, new ItemBuilderAPI(Material.NETHER_STAR).setDisplayName("§5§lJackpot").setLore("", "§8» §7Maximal §61.000.000 §7Coins", "").build());
                    inv.setItem(8, new ItemBuilderAPI(Material.BARRIER).setDisplayName("§7» §8Zurück").build());

                    p.openInventory(inv);
                }
            } else if (e.getView().getTitle().equalsIgnoreCase("§7Raritäten")) {
                e.setCancelled(true);
                if (e.getCurrentItem().getData().getItemType().equals(Material.BARRIER)) {
                    p.closeInventory();
                    openSelectInv(p);
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

            HashMap<CoinService, UUID> coinServiceMap = new HashMap<>();
            service = MainService.getService(service);

            //Initializing CoinServices
            CoinService coinService = service.getCoinService();
            if (coinService == null) {
                coinService = new CoinService(database, service);
            }
            coinServiceMap.put(coinService, p.getUniqueId());


            //Initializing TicketServices
            service = MainService.getService(service);
            HashMap<TicketService, UUID> ticketServiceMap = new HashMap<>();
            TicketService ticketService = service.getTicketService();
            if (ticketService == null) {
                ticketService = new TicketService(database, service);
            }
            ticketServiceMap.put(ticketService, p.getUniqueId());

            if (e.getView().getTitle().equalsIgnoreCase("§3Wähle 5 Lose")) {
                e.setCancelled(true);
                if (e.getCurrentItem().getType().equals(Material.ENDER_CHEST) && e.getCurrentItem().getItemMeta().getDisplayName().equals("§6Ticket")) {
                    if (opendticket.get(p) != 5) {
                        Integer endopen = opendticket.get(p);
                        endopen++;
                        opendticket.put(p, endopen);

                        Random rm = new Random();
                        float chance = rm.nextFloat();

                        float normal = 0.85F;
                        float epic = 0.10F;
                        float legendary = 0.05F;

                        Integer gewinn;

                        if ((chance >= normal) || chance >= epic) {
                            Bukkit.getConsoleSender().sendMessage("NORMAL");
                            Random r = new Random();
                            Integer gewinnchance = r.nextInt(200 - 30 + 1) + 0;
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
                            p.playSound(p.getLocation(), Sound.LEVEL_UP, 1.0f, 1.5f);

                            endgewinn.put(p, endgewinn1.get(p) + endgewinn2.get(p) + endgewinn3.get(p) + endgewinn4.get(p) + endgewinn5.get(p));
                        } else if ((chance <= epic) || chance >= legendary) {
                            Random r = new Random();
                            Integer gewinnchance = r.nextInt(2500 - 200 + 1) + 500;
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
                            p.playSound(p.getLocation(), Sound.NOTE_PLING, 1.0f, 1.5f);

                            endgewinn.put(p, endgewinn1.get(p) + endgewinn2.get(p) + endgewinn3.get(p) + endgewinn4.get(p) + endgewinn5.get(p));
                        } else if ((chance <= legendary) || chance >= 0.05F) {
                            Random r = new Random();
                            Integer gewinnchance = r.nextInt(15000 - 2500 + 1) + 7500;
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
                            p.playSound(p.getLocation(), Sound.EXPLODE, 1.0f, 1.5f);

                            endgewinn.put(p, endgewinn1.get(p) + endgewinn2.get(p) + endgewinn3.get(p) + endgewinn4.get(p) + endgewinn5.get(p));
                        } else if (chance <= 0.05) {
                            Bukkit.getConsoleSender().sendMessage(Main.prefix + "JACKPOT!!!");
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
            HashMap<CoinService, UUID> coinServiceMap = new HashMap<>();
            service = MainService.getService(service);

            //Initializing CoinServices
            CoinService coinService = service.getCoinService();
            if (coinService == null) {
                coinService = new CoinService(database, service);
            }
            coinServiceMap.put(coinService, p.getUniqueId());


            //Initializing TicketServices
            service = MainService.getService(service);
            HashMap<TicketService, UUID> ticketServiceMap = new HashMap<>();
            TicketService ticketService = service.getTicketService();
            if (ticketService == null) {
                ticketService = new TicketService(database, service);
            }
            ticketServiceMap.put(ticketService, p.getUniqueId());

            if (e.getInventory().getTitle().equalsIgnoreCase("§3Wähle 5 Lose") && opendticket.get(p) != 5) {
                Inventory inv = Bukkit.createInventory(null, 9 * 6, "§3Wähle 5 Lose");

                for (int i = 0; i < 54; i++) {
                    inv.setItem(i, new ItemBuilderAPI(Material.ENDER_CHEST).setDisplayName("§6Ticket").build());
                }
            } else if (e.getInventory().getTitle().equalsIgnoreCase("§3Wähle 5 Lose")) {
                p.sendMessage(Main.prefix + " §7Du hast §e" + endgewinn.get(p) + " Coins §7gewonnen!");
                endgewinn.remove(p);
                endgewinn1.remove(p);
                endgewinn2.remove(p);
                endgewinn3.remove(p);
                endgewinn4.remove(p);
                endgewinn5.remove(p);

                Bukkit.getScheduler().runTaskLater(instance, () -> {
                    openSelectInv(p);
                }, 5);

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

    private static Integer getCoins(Player player) {
        return service.getCoinService().getCoinAmount(player.getUniqueId().toString());
    }

    private static Integer getTickets(Player player) {
        return service.getTicketService().getTicketAmount(player.getUniqueId().toString());
    }

    private static void openSelectInv(Player player) {
        Inventory inv = Bukkit.createInventory(null, InventoryType.HOPPER, "§6Lotterie");

        inv.setItem(0, new ItemBuilderAPI(Material.PAPER).setDisplayName("§2Einlösen").setLore("", "§8Anzahl: §6" + getTickets(player), "§c§oZum öffnen klicken!").build());
        inv.setItem(2, new ItemBuilderAPI(Material.BOOK).setDisplayName("§cKaufen").setLore("", "§8Kosten: §61000", "§c§oZum kaufen klicken!").build());
        inv.setItem(4, new ItemBuilderAPI(Material.ENDER_CHEST).setDisplayName("§7Raritäten").setLore("", "§8• §eNormal §8| §285%", "§8• §6§lEpic §8| §210%", "§8• §d§lLegendär §8| §25%","§8• §5§lJackpot §8| §2Kleiner als 5%", "").build());

        player.openInventory(inv);
    }
}
