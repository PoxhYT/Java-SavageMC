package com.rosemite.server.config;

import com.google.gson.Gson;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

public class Config {
    public static String getConnectionString() {
        Map<String, String> json = null;

        String path = System.getProperty("user.dir") + "\\plugins\\ServerServices\\Config.json";

        try {
            json = new Gson().fromJson(new String(Files.readAllBytes(Paths.get(path))), Map.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return json.get("connectionString");
    }
}
