package com.rosemite.helper;

import com.google.gson.Gson;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

public class Convert {
    private final static Gson g = new Gson();
    private final static String pattern = "dd/MM/yyyy HH:mm:ss";
    private final static DateFormat df = new SimpleDateFormat(pattern);

    public static int c(Object number) {
        if (number.getClass() == Integer.class) {
            return (int)number;
        }

        return (int) Math.round((double)number);
    }

    public static String date() {
        return date(new Date());
    }

    public static String date(Date date) {
        if (date == null) {
            return null;
        }

        return df.format(date);
    }

    public static Date date(String date) {
        if (date == null) {
            return null;
        }

        DateFormat format = new SimpleDateFormat(pattern, Locale.ENGLISH);
        try {
            return format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Map<String, Object> getPropertiesToMap(Object instance) {
        return g.fromJson(g.toJson(instance), Map.class);
    }
}
