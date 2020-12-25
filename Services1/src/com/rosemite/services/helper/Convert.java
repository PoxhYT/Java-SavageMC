package com.rosemite.services.helper;

import com.google.gson.Gson;
import java.util.Map;

public class Convert {
    private final static Gson g = new Gson();

    public static int c(Object number) {
        if (number.getClass() == Integer.class) {
            return (int)number;
        }

        return (int) Math.round((double)number);
    }

    public static Map<String, Object> getPropertiesToMap(Object instance) {
        return g.fromJson(g.toJson(instance), Map.class);
    }
}
