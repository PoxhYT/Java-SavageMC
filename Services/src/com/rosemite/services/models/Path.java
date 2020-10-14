package com.rosemite.services.models;

public class Path {
    private String path = null;

    public String get() {
        return path;
    }

    public Path(Paths paths, String document) {
        this.path = paths + document;
    }
}
