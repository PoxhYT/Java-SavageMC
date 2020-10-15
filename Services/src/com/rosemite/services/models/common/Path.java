package com.rosemite.services.models.common;

public class Path {
    private String path = null;

    public String get() {
        return path;
    }

    public Path(Paths path, String document) {
        this.path = path + document;
    }

    public Path(Paths path, String document, Paths path2, String document2) {
        this.path = path + document + path2 + document;
    }
}
