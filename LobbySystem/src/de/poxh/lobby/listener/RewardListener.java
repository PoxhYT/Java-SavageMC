package de.poxh.lobby.listener;

import de.poxh.lobby.api.ItemBuilderAPI;
import de.poxh.lobby.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.Inventory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class RewardListener implements Listener {

    @EventHandler
    public void PlayerRightClick(PlayerInteractEntityEvent event) throws ParseException {
        Player player = event.getPlayer();
        Entity entity = event.getRightClicked();
        if((entity.getCustomName().equalsIgnoreCase("§eDAILY-REWARD"))) {
            event.setCancelled(true);
            openRewardInv(player);
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) throws ParseException {

        Player player = (Player) event.getWhoClicked();

        Date date = new Date();
        Date ticketDate =new SimpleDateFormat("dd/MM/yyyy H:m").parse(Main.service.getRewardService().getPlayerTicketDate(player.getUniqueId().toString()));
        Date coinsDate =new SimpleDateFormat("dd/MM/yyyy H:m").parse(Main.service.getRewardService().getPlayerCoinsDate(player.getUniqueId().toString()));

        if (event.getInventory().getTitle().equalsIgnoreCase("§eTägliche-Belohnungen")) {
            event.setCancelled(true);
            if (event.getCurrentItem().getType() == Material.PAPER) {
                if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7Spieler§7-§eBelohnung")) {
                    openPlayerRewards(player);
                }
            }
        } else if(event.getInventory().getTitle().equalsIgnoreCase("§7Spieler-§eBelohnung")) {
            event.setCancelled(true);
            if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§71 §eLotterieticket §a✔")) {
                Main.service.getRewardService().setPlayerTicket(player.getUniqueId().toString(), true);
                Main.service.getRewardService().setPlayerTicketDate(player.getUniqueId().toString(), setDate(1));
                Main.service.getTicketService().addTickets(player.getUniqueId().toString(), 1);
                player.sendMessage(Main.prefix + "§7Du hast dein §eLotterieticket §7für heute abgeholt.");
                player.closeInventory();
            } else if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§71 §eLotterieticket §c✘")) {
               player.sendMessage(Main.prefix + "Du kannst dein §eLotterieticket §7in §e" + getDifferenceInHours(ticketDate, date, player) + " §7wieder abholen!");
               player.closeInventory();
            }

            if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§71000 §eCoins §a✔")) {
                Main.service.getRewardService().setPlayerCoins(player.getUniqueId().toString(), true);
                Main.service.getRewardService().setPlayerCoinsDate(player.getUniqueId().toString(), setDate(1));
                Main.service.getCoinService().addCoins(player.getUniqueId().toString(), 1000);
                player.sendMessage(Main.prefix + "§7Du hast deine §eCoins §7für heute abgeholt.");
                player.closeInventory();
            } else if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§71000 §eCoins §c✘")) {
                player.sendMessage(Main.prefix + "Du kannst deine §eCoins §7in §e" + getDifferenceInHours(date, coinsDate, player) + " §7wieder abholen!");
                player.closeInventory();
            }
        }

    }

    private static void openRewardInv(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 27, "§eTägliche-Belohnungen");
        for (int i = 0; i < inventory.getSize(); i++) {
            inventory.setItem(i, new ItemBuilderAPI(Material.STAINED_GLASS_PANE, (short)7).setDisplayName("§1 ").build());
        }
        inventory.setItem(11, new ItemBuilderAPI(Material.PAPER).setDisplayName("§7Spieler§7-§eBelohnung").setLore("§7➥ §eLinksklick zum öffnen").build());
        inventory.setItem(13, new ItemBuilderAPI(Material.PAPER).setDisplayName("§6Premium§7-§eBelohnung").setLore("§7➥ §eLinksklick zum öffnen").build());
        inventory.setItem(15, new ItemBuilderAPI(Material.PAPER).setDisplayName("§5Savage§7-§eBelohnung").setLore("§7➥ §eLinksklick zum öffnen").build());
        player.openInventory(inventory);
    }

    private static void openPlayerRewards(Player player) throws ParseException {
        Date date = new Date();
        Date ticketDate =new SimpleDateFormat("dd/MM/yyyy H:m").parse(Main.service.getRewardService().getPlayerTicketDate(player.getUniqueId().toString()));
        Date coinsDate =new SimpleDateFormat("dd/MM/yyyy H:m").parse(Main.service.getRewardService().getPlayerCoinsDate(player.getUniqueId().toString()));

        Inventory inventory = Bukkit.createInventory(null, 27, "§7Spieler-§eBelohnung");
        for (int i = 0; i < inventory.getSize(); i++) {
            inventory.setItem(i, new ItemBuilderAPI(Material.STAINED_GLASS_PANE, (short)7).setDisplayName("§1 ").build());
        }
        if(Main.service.getRewardService().getPlayerInfoByName(player.getName()).isPlayerTicketReceived() == false) {
            inventory.setItem(12, new ItemBuilderAPI(Material.PAPER).setDisplayName("§71 §eLotterieticket §a✔").setLore("§7➥ Abholbar in: §eJETZT").build());
        } else {
            inventory.setItem(12, new ItemBuilderAPI(Material.PAPER).setDisplayName("§71 §eLotterieticket §c✘").setLore("§7➥ Abholbar in: §e" +
                    getDifferenceInHours(date, ticketDate,  player)).build());
        }
        if(Main.service.getRewardService().getPlayerInfoByName(player.getName()).isPlayerCoinsReceived() == false) {
            inventory.setItem(14, new ItemBuilderAPI(Material.PAPER).setDisplayName("§71000 §eCoins §a✔").setLore("§7➥ Abholbar in: §eJETZT").build());
        } else {
            inventory.setItem(14, new ItemBuilderAPI(Material.PAPER).setDisplayName("§71000 §eCoins §c✘").setLore("§7➥ Abholbar in: §e" +
                    getDifferenceInHours(date, coinsDate, player)).build());
        }
        player.openInventory(inventory);
    }


    private static String setDate(int amount) {
        Date date = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DAY_OF_WEEK, amount);
        String pattern = "dd/MM/yyyy H:m";
        DateFormat df = new SimpleDateFormat(pattern);
        String dateAsString = df.format(c.getTime());
        return dateAsString;
    }

    public static String getDifferenceInHours(Date startDate, Date endDate, Player player){

        //milliseconds
        long different = endDate.getTime() - startDate.getTime();

        System.out.println("startDate : " + startDate);
        System.out.println("endDate : "+ endDate);
        System.out.println("different : " + different);

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        //long elapsedDays = different / daysInMilli;
        //different = different % daysInMilli;

        long elapsedHours = different / hoursInMilli;
        different = different % hoursInMilli;

        long elapsedMinutes = different / minutesInMilli;
        different = different % minutesInMilli;

        long elapsedSeconds = different / secondsInMilli;

        if(different < 0) {
            Main.service.getRewardService().setPlayerCoins(player.getUniqueId().toString(), false);
            Main.service.getRewardService().setPlayerTicket(player.getUniqueId().toString(), false);
            Main.service.getRewardService().setPremiumCoins(player.getUniqueId().toString(), false);
            Main.service.getRewardService().setPremiumTicket(player.getUniqueId().toString(), false);
            Main.service.getRewardService().setSavageCoins(player.getUniqueId().toString(), false);
            Main.service.getRewardService().setSavageTicket(player.getUniqueId().toString(), false);
        }

        return elapsedHours + " §eStunden und " + elapsedMinutes + " Minuten";
    }
}
