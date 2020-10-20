package com.rosemite.services.helper;

public class Converters {
    public static int c(Object number) {
        if (number.getClass() == Integer.class) {
            return (int)number;
        }

        return (int) Math.round((double)number);
    }
}
