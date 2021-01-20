package de.poxh.signsystem.manager;

public enum SignStatus {

    ONLINE("§aONLINE"), OFFLINE("§cOFFLINE"), WARTUNGEN("§4WARTUNGEN"), INGAME("§6INGAME");

    public final String val;
    @Override
    public String toString() {
        return val;
    }
    SignStatus(final String text) {
        this.val = text;
    }
}
