package main.java.de.xenodev.cbs.command;

import main.java.de.xenodev.cbs.main.Main;
import main.java.de.xenodev.cbs.utils.ItemBuilder;
import main.java.de.xenodev.cbs.utils.MoneyAPI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

public class TradeCMD implements CommandExecutor, Listener {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player p = (Player)sender;

        if(args.length == 1){
            if(args[0].equalsIgnoreCase("buy")){
                if(MoneyAPI.hasBank(p) == false){
                    if(MoneyAPI.getMoney(p) >= 1000){
                        MoneyAPI.removeMoney(p, 1000);
                        MoneyAPI.setBank(p, 0);
                        p.sendMessage(Main.prefix + " §7Du hast dir ein Bankkonto gekauft");
                    }else{
                        p.sendMessage(Main.prefix + " §7Du hast nicht genügend Geld");
                    }
                }else{
                    p.sendMessage(Main.prefix + " §7Du hast bereits ein Bankkonto");
                }
            }else{
                p.sendMessage(Main.prefix + " §7Kaufe dir ein Bankkonto mit /trade buy");
            }
        }else{
            if(MoneyAPI.hasBank(p) == true){
                Inventory inv = Bukkit.createInventory(null, 9*6, "§2§lBank");

                for(int i = 0; i < 54; i++){
                    inv.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 15).build());
                }

                inv.setItem(10, new ItemBuilder(Material.SKULL_ITEM, 1, (short) 3).setDisplayName("§6Auszahlen").setValueOwner("http://textures.minecraft.net/texture/bd8a99db2c37ec71d7199cd52639981a7513ce9cca9626a3936f965b131193").build());
                inv.setItem(13, new ItemBuilder(Material.SKULL_ITEM, 1, (short) 3).setDisplayName("§2Vermögen").setOwner(p.getName()).setLore("", "§8• §7Money: §6" + MoneyAPI.getMoney(p), "", "§8• §7Bank: §6" + MoneyAPI.getBank(p), "").build());
                inv.setItem(16, new ItemBuilder(Material.SKULL_ITEM, 1, (short) 3).setDisplayName("§6Einzahlen").setValueOwner("http://textures.minecraft.net/texture/3edd20be93520949e6ce789dc4f43efaeb28c717ee6bfcbbe02780142f716").build());
                inv.setItem(40, new ItemBuilder(Material.SKULL_ITEM, 1, (short) 3).setDisplayName("§6Reload").setValueOwner("http://textures.minecraft.net/texture/11d720cd39df3be74b0cac75e3937f0085a37824743cad6330dc9f4666a4510d").build());

                inv.setItem(28, new ItemBuilder(Material.SKULL_ITEM, 1, (short) 3).setDisplayName("§9-100").setValueOwner("http://textures.minecraft.net/texture/71bc2bcfb2bd3759e6b1e86fc7a79585e1127dd357fc202893f9de241bc9e530").build());
                inv.setItem(37, new ItemBuilder(Material.SKULL_ITEM, 1, (short) 3).setDisplayName("§9-1000").setValueOwner("http://textures.minecraft.net/texture/31a9463fd3c433d5e1d9fec6d5d4b09a83a970b0b74dd546ce67a73348caab").build());
                inv.setItem(46, new ItemBuilder(Material.SKULL_ITEM, 1, (short) 3).setDisplayName("§9-10000").setValueOwner("http://textures.minecraft.net/texture/d55fc2c1bae8e08d3e426c17c455d2ff9342286dffa3c7c23f4bd365e0c3fe").build());
                inv.setItem(38, new ItemBuilder(Material.SKULL_ITEM, 1, (short) 3).setDisplayName("§9-Alles").setValueOwner("http://textures.minecraft.net/texture/f417618ca1f5031d1ab97ba3253d088f4da7a773bbb56f1dec9b999d71ec").build());

                inv.setItem(34, new ItemBuilder(Material.SKULL_ITEM, 1, (short) 3).setDisplayName("§9+100").setValueOwner("http://textures.minecraft.net/texture/71bc2bcfb2bd3759e6b1e86fc7a79585e1127dd357fc202893f9de241bc9e530").build());
                inv.setItem(43, new ItemBuilder(Material.SKULL_ITEM, 1, (short) 3).setDisplayName("§9+1000").setValueOwner("http://textures.minecraft.net/texture/31a9463fd3c433d5e1d9fec6d5d4b09a83a970b0b74dd546ce67a73348caab").build());
                inv.setItem(52, new ItemBuilder(Material.SKULL_ITEM, 1, (short) 3).setDisplayName("§9+10000").setValueOwner("http://textures.minecraft.net/texture/d55fc2c1bae8e08d3e426c17c455d2ff9342286dffa3c7c23f4bd365e0c3fe").build());
                inv.setItem(42, new ItemBuilder(Material.SKULL_ITEM, 1, (short) 3).setDisplayName("§9+Alles").setValueOwner("http://textures.minecraft.net/texture/f417618ca1f5031d1ab97ba3253d088f4da7a773bbb56f1dec9b999d71ec").build());

