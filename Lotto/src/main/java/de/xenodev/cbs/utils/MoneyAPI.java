package main.java.de.xenodev.cbs.utils;

import main.java.de.xenodev.cbs.main.Main;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class MoneyAPI {

    private static File file = new File("plugins//" + Main.getInstance().getName() + "//Coins", "file.yml");
    private static YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);

    public static void createPlayer(Player p){
        setName(p);
        if(hasMoney(p) == false){
            p.sendMessage(Main.prefix + " §7Du hast dein Geldbörse wiedergefunden");
            setMoney(p, 1000);
        }
    }

    public static String getName(Player p){
        return cfg.getString(p.getUniqueId().toString() + ".Name");
    }

    public static void setName(Player p){
        cfg.set(p.getUniqueId().toString() + ".Name", p.getName());
        save();
    }

    public static boolean hasMoney(Player p){
        if(cfg.get(p.getUniqueId().toString() + ".Money") != null){
            return true;
        }
        return false;
    }

    public static Double getMoney(Player p){
        return cfg.getDouble(p.getUniqueId().toString() + ".Money");
    }

    public static void setMoney(Player p, double amount){
        cfg.set(p.getUniqueId().toString() + ".Money", amount);
        save();
    }

    public static void removeMoney(Player p, double amount){
        cfg.set(p.getUniqueId().toString() + ".Money", getMoney(p) - amount);
        save();
    }

    public static void addMoney(Player p, double amount){
        cfg.set(p.getUniqueId().toString() + ".Money", getMoney(p) + amount);
        save();
    }

    public static String getMoneyString(Player p){
        String name = "";
        Double amount = getMoney(p);

        if(amount >= 1000 && !(amount < 1000)){
            double balance = amount / 1000;
            Integer end = Integer.valueOf((int) balance);
            name = end + "k";
        }
        if(amount >= 1000000 && !(amount < 1000000)){
            double balance = amount / 1000000;
            Integer end = Integer.valueOf((int) balance);
            name = end + " Mio";
        }
        if(amount >= 1000000000 && !(amount < 1000000000)){
            double balance = amount / 1000000000;
            Integer end = Integer.valueOf((int) balance);
            name = end + " Mrd";
        }
        if(amount < 1000){
            String last = String.format("%.2f", amount);

            name = last;
        }

        return name;
    }

    public static boolean hasBank(Player p){
        if(cfg.get(p.getUniqueId().toString() + ".Bank") != null){
            return true;
        }
        return false;
    }

    public static Double getBank(Player p){
        return cfg.getDouble(p.getUniqueId().toString() + ".Bank");
    }

    public static void setBank(Player p, double amount){
        cfg.set(p.getUniqueId().toString() + ".Bank", amount);
        save();
    }

    public static void removeBank(Player p, double amount){
        cfg.set(p.getUniqueId().toString() + ".Bank", getBank(p) - amount);
        save();
    }

    public static void addBank(Player p, double amount){
        cfg.set(p.getUniqueId().toString() + ".Bank", getBank(p) + amount);
        save();
    }

    public static String getBankString(Player p){
        String name = "";
        Double amount = getBank(p);

        if(amount >= 1000 && !(amount < 1000)){
            double balance = amount / 1000;
            Integer end = Integer.valueOf((int) balance);
            name = end + "k";
        }
        if(amount >= 1000000 && !(amount < 1000000)){
            double balance = amount / 1000000;
            Integer end = Integer.valueOf((int) balance);
            name = end + " Mio";
        }
        if(amount >= 1000000000 && !(amount < 1000000000)){
            double balance = amount / 1000000000;
            Integer end = Integer.valueOf((int) balance);
            name = end + " Mrd";
        }
        if(amount < 1000){
            String last = String.format("%.2f", amount);

            name = last;
        }

        return name;
    }

    private static void save(){
        try {
            cfg.save(file);
        } catch (IOException e) {}
    }

}
