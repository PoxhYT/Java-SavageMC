package com.poxh.listener;

import de.xenodev.cbs.main.Main;
import org.bukkit.*;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Random;

public class ChestLottery implements Listener {

    private static File file = new File("plugins//" + Main.getInstance().getName() + "//Lottery", "file.yml");
    private static YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);

    @EventHandler
    public void onInt(PlayerInteractEvent e){
        Player p = e.getPlayer();
        if(e.getClickedBlock().getType() == Material.ENDER_CHEST) {
            e.setCancelled(true);
            if(e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.LEFT_CLICK_BLOCK){
                Inventory inv = Bukkit.createInventory(null, InventoryType.HOPPER, "§6Lotterie");

                inv.setItem(0, new ItemBuilder(Material.PAPER).setDisplayName("§2Einlösen").setLore("", "§8Anzahl: §6" + getLottery(p), "§c§oZum öffnen klicken!").build());
                inv.setItem(2, new ItemBuilder(Material.BOOK).setDisplayName("§cKaufen").setLore("", "§8Kosten: §61000", "§c§oZum kaufen klicken!").build());
                inv.setItem(4, new ItemBuilder(Material.ENDER_CHEST).setDisplayName("§7Raritäten").setLore("", "§8• §eNormal §8| §280%", "§8• §6§lEpic §8| §250%", "§8• §d§lLegendär §8| §220%","§8• §5§lJackpot §8| §21%", "").build());

                p.openInventory(inv);
            }
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent e){
        final Player p = (Player) e.getWhoClicked();
        if(e.getView().getTitle().equalsIgnoreCase("§6Lotterie")){
            e.setCancelled(true);
            if(e.getCurrentItem().getData().getItemType().equals(Material.PAPER)){
                if(opendticket.get(p) == null || opendticket.get(p) == 5){
                    if(getLottery(p) >= 1){
                        Inventory inv = Bukkit.createInventory(null, 9*6, "§3Wähle 5 Lose");

                        for(int i = 0; i < 54; i++){
                            inv.setItem(i, new ItemBuilder(Material.ENDER_CHEST).setDisplayName("§6Ticket").build());
                        }

                        removeLottery(p, 1);
                        p.openInventory(inv);
                        opendticket.put(p, 0);
                    }else{
                        p.sendMessage(Main.prefix + " §7Du hast nicht genügent Lotterietickets");
                    }
                }else{
                    p.sendMessage(Main.prefix + " §7Du öffnest zur Zeit ein Lotterieticket");
                }
            }else if(e.getCurrentItem().getData().getItemType().equals(Material.BOOK)){
                if(MoneyAPI.getMoney(p) >= 1000){
                    addLottery(p, 1);
                    MoneyAPI.removeMoney(p, 1000);
                    p.closeInventory();
                    Bukkit.getScheduler().runTaskLater(Main.getInstance(), new Runnable() {
                        @Override
                        public void run() {
                            Inventory inv = Bukkit.createInventory(null, InventoryType.HOPPER, "§6Lotterie");

                            inv.setItem(0, new ItemBuilder(Material.PAPER).setDisplayName("§2Einlösen").setLore("", "§8Anzahl: §6" + getLottery(p), "§c§oZum öffnen klicken!").build());
                            inv.setItem(2, new ItemBuilder(Material.BOOK).setDisplayName("§cKaufen").setLore("", "§8Kosten: §61000", "§c§oZum kaufen klicken!").build());
                            inv.setItem(4, new ItemBuilder(Material.ENDER_CHEST).setDisplayName("§7Raritäten").setLore("", "§8• §eNormal §8| §280%", "§8• §6§lEpic §8| §250%", "§8• §d§lLegendär §8| §220%","§8• §5§lJackpot §8| §21%", "").build());

                            p.openInventory(inv);
                        }
                    }, 5);
                }else{
                    p.sendMessage(Main.prefix + " §7Du hast nicht genügent Money");
                }
            }else if(e.getCurrentItem().getData().getItemType().equals(Material.ENDER_CHEST)){
                Inventory inv = Bukkit.createInventory(null, 9*1, "§7Raritäten");

                inv.setItem(0, new ItemBuilder(Material.CHEST).setDisplayName("§eNormal").setLore("", "§8» §7Maximal §6500 §7Money", "").build());
                inv.setItem(2, new ItemBuilder(Material.IRON_INGOT).setDisplayName("§6§lEpic").setLore("", "§8» §7Maximal §67.500 §7Money", "").build());
                inv.setItem(4, new ItemBuilder(Material.EMERALD).setDisplayName("§d§lLegendär").setLore("", "§8» §7Maximal §615.000 §7Money", "").build());
                inv.setItem(6, new ItemBuilder(Material.NETHER_STAR).setDisplayName("§5§lJackpot").setLore("", "§8» §7Maximal §61.000.000 §7Money", "").build());
                inv.setItem(8, new ItemBuilder(Material.BARRIER).setDisplayName("§7» §8Zurück").build());

                p.openInventory(inv);
            }
        }else if(e.getView().getTitle().equalsIgnoreCase("§7Raritäten")){
            e.setCancelled(true);
            if(e.getCurrentItem().getData().getItemType().equals(Material.BARRIER)){
                p.closeInventory();
                Bukkit.getScheduler().runTaskLater(Main.getInstance(), new Runnable() {
                    @Override
                    public void run() {
                        Inventory inv = Bukkit.createInventory(null, InventoryType.HOPPER, "§6Lotterie");

                        inv.setItem(0, new ItemBuilder(Material.PAPER).setDisplayName("§2Einlösen").setLore("", "§8Anzahl: §6" + getLottery(p), "§c§oZum öffnen klicken!").build());
                        inv.setItem(2, new ItemBuilder(Material.BOOK).setDisplayName("§cKaufen").setLore("", "§8Kosten: §61000", "§c§oZum kaufen klicken!").build());
                        inv.setItem(4, new ItemBuilder(Material.ENDER_CHEST).setDisplayName("§7Raritäten").setLore("", "§8• §eNormal §8| §280%", "§8• §6§lEpic §8| §250%", "§8• §d§lLegendär §8| §220%","§8• §5§lJackpot §8| §21%", "").build());

                        p.openInventory(inv);
                    }
                }, 5);
            }
        }
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
        Player p = (Player) e.getWhoClicked();
        if(e.getView().getTitle().equalsIgnoreCase("§3Wähle 5 Lose")){
            e.setCancelled(true);
            if(e.getCurrentItem().getType().equals(Material.ENDER_CHEST) && e.getCurrentItem().getItemMeta().getDisplayName().equals("§6Ticket")){
                if(opendticket.get(p) != 5){
                    Integer endopen = opendticket.get(p);
                    endopen++;
                    opendticket.put(p, endopen);

                    Random rm = new Random();
                    float chance = rm.nextFloat() * 100.0F;

                    float normal = 82.0F;
                    float epic = 87.0F;
                    float legendary = 99.9F;

                    Integer gewinn = 0;

                    if((chance > 0.0F) && chance <= normal) {
                        Random r = new Random();
                        Integer gewinnchance = r.nextInt(500 - 0 + 1) + 0;
                        gewinn = gewinnchance;
                        if(opendticket.get(p) == 1){
                            endgewinn1.put(p, gewinn);
                        }else if(opendticket.get(p) == 2){
                            endgewinn2.put(p, gewinn);
                        }else if(opendticket.get(p) == 3){
                            endgewinn3.put(p, gewinn);
                        }else if(opendticket.get(p) == 4){
                            endgewinn4.put(p, gewinn);
                        }else if(opendticket.get(p) == 5){
                            endgewinn5.put(p, gewinn);
                        }

                        ItemStack i = e.getCurrentItem();
                        ItemMeta m = i.getItemMeta();
                        i.setType(Material.CHEST);
                        m.setDisplayName("§e" + gewinnchance + " §8[§eNormal§8]");
                        m.addEnchant(Enchantment.LUCK, 1, true);
                        i.setItemMeta(m);
                        MoneyAPI.addMoney(p, gewinnchance);
                        p.sendMessage(Main.prefix + " §7Du hast §e" + gewinnchance + " §7Money gewonnen");
                        p.playSound(p.getLocation(), Sound.LEVEL_UP, 1.0f, 1.5f);

                        endgewinn.put(p, endgewinn1.get(p) + endgewinn2.get(p) + endgewinn3.get(p) + endgewinn4.get(p) + endgewinn5.get(p));
                    } else if((chance > normal) && chance <= epic) {
                        Random r = new Random();
                        Integer gewinnchance = r.nextInt(7500 - 500 + 1) + 500;
                        gewinn = gewinnchance;
                        if(opendticket.get(p) == 1){
                            endgewinn1.put(p, gewinn);
                        }else if(opendticket.get(p) == 2){
                            endgewinn2.put(p, gewinn);
                        }else if(opendticket.get(p) == 3){
                            endgewinn3.put(p, gewinn);
                        }else if(opendticket.get(p) == 4){
                            endgewinn4.put(p, gewinn);
                        }else if(opendticket.get(p) == 5){
                            endgewinn5.put(p, gewinn);
                        }

                        ItemStack i = e.getCurrentItem();
                        ItemMeta m = i.getItemMeta();
                        i.setType(Material.IRON_INGOT);
                        m.setDisplayName("§6§l" + gewinnchance + " §8[§6§lEpic§8]");
                        m.addEnchant(Enchantment.LUCK, 2, true);
                        i.setItemMeta(m);
                        MoneyAPI.addMoney(p, gewinnchance);
                        p.sendMessage(Main.prefix + " §7Du hast §6§l" + gewinnchance + " §7Money gewonnen");
                        p.playSound(p.getLocation(), Sound.NOTE_PLING, 1.0f, 1.5f);

                        endgewinn.put(p, endgewinn1.get(p) + endgewinn2.get(p) + endgewinn3.get(p) + endgewinn4.get(p) + endgewinn5.get(p));
                    } else if((chance > epic) && chance <= legendary) {
                        Random r = new Random();
                        Integer gewinnchance = r.nextInt(15000 - 7500 + 1) + 7500;
                        gewinn = gewinnchance;
                        if(opendticket.get(p) == 1){
                            endgewinn1.put(p, gewinn);
                        }else if(opendticket.get(p) == 2){
                            endgewinn2.put(p, gewinn);
                        }else if(opendticket.get(p) == 3){
                            endgewinn3.put(p, gewinn);
                        }else if(opendticket.get(p) == 4){
                            endgewinn4.put(p, gewinn);
                        }else if(opendticket.get(p) == 5){
                            endgewinn5.put(p, gewinn);
                        }

                        ItemStack i = e.getCurrentItem();
                        ItemMeta m = i.getItemMeta();
                        i.setType(Material.EMERALD);
                        m.setDisplayName("§d§l" + gewinnchance + " §8[§d§lLegendär§8]");
                        m.addEnchant(Enchantment.LUCK, 3, true);
                        i.setItemMeta(m);
                        MoneyAPI.addMoney(p, gewinnchance);
                        p.sendMessage(Main.prefix + " §7Du hast §d§l" + gewinnchance + " §7Money gewonnen");
                        p.playSound(p.getLocation(), Sound.EXPLODE, 1.0f, 1.5f);

                        endgewinn.put(p, endgewinn1.get(p) + endgewinn2.get(p) + endgewinn3.get(p) + endgewinn4.get(p) + endgewinn5.get(p));
                    } else if((chance > legendary) && (chance <= 100.0F)){
                        Random r = new Random();
                        Integer gewinnchance = 1000000;
                        gewinn = gewinnchance;
                        if(opendticket.get(p) == 1){
                            endgewinn1.put(p, gewinn);
                        }else if(opendticket.get(p) == 2){
                            endgewinn2.put(p, gewinn);
                        }else if(opendticket.get(p) == 3){
                            endgewinn3.put(p, gewinn);
                        }else if(opendticket.get(p) == 4){
                            endgewinn4.put(p, gewinn);
                        }else if(opendticket.get(p) == 5){
                            endgewinn5.put(p, gewinn);
                        }

                        ItemStack i = e.getCurrentItem();
                        ItemMeta m = i.getItemMeta();
                        i.setType(Material.NETHER_STAR);
                        m.setDisplayName("§5§l" + gewinnchance + " §8[§5§lJackpot§8]");
                        m.addEnchant(Enchantment.LUCK, 4, true);
                        i.setItemMeta(m);
                        MoneyAPI.addMoney(p, gewinnchance);
                        for(Player all : Bukkit.getOnlinePlayers()){
                            all.playSound(all.getLocation(), Sound.EXPLODE, 1.0f, 1.5f);
                            all.playSound(all.getLocation(), Sound.ENDERDRAGON_GROWL, 1.0f, 1.5f);
                            all.playSound(all.getLocation(), Sound.AMBIENCE_THUNDER, 1.0f, 1.5f);
                            TitleManager.setBar(all, "§6" + p.getName() + " §7hat den Jackpot von §5§l" + gewinnchance + " §7gezogen");
                            TitleManager.setTitle(all, 20, 50, 20, "§6" + p.getName() + " §7hat den Jackpot gezogen!", "§7Dieser beträgt §5§l" + gewinnchance);
                            createFrieWork(all);
                        }

                        endgewinn.put(p, endgewinn1.get(p) + endgewinn2.get(p) + endgewinn3.get(p) + endgewinn4.get(p) + endgewinn5.get(p));
                    }
                }else{
                    p.closeInventory();
                    opendticket.remove(p);
                }
            }
        }
    }

    @EventHandler
    public void onCloseTicket(final InventoryCloseEvent e){
        final Player p = (Player) e.getPlayer();
        if(e.getInventory().getTitle().equalsIgnoreCase("§3Wähle 5 Lose") && opendticket.get(p) != 5){
            Bukkit.getScheduler().runTaskLater(Main.getInstance(), new Runnable() {
                @Override
                public void run() {
                    Inventory inv = Bukkit.createInventory(null, 9*6, "§3Wähle 5 Lose");

                    for(int i = 0; i < 54; i++){
                        inv.setItem(i, new ItemBuilder(Material.ENDER_CHEST).setDisplayName("§6Ticket").build());
                    }

                    p.openInventory(inv);
                }
            }, 5);
        }else if(e.getInventory().getTitle().equalsIgnoreCase("§3Wähle 5 Lose")){
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
                    Inventory inv = Bukkit.createInventory(null, InventoryType.HOPPER, "§6Lotterie");

                    inv.setItem(0, new ItemBuilder(Material.PAPER).setDisplayName("§2Einlösen").setLore("", "§8Anzahl: §6" + getLottery(p), "§c§oZum öffnen klicken!").build());
                    inv.setItem(2, new ItemBuilder(Material.BOOK).setDisplayName("§cKaufen").setLore("", "§8Kosten: §61000", "§c§oZum kaufen klicken!").build());
                    inv.setItem(4, new ItemBuilder(Material.ENDER_CHEST).setDisplayName("§7Raritäten").setLore("", "§8• §eNormal §8| §280%", "§8• §6§lEpic §8| §250%", "§8• §d§lLegendär §8| §220%","§8• §5§lJackpot §8| §21%", "").build());

                    p.openInventory(inv);
                }
            }, 5);
        }
    }

    public static void createPlayer(Player p){
        if(hasLottery(p) == false){
            setLottery(p, 999);
        }
    }

    public static boolean hasLottery(Player p){
        if(cfg.get(p.getUniqueId().toString() + ".Lottery") != null){
            return true;
        }
        return false;
    }

    public static Integer getLottery(Player p){
        return cfg.getInt(p.getUniqueId().toString() + ".Lottery");
    }

    public static void setLottery(Player p, Integer amount){
        cfg.set(p.getUniqueId().toString() + ".Lottery", amount);
        save();
    }

    public static void removeLottery(Player p, Integer amount){
        cfg.set(p.getUniqueId().toString() + ".Lottery", getLottery(p) - amount);
        save();
    }

    public static void addLottery(Player p, Integer amount){
        cfg.set(p.getUniqueId().toString() + ".Lottery", getLottery(p) + amount);
        save();
    }

    private static void save(){
        try {
            cfg.save(file);
        } catch (IOException e) {}
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