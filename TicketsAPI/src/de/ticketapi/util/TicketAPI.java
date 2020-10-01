package de.ticketapi.util;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TicketAPI {
    public static boolean playerExists(String uuid) {
        try {
            ResultSet rs = MySQL.getResult("SELECT * FROM TICKETAPI WHERE UUID='" + uuid + "'");
            if (rs.next())
                return (rs.getString("UUID") != null);
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void createPlayer(String uuid) {
        if (!playerExists(uuid))
            MySQL.update("INSERT INTO TICKETAPI (UUID, TICKETS) VALUES ('" + uuid + "', '5');");
    }

    public static Integer getTickets(String uuid) {
        Integer i = Integer.valueOf(0);
        if (playerExists(uuid))
            try {
                ResultSet rs = MySQL.getResult("SELECT * FROM TICKETAPI WHERE UUID='" + uuid + "'");
                if (rs.next())
                    Integer.valueOf(rs.getInt("TICKETS"));
                i = Integer.valueOf(rs.getInt("TICKETS"));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        return i;
    }

    public static void setTickets(String uuid, Integer tickets) {
        if (playerExists(uuid))
            MySQL.update("UPDATE TICKETAPI SET TICKETS='" + tickets + "' WHERE UUID='" + uuid + "'");
    }

    public static void addTickets(String uuid, Integer tickets) {
        if (playerExists(uuid))
            setTickets(uuid, Integer.valueOf(getTickets(uuid).intValue() + tickets.intValue()));
    }

    public static void removeTickets(String uuid, Integer tickets) {
        if (playerExists(uuid))
            setTickets(uuid, Integer.valueOf(getTickets(uuid).intValue() - tickets.intValue()));
    }
}


