package main.java.de.xenodev.cbs.main;

import main.java.de.xenodev.cbs.command.*;
import main.java.de.xenodev.cbs.listener.InvHeldListener;
import main.java.de.xenodev.cbs.listener.JoinListener;
import main.java.de.xenodev.cbs.listener.QuitListener;
import main.java.de.xenodev.cbs.utils.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private static Main instance;

    public static String prefix;

    @Override
    public void onEnable() {
        instance = this;

        FileManager.setConfig();
        zinsCD();
        scoreCD();
        timeCD();

        commands();
        listeners();
    }

    @Override
    public void onDisable() {

    }

    private void commands(){
        getServer().getPluginCommand("money").setExecutor(new MoneyCMD());
        getServer().getPluginCommand("bank").setExecutor(new BankCMD());
        getServer().getPluginCommand("ticket").setExecutor(new TicketCMD());
        getServer().getPluginCommand("trade").setExecutor(new TradeCMD());
        getServer().getPluginCommand("oz").setExecutor(new TimeCMD());
        getServer().getPluginCommand("onlinezeit").setExecutor(new TimeCMD());
        getServer().getPluginCommand("ot").setExecutor(new TimeCMD());
        getServer().getPluginCommand("onlinetime").setExecutor(new TimeCMD());
    }

    private void listeners(){
        getServer().getPluginManager().registerEvents(new InvHeldListener(), this);
        getServer().getPluginManager().registerEvents(new JoinListener(), this);
        getServer().getPluginManager().registerEvents(new ChestLottery(), this);
        getServer().getPluginManager().registerEvents(new TradeCMD(), this);
        getServer().getPluginManager().registerEvents(new QuitListener(), this);
    }

    public static Main getInstance(){
        return instance;
    }

    private static void zinsCD(){
        Bukkit.getScheduler().scheduleSyncRepeatingTask(instance, new Runnable() {

            @Override
            public void run() {
                for (Player all : Bukkit.getOnlinePlayers()) {
                    if(MoneyAPI.hasBank(all) == true){
                        if(MoneyAPI.getBank(all) >= 0){
                            double bank = (MoneyAPI.getBank(all) * 0.01) / Bukkit.getOnlinePlayers().size();
                            String money = String.format("%.2f", bank);
                            MoneyAPI.addBank(all, bank);
                            all.sendMessage(Main.prefix + " §7Du hast §e" + money + " §7Money an Zinsen bekommen");
                        }
                    }else{
                        all.sendMessage(Main.prefix + " §7Du hast noch kein Bankkonto");
                        all.sendMessage(Main.prefix + " §7Kaufe dir ein Bankkonto mit /trade buy");
                    }
                }
            }
        }, 20, 20*60*60);
    }

    public static void scoreCD() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(instance, new Runnable() {

            @Override
            public void run() {
                for (Player all : Bukkit.getOnlinePlayers()) {
                    SBManager.updateScoreboard(all);
                }
            }
        }, 0, 20);
    }

    public static void timeCD() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(instance, new Runnable() {

            @Override
            public void run() {
                for (Player all : Bukkit.getOnlinePlayers()) {
                    if (TimeManager.getSeconds(all) >= 59) {
                        TimeManager.setSeconds(all, 0);
                        TimeManager.addMinutes(all, 1);
                    } else {
                        TimeManager.addSeconds(all, 1);
                    }

                    if (TimeManager.getMinutes(all) >= 59) {
                        TimeManager.setMinutes(all, 0);
                        TimeManager.addHours(all, 1);
                    }
                }
            }
        }, 0, 20);
    }
}
