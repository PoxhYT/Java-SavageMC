package de.mlgrush.enums;

public enum LocationType {

    LOBBY("§7Setzt die Lobby"),
    SPAWN_1("§7Setzt den Spawn für Team #1"),
    SPAWN_2("§7Setzt den Spawn für Team #2"),
    BED_TOP_1("§7Setzt den oberen Teil des Bettes für Team #1"),
    BED_BOTTOM_1("§7Setzt den unteren Teil des Bettes für Team #1"),
    BED_TOP_2("§7Setzt den oberen Teil des Bettes für Team #2"),
    BED_BOTTOM_2("§7Setzt den unteren Teil des Bettes für Team #2"),
    POS_1("§7Der untere und äußere Punkt der Arena"),
    POS_2("§7Der obere und äußere Punkt der Arena"),
    SPECTATOR("§7Der Punkt, an dem Zuschauer spawnen");

    private String description;

    LocationType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}


