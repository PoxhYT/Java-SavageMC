package com.rosemite.services.helper;

import com.google.gson.internal.Primitives;
import de.sw.api.ItemBuilderAPI;
import org.bukkit.Material;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Convert {
    public static int c(Object number) {
        if (number.getClass() == Integer.class) {
            return (int)number;
        }

        return (int) Math.round((double)number);
    }

    public static Map<String, Object> getPropertiesToMap(Object instance) {
        Map<String, Object> data = new HashMap<>();

        for (Field field : instance.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            Object value = null;

            try {
                value = field.get(instance);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            try {
                if (value != null) {
                    try {
                        if (!isPrimitive(value)) {
                            value = getPropertiesToMap(value);
                        }
                    } catch (Exception e) {
                        Log.d("bad");
//                    e.printStackTrace();
                    }
                    try {
                        value = field.get(instance);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception ignore) {

            }

            data.put(field.getName(), value);
        }

        return data;
    }

    public static boolean isPrimitive(Object o) {
        if (o == null) {
            return true;
        }

        return Primitives.isWrapperType(o.getClass());
    }
}
