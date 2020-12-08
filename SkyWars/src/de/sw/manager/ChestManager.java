package de.sw.manager;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.internal.Primitives;
import com.rosemite.services.helper.Convert;
import com.rosemite.services.helper.Log;
import de.sw.api.ItemBuilderAPI;
import de.sw.enums.Path;
import de.sw.main.Main;
import de.sw.manager.SkyWarsMapData;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

public class ChestManager {
    private final Location locationOfMiddlePoint;
    private final SkyWarsMapData map;
    private final Random random;

    private final List<RandomItemInChest> normal;
    private final List<RandomItemInChest> center;

    public ChestManager(Location locationOfMiddlePoint, SkyWarsMapData map) {
        this.locationOfMiddlePoint = locationOfMiddlePoint;
        this.map = map;

        random = new Random();

        this.normal = configureRandomItemInChest(false);
        this.center = configureRandomItemInChest(true);
    }

    private List<RandomItemInChest> configureRandomItemInChest(boolean center) {
        if (center) {
            String json = new Gson().toJson(Main.center);
            Type listType = new TypeToken<ArrayList<RandomItemInChest>>(){}.getType();
            List<RandomItemInChest> list = new Gson().fromJson(json, listType);

            if (list == null) {
                list = new ArrayList<>();
            }

            return list;
        }

        String json = new Gson().toJson(Main.normal);
        Type listType = new TypeToken<ArrayList<RandomItemInChest>>(){}.getType();
        List<RandomItemInChest> list = new Gson().fromJson(json, listType);

        if (list == null) {
            list = new ArrayList<>();
        }

        return list;
    }

    public void onGameStart() {
        List<Chest> chestsInMiddle = getAllChestsInRadius(this.locationOfMiddlePoint, map.getRadius());
        List<Chest> allChests = getAllChestsInWorld();

        // Clear Chests
        allChests.forEach(chest -> chest.getBlockInventory().clear());

        // Populate all chests first then override with chestsInMiddle List
        populateChests(allChests, false);

        // Clear All Middle Chests
        chestsInMiddle.forEach(chest -> chest.getBlockInventory().clear());

        populateChests(chestsInMiddle, true);
    }

    private void populateChests(List<Chest> chests, boolean center) {
        for (int i = 0; i < chests.size(); i++) {
            // Generate Random number
            int length = getRandomNumber(7, 16);

            for (int j = 0; j < length; j++) {
                RandomItemInChest item = getRandomItem(center);
                ItemBuilderAPI builder = new ItemBuilderAPI(Material.getMaterial(item.ItemId));

                item.KitEnchantments.forEach(kitEnchantments -> {
                    builder.addEnchantment(Enchantment.getById(kitEnchantments.EnchantmentId), kitEnchantments.EnchantmentLevel);
                });

                int index = getRandomNumber(0, 23);

                if(item.Min == item.Max) {
                    chests.get(i).getBlockInventory().setItem(index, builder.setAmount(getRandomNumber(item.Min, item.Max + 1)).build());
                    continue;
                }

                chests.get(i).getBlockInventory().setItem(index, builder.setAmount(getRandomNumber(item.Min, item.Max)).build());
            }
        }
    }

    private List<Chest> getAllChestsInWorld() {
        List<Chest> chests = new ArrayList<>();

        for (Chunk c : this.locationOfMiddlePoint.getWorld().getLoadedChunks()) {
            for (BlockState b : c.getTileEntities()) {
                if (b instanceof Chest){
                    chests.add((Chest) b);
                }
            }
        }

        return chests;
    }

    private int getRandomNumber(int min, int max) {
        return random.nextInt(max - min) + min;
    }

    private RandomItemInChest getRandomItem(boolean center) {
        if (center) {
            int i = random.nextInt(99) + 1;

            List<RandomItemInChest> items = this.center.stream().filter((c) -> c.Chance >= i).collect(Collectors.toList());
            Log.d(items.size());
            if (items.size() == 0) {
                return getRandomItem(center);
            }
            return items.get(random.nextInt(items.size()));
        }

        int i = random.nextInt(65) + 1;


        List<RandomItemInChest> items = this.normal.stream().filter((c) -> c.Chance >= i).collect(Collectors.toList());
        Log.d(items.size());
        if (items.size() == 0) {
            return getRandomItem(center);
        }
        return items.get(random.nextInt(items.size()));
    }

    private List<Chest> getAllChestsInRadius(Location locationOfMiddlePoint, int radius) {
        if (radius < 0) {
            return new ArrayList<>(0);
        }

        List<Chest> chests = new ArrayList<>();

        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    Block b = locationOfMiddlePoint.getBlock().getRelative(x, y, z);

                    if (b.getState() instanceof Chest) {
                        chests.add((Chest) b.getState());
                    }
                }
            }
        }

        return chests;
    }
}
