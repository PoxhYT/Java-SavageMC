package com.rosemite.services.services.skywars;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
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
    private final Http http;

    private List<KitManager> kits;

    public SkywarsServices(Http http) {
        this.http = http;
        kits = new ArrayList<>();
    }

    public List<KitManager> getEveryKit() {
        if (kits.size() != 0) {
            return kits;
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

            kits.addAll(list);

            return list;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
