package com.rosemite.services.services.skywars;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rosemite.services.helper.Log;
import com.rosemite.services.main.MainService;
import com.rosemite.services.models.player.PlayerSkywarsKits;
import com.rosemite.services.services.http.Http;
import com.rosemite.services.models.http.HttpType;
import com.rosemite.services.models.http.HttpResponse;
import com.rosemite.services.models.common.Path;
import com.rosemite.services.models.common.Paths;
import de.sw.manager.KitManager;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;

public class SkywarsServices {
    private Map<UUID, PlayerSkywarsKits> map;
    private final Http http;

    private List<KitManager> allKits;

    public SkywarsServices(Http http) {
        this.http = http;
        allKits = new ArrayList<>();

        map = new HashMap<>();
    }

    public List<KitManager> verifyKits(List<KitManager> kits, UUID uuid) {
        if (map.get(uuid) == null) {
            map.put(uuid, MainService.getService(null)
                    .getPlayerService()
                    .fetchPlayerSkywarsKits(uuid));
        }

        PlayerSkywarsKits playerKits = map.get(uuid);

        if (playerKits == null) {
            Log.d("Problem");
            http.reportError("Player Kits was null but it should not be. Check if this UUID is under players collection: " + uuid.toString());
            return kits;
        }

        for (int i = 0; i < kits.size(); i++) {
            if (playerKits.hasSkywarsKit(kits.get(i).getKitNameLiteralString())) {
                kits.get(i).setHasKit(true);
            }
        }

        return kits;
    }

    public List<KitManager> getEveryKit() {
        if (allKits.size() != 0) {
            return allKits;
        }
        try {
            Map<String, String> header = new HashMap<>();
            header.put("collection", "true");

            HttpResponse res = http.request(HttpType.GET,null, new Path(Paths.SkywarsKits, ""), header);

            if (res.statusCode != 200) {
                http.reportError(res.content);
                return null;
            }

            String json = new Gson().toJson(res.getAsMap().get("data"));

            Type listType = new TypeToken<ArrayList<KitManager>>(){}.getType();
            List<KitManager> list = new Gson().fromJson(json, listType);

            list.sort(Comparator.comparingInt(KitManager::getKitPrice));

            allKits.addAll(list);

            return list;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
