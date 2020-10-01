package de.lobby.listener;

import de.lobby.main.Main;
import de.lobby.utils.ItemAPI;
import de.lobby.utils.TitleManager;
import de.magnus.coinsapi.util.CoinsAPI;
import de.ticketapi.util.TicketAPI;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.Random;

public class ChestLotteryListener implements Listener {

    public HashMap<Player, Integer> openTicket = new HashMap<>();
    public HashMap<Player, Integer> endWin1 = new HashMap<>();
    public HashMap<Player, Integer> endWin2 = new HashMap<>();
    public HashMap<Player, Integer> endWin3 = new HashMap<>();
    public HashMap<Player, Integer> endWin4 = new HashMap<>();
    public HashMap<Player, Integer> endWin5 = new HashMap<>();
    public HashMap<Player, Integer> endWin = new HashMap<>();

    @EventHandler
    public void onInt(PlayerInteractEntityEvent event){
        Player player = event.getPlayer();
        if (event.getRightClicked().getType() == EntityType.VILLAGER) {
            Villager v = (Villager)event.getRightClicked();
            if (v.getCustomName().equalsIgnoreCase("§8• §r§eTägliche Belohnung §8•")) {
                Inventory inv = Bukkit.createInventory(null, InventoryType.HOPPER, "§6Lotterie");

                for (int i = 0; i < inv.getSize(); i++)
                    inv.setItem(i, new ItemAPI(Material.STAINED_GLASS_PANE, (short)15).setName("§r").build());

                inv.setItem(2, new ItemAPI(Material.PAPER).setName("§8• §r§eTickets §8•").setLore("§r§aAnzahl §8(§r§eTickets§8)§7: §c" + TicketAPI.getTickets(player.getUniqueId().toString()), "§r§7Klicke, zum öffnen!").build());
                inv.setItem(0, new ItemAPI(Material.CHEST).setName("§8• §r§eTickets §r§7kaufen §8•").setLore("§r§7Preis §8(§r§eTickets§8)§7: §c1000", "§r§7Klicke, zum kaufen!").build());
                inv.setItem(4, new ItemAPI(Material.IRON_INGOT).setName("§8• §r§7Aktuelle §r§eCoins §8•").setLore("§r§aAnzahl §8(§r§eCoins§8)§7: §c" + CoinsAPI.getCoins(player.getUniqueId().toString())).build());

                player.openInventory(inv);
                player.playSound(player.getLocation(), Sound.LEVEL_UP, 1,1);
            }
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent e){
        final Player p = (Player) e.getWhoClicked();
        String uuid = String.valueOf(p.getUniqueId());
        if(e.getView().getTitle().equalsIgnoreCase("§6Lotterie")){
            e.setCancelled(true);
            if(e.getCurrentItem().getData().getItemType().equals(Material.PAPER)){
                if(openTicket.get(p) == null || openTicket.get(p) == 5){
                    if (TicketAPI.getTickets(p.getUniqueId().toString()).intValue() >= 1) {
                        Inventory inv = Bukkit.createInventory(null, 9*6, "§3Wähle 5 Lose");

                        for(int i = 0; i < 54; i++){
                            inv.setItem(i, new ItemAPI(Material.ENDER_CHEST).setName("§6Ticket").build());
                        }

                        TicketAPI.removeTickets(p.getUniqueId().toString(), 1);
                        p.openInventory(inv);
                        openTicket.put(p, 0);
                    }else{
                        p.sendMessage(Main.prefix2 + " §7Du hast nicht §r§cgenügend §r§eTickets§7.");
                    }
                }else{
                    p.sendMessage(Main.prefix2 + "§7Du öffnest zur Zeit ein Lotterieticket");
                }
            }else if(e.getCurrentItem().getData().getItemType().equals(Material.CHEST)){
                if (CoinsAPI.getCoins(p.getUniqueId().toString()).intValue() >= 1000) {
                    TicketAPI.addTickets(p.getUniqueId().toString(), 1);
                    CoinsAPI.removeCoins(p.getUniqueId().toString(), 1000);
                    p.sendMessage(Main.prefix2 + "Du hast §r§c1 §r§eTicket §r§7gekauft.");
                    p.closeInventory();
                    Bukkit.getScheduler().runTaskLater(Main.getInstance(), new Runnable() {
                        @Override
                        public void run() {
                            Inventory inv = Bukkit.createInventory(null, InventoryType.HOPPER, "§6Lotterie");

                            inv.setItem(2, new ItemAPI(Material.PAPER).setName("§8• §r§eTickets §8•").setLore("§r§aAnzahl §8(§r§eTickets§8)§7: §c" + TicketAPI.getTickets(p.getUniqueId().toString()), "§r§7Klicke, zum öffnen!").build());
                            inv.setItem(0, new ItemAPI(Material.CHEST).setName("§8• §r§eTickets §r§7kaufen §8•").setLore("§r§7Preis §8(§r§eTickets§8)§7: §c1000", "§r§7Klicke, zum kaufen!").build());
                            inv.setItem(4, new ItemAPI(Material.IRON_INGOT).setName("§8• §r§7Aktuelle §r§eCoins §8•").setLore("§r§aAnzahl §8(§r§eCoins§8)§7: §c" + CoinsAPI.getCoins(p.getUniqueId().toString())).build());

                            p.openInventory(inv);
                            p.playSound(p.getLocation(), Sound.LEVEL_UP, 1,1);
                        }
                    }, 5);
                }else{
                    p.sendMessage(Main.prefix2 + " §7Du hast nicht §r§cgenügend §r§eCoins§7.");
                }
            }else if(e.getCurrentItem().getData().getItemType().equals(Material.ENDER_CHEST)){
                Inventory inv = Bukkit.createInventory(null, 9*1, "§7Raritäten");

                inv.setItem(0, new ItemAPI(Material.CHEST).setName("§eNormal").setLore("", "§8» §7Maximal §6500 §7Money", "").build());
                inv.setItem(2, new ItemAPI(Material.IRON_INGOT).setName("§6§lEpic").setLore("", "§8» §7Maximal §67.500 §7Money", "").build());
                inv.setItem(4, new ItemAPI(Material.EMERALD).setName("§d§lLegendär").setLore("", "§8» §7Maximal §615.000 §7Money", "").build());
                inv.setItem(6, new ItemAPI(Material.NETHER_STAR).setName("§5§lJackpot").setLore("", "§8» §7Maximal §61.000.000 §7Money", "").build());
                inv.setItem(8, new ItemAPI(Material.BARRIER).setName("§7» §8Zurück").build());

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

                        inv.setItem(2, new ItemAPI(Material.PAPER).setName("§8• §r§eTickets §8•").setLore("§r§aAnzahl §8(§r§eTickets§8)§7: §c" + TicketAPI.getTickets(p.getUniqueId().toString()), "§r§7Klicke, zum öffnen!").build());
                        inv.setItem(0, new ItemAPI(Material.CHEST).setName("§8• §r§eTickets §r§7kaufen §8•").setLore("§r§7Preis §8(§r§eTickets§8)§7: §c1000", "§r§7Klicke, zum kaufen!").build());
                        inv.setItem(4, new ItemAPI(Material.IRON_INGOT).setName("§8• §r§7Aktuelle §r§eCoins §8•").setLore("§r§aAnzahl §8(§r§eCoins§8)§7: §c" + CoinsAPI.getCoins(p.getUniqueId().toString())).build());

                        p.openInventory(inv);
                        p.playSound(p.getLocation(), Sound.LEVEL_UP, 1,1);
                    }
                }, 5);
            }
        }
    }

    @EventHandler
    public void onClickTicket(InventoryClickEvent e){
        Player p = (Player) e.getWhoClicked();
        String uuid = String.valueOf(p.getUniqueId());
        if(e.getView().getTitle().equalsIgnoreCase("§3Wähle 5 Lose")){
            e.setCancelled(true);
            if(e.getCurrentItem().getType().equals(Material.ENDER_CHEST) && e.getCurrentItem().getItemMeta().getDisplayName().equals("§6Ticket")){
                if(openTicket.get(p) != 5){
                    Integer endopen = openTicket.get(p);
                    endopen++;
                    openTicket.put(p, endopen);

                    Random rm = new Random();
                    float chance = rm.nextFloat() * 100.0F;

                    float normal = 93.0F;
                    float epic = 97.0F;
                    float legendary = 99.9F;

                    Integer gewinn = 0;

                    if((chance > 0.0F) && chance <= normal) {
                        Random r = new Random();
                        Integer gewinnchance = r.nextInt(500 - 0 + 1) + 0;
                        gewinn = gewinnchance;
                        if(openTicket.get(p) == 1){
                            endWin1.put(p, gewinn);
                        }else if(openTicket.get(p) == 2){
                            endWin2.put(p, gewinn);
                        }else if(openTicket.get(p) == 3){
                            endWin3.put(p, gewinn);
                        }else if(openTicket.get(p) == 4){
                            endWin4.put(p, gewinn);
                        }else if(openTicket.get(p) == 5){
                            endWin5.put(p, gewinn);
                        }

                        ItemStack i = e.getCurrentItem();
                        ItemMeta m = i.getItemMeta();
                        i.setType(Material.GOLD_INGOT);
                        m.setDisplayName("§e" + gewinnchance + " §8[§eNormal§8]");
                        i.setItemMeta(m);
                        CoinsAPI.addCoins(uuid, gewinnchance);
                        p.sendMessage(Main.prefix2 + "Du hast §c" + gewinnchance + " §r§eCoins §r§7gewonnen§7.");
                        p.playSound(p.getLocation(), Sound.LEVEL_UP, 1.0f, 1.5f);

                        endWin.put(p, endWin1.get(p) + endWin2.get(p) + endWin3.get(p) + endWin4.get(p) + endWin5.get(p));
                    } else if((chance > normal) && chance <= epic) {
                        Random r = new Random();
                        Integer gewinnchance = r.nextInt(3000 - 500 + 1) + 500;
                        gewinn = gewinnchance;
                        if(openTicket.get(p) == 1){
                            endWin1.put(p, gewinn);
                        }else if(openTicket.get(p) == 2){
                            endWin2.put(p, gewinn);
                        }else if(openTicket.get(p) == 3){
                            endWin3.put(p, gewinn);
                        }else if(openTicket.get(p) == 4){
                            endWin4.put(p, gewinn);
                        }else if(openTicket.get(p) == 5){
                            endWin5.put(p, gewinn);
                        }

                        ItemStack i = e.getCurrentItem();
                        ItemMeta m = i.getItemMeta();
                        i.setType(Material.GOLD_INGOT);
                        m.setDisplayName("§6§l" + gewinnchance + " §8[§6§lEpic§8]");
                        m.addEnchant(Enchantment.LUCK, 2, true);
                        i.setItemMeta(m);
                        CoinsAPI.addCoins(uuid, gewinnchance);
                        p.sendMessage(Main.prefix2 + "Du hast §c" + gewinnchance + " §r§eCoins §r§7gewonnen§7.");
                        p.playSound(p.getLocation(), Sound.NOTE_PLING, 1.0f, 1.5f);

                        endWin.put(p, endWin1.get(p) + endWin2.get(p) + endWin3.get(p) + endWin4.get(p) + endWin5.get(p));
                    } else if((chance > epic) && chance <= legendary) {
                        Random r = new Random();
                        Integer gewinnchance = r.nextInt(10000 - 3000 + 1) + 3000;
                        gewinn = gewinnchance;
                        if(openTicket.get(p) == 1){
                            endWin1.put(p, gewinn);
                        }else if(openTicket.get(p) == 2){
                            endWin2.put(p, gewinn);
                        }else if(openTicket.get(p) == 3){
                            endWin3.put(p, gewinn);
                        }else if(openTicket.get(p) == 4){
                            endWin4.put(p, gewinn);
                        }else if(openTicket.get(p) == 5){
                            endWin5.put(p, gewinn);
                        }

                        ItemStack i = e.getCurrentItem();
                        ItemMeta m = i.getItemMeta();
                        i.setType(Material.GOLD_INGOT);
                        m.setDisplayName("§d§l" + gewinnchance + " §8[§d§lLegendär§8]");
                        m.addEnchant(Enchantment.LUCK, 3, true);
                        i.setItemMeta(m);
                        CoinsAPI.addCoins(p.getUniqueId().toString() , gewinnchance);
                        p.sendMessage(Main.prefix2 + "Du hast §c" + gewinnchance + " §r§eCoins §r§7gewonnen§7.");
                        p.playSound(p.getLocation(), Sound.EXPLODE, 1.0f, 1.5f);

                        endWin.put(p, endWin1.get(p) + endWin2.get(p) + endWin3.get(p) + endWin4.get(p) + endWin5.get(p));
                    } else if((chance > legendary) && (chance <= 100.0F)){
                        Random r = new Random();
                        Integer gewinnchance = 1000000;
                        gewinn = gewinnchance;
                        if(openTicket.get(p) == 1){
                            endWin1.put(p, gewinn);
                        }else if(openTicket.get(p) == 2){
                            endWin2.put(p, gewinn);
                        }else if(openTicket.get(p) == 3){
                            endWin3.put(p, gewinn);
                        }else if(openTicket.get(p) == 4){
                            endWin4.put(p, gewinn);
                        }else if(openTicket.get(p) == 5){
                            endWin5.put(p, gewinn);
                        }

                        ItemStack i = e.getCurrentItem();
                        ItemMeta m = i.getItemMeta();
                        i.setType(Material.NETHER_STAR);
                        m.setDisplayName("§5§l" + gewinnchance + " §8[§5§lJackpot§8]");
                        m.addEnchant(Enchantment.LUCK, 4, true);
                        i.setItemMeta(m);
                        CoinsAPI.addCoins(p.getUniqueId().toString() , gewinnchance);
                        for(Player all : Bukkit.getOnlinePlayers()){
                            all.playSound(all.getLocation(), Sound.EXPLODE, 1.0f, 1.5f);
                            all.playSound(all.getLocation(), Sound.ENDERDRAGON_GROWL, 1.0f, 1.5f);
                            all.playSound(all.getLocation(), Sound.AMBIENCE_THUNDER, 1.0f, 1.5f);
                            TitleManager.setBar(all, "§6" + p.getName() + " §7hat den Jackpot von §5§l" + gewinnchance + " §7gezogen");
                            TitleManager.setTitle(all, 20, 50, 20, "§6" + p.getName() + " §7hat den Jackpot gezogen!", "§7Dieser beträgt §5§l" + gewinnchance);
                            createFrieWork(all);
                        }

                        endWin.put(p, endWin1.get(p) + endWin2.get(p) + endWin3.get(p) + endWin4.get(p) + endWin5.get(p));
                    }
                }else{
                    p.closeInventory();
                    openTicket.remove(p);
                }
            }
        }
    }

    @EventHandler
    public void onCloseTicket(final InventoryCloseEvent e){
        final Player p = (Player) e.getPlayer();
        if(e.getInventory().getTitle().equalsIgnoreCase("§3Wähle 5 Lose") && openTicket.get(p) != 5){
            Bukkit.getScheduler().runTaskLater(Main.getInstance(), new Runnable() {
                @Override
                public void run() {
                    Inventory inv = Bukkit.createInventory(null, 9*6, "§3Wähle 5 Lose");

                    for(int i = 0; i < 54; i++){
                        inv.setItem(i, new ItemAPI(Material.ENDER_CHEST).setName("§6Ticket").build());
                    }

                    p.openInventory(inv);
                }
            }, 5);
        }else if(e.getInventory().getTitle().equalsIgnoreCase("§3Wähle 5 Lose")){
            p.sendMessage(Main.prefix2 + "Dein §r§eEndbetrag §r§7beträgt §c" + endWin.get(p) + " §r§eCoins§7.");
            endWin.remove(p);
            endWin1.remove(p);
            endWin2.remove(p);
            endWin3.remove(p);
            endWin4.remove(p);
            endWin5.remove(p);
            Bukkit.getScheduler().runTaskLater(Main.getInstance(), new Runnable() {
                @Override
                public void run() {
                    Inventory inv = Bukkit.createInventory(null, InventoryType.HOPPER, "§6Lotterie");

                    inv.setItem(2, new ItemAPI(Material.PAPER).setName("§8• §r§eTickets §8•").setLore("§r§aAnzahl §8(§r§eTickets§8)§7: §c" + TicketAPI.getTickets(p.getUniqueId().toString()), "§r§7Klicke, zum öffnen!").build());
                    inv.setItem(0, new ItemAPI(Material.CHEST).setName("§8• §r§eTickets §r§7kaufen §8•").setLore("§r§7Preis §8(§r§eTickets§8)§7: §c1000", "§r§7Klicke, zum kaufen!").build());
                    inv.setItem(4, new ItemAPI(Material.IRON_INGOT).setName("§8• §r§7Aktuelle §r§eCoins §8•").setLore("§r§aAnzahl §8(§r§eCoins§8)§7: §c" + CoinsAPI.getCoins(p.getUniqueId().toString())).build());

                    p.openInventory(inv);
                    p.playSound(p.getLocation(), Sound.LEVEL_UP, 1,1);
                }
            }, 5);
        }
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