                p.openInventory(inv);
            }else{
                p.sendMessage(Main.prefix + " §7Kaufe dir bitte zuerst ein Bankkonto mit /trade buy");
            }
        }

        return false;
    }


    @EventHandler
    public void onClick(InventoryClickEvent e) {
        final Player p = (Player) e.getWhoClicked();
        if (e.getView().getTitle().equalsIgnoreCase("§2§lBank")) {
            e.setCancelled(true);
            if(e.getCurrentItem().getData().getItemType().equals(Material.SKULL_ITEM)){
                if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§6Reload")){
                    p.closeInventory();
                    commanders(p);
                }else if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§9-100")){
                    if(MoneyAPI.getBank(p) >= 100){
                        MoneyAPI.removeBank(p, 100);
                        MoneyAPI.addMoney(p, 100);
                        p.sendMessage(Main.prefix + " §7Du hast §6100 §7Money ausgezahlt");
                        commanders(p);
                    }else{
                        p.sendMessage(Main.prefix + " §7Du hast nicht genügend Geld auf der Bank");
                    }
                }else if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§9-1000")){
                    if(MoneyAPI.getBank(p) >= 1000){
                        MoneyAPI.removeBank(p, 1000);
                        MoneyAPI.addMoney(p, 1000);
                        p.sendMessage(Main.prefix + " §7Du hast §61000 §7Money ausgezahlt");
                        commanders(p);
                    }else{
                        p.sendMessage(Main.prefix + " §7Du hast nicht genügend Geld auf der Bank");
                    }
                }else if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§9-10000")){
                    if(MoneyAPI.getBank(p) >= 10000){
                        MoneyAPI.removeBank(p, 10000);
                        MoneyAPI.addMoney(p, 10000);
                        p.sendMessage(Main.prefix + " §7Du hast §610000 §7Money ausgezahlt");
                        commanders(p);
                    }else{
                        p.sendMessage(Main.prefix + " §7Du hast nicht genügend Geld auf der Bank");
                    }
                }else if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§9-Alles")){
                    if(MoneyAPI.getBank(p) > 0){
                        Double amount = MoneyAPI.getBank(p);
                        MoneyAPI.removeBank(p, amount);
                        MoneyAPI.addMoney(p, amount);
                        p.sendMessage(Main.prefix + " §7Du hast §6alles §7ausgezahlt");
                        commanders(p);
                    }else{
                        p.sendMessage(Main.prefix + " §7Du hast nicht genügend Geld auf der Bank");
                    }
                }else if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§9+100")){
                    if(MoneyAPI.getMoney(p) >= 100){
                        MoneyAPI.addBank(p, 100);
                        MoneyAPI.removeMoney(p, 100);
                        p.sendMessage(Main.prefix + " §7Du hast §6100 §7Money eingezahlt");
                        commanders(p);
                    }else{
                        p.sendMessage(Main.prefix + " §7Du hast nicht genügend Geld auf der Bank");
                    }
                }else if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§9+1000")){
                    if(MoneyAPI.getMoney(p) >= 1000){
                        MoneyAPI.addBank(p, 1000);
                        MoneyAPI.removeMoney(p, 1000);
                        p.sendMessage(Main.prefix + " §7Du hast §61000 §7Money eingezahlt");
                        commanders(p);
                    }else{
                        p.sendMessage(Main.prefix + " §7Du hast nicht genügend Geld auf der Bank");
                    }
                }else if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§9+10000")){
                    if(MoneyAPI.getMoney(p) >= 10000){
                        MoneyAPI.addBank(p, 10000);
                        MoneyAPI.removeMoney(p, 10000);
                        p.sendMessage(Main.prefix + " §7Du hast §610000 §7Money eingezahlt");
                        commanders(p);
                    }else{
                        p.sendMessage(Main.prefix + " §7Du hast nicht genügend Geld auf der Bank");
                    }
                }else if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§9+Alles")){
                    if(MoneyAPI.getMoney(p) > 0){
                        Double amount = MoneyAPI.getMoney(p);
                        MoneyAPI.addBank(p, amount);
                        MoneyAPI.removeMoney(p, amount);
                        p.sendMessage(Main.prefix + " §7Du hast §6alles §7eingezahlt");
                        commanders(p);
                    }else{
                        p.sendMessage(Main.prefix + " §7Du hast nicht genügend Geld auf der Bank");
                    }
                }
            }

        }
    }

    private void commanders(final Player p){
        Bukkit.getScheduler().runTaskLater(Main.getInstance(), new Runnable() {
            @Override
            public void run() {
                p.performCommand("trade");
            }
        }, 5);
    }
}
