package com.rosemite.services.models;

public enum Paths {
    SoupTraining("SoupTraining/");
    private final String text;

    @Override
    public String toString() {
        return text;
    }

    Paths(final String text) {
        this.text = text;
    }
}
