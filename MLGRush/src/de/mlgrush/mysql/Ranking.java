package de.mlgrush.mysql;

import de.mlgrush.api.SQLStats;
import de.mlgrush.main.Main;
import org.bukkit.*;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.block.Skull;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class Ranking{

    static HashMap<Integer, String> rank = new HashMap<>();

    public static void set() {

        ResultSet resultSet = Main.mySQL.query("SELECT UUID FROM Stats ORDER BY Wins DESC LIMIT 1");

        int in = 0;

        try {
            while (resultSet.next()) {
                in++;
                rank.put(in, resultSet.getString("UUID"));
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        Location location = new Location(Bukkit.getWorld("world"), -33, 67, 43);

        List<Location> LOC = new ArrayList<>();
        LOC.add(location);

        for(int i = 0; i < LOC.size(); i++) {
            int id = i+1;

            Player player = Bukkit.getPlayer(UUID.fromString(rank.get(id)));

            LOC.get(i).getBlock().setType(Material.SKULL);

            Skull skull = (Skull) LOC.get(i).getBlock().getState();
            skull.setSkullType(SkullType.PLAYER);
            String name = Bukkit.getOfflinePlayer(UUID.fromString(rank.get(id))).getName();
            skull.setOwner(name);
            skull.update();

            Location newloc = new Location(LOC.get(i).getWorld(), LOC.get(i).getX(), LOC.get(i).getY() -1, LOC.get(i).getZ());

            if(newloc.getBlock().getState() instanceof Sign);
                BlockState blockState = newloc.getBlock().getState();
                Sign sign = (Sign) blockState;

                int kills = SQLStats.getKills(player.getUniqueId().toString());
                int deaths = SQLStats.getDeaths(player.getUniqueId().toString());

                double KD = ((double) kills) / ((double) deaths);

                DecimalFormat decimalFormat = new DecimalFormat("#.##");
                String formatted = decimalFormat.format(KD);

                sign.setLine(0, "Platz #" + id);
                sign.setLine(1, name);
                sign.setLine(2, SQLStats.getWins(rank.get(id)) + " Siege");
                sign.setLine(3, "§7KD: §e" + formatted.replace("Infinity", "0").replace("NaN", "0"));
                sign.update();
        }

    }
}
