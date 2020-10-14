package com.rosemite.services.models;

public class Path {
    private String path = null;

    public String get() {
        return path;
    }

    public Path(Paths paths, String document) {
        switch (paths) {
            case SoupTraining:
                this.path = paths + document;
                break;
        }
    }
}
