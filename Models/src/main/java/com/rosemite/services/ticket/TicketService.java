package com.rosemite.services.ticket;

import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.rosemite.models.service.common.IService;
import com.rosemite.helper.Convert;
import com.rosemite.models.service.common.Paths;
import org.bson.Document;

import java.util.AbstractMap;
import java.util.Map;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;

public class TicketService {
    private final MongoDatabase db;
    private final IService service;

    public TicketService(MongoDatabase db, IService service) {
        this.db = db;
        this.service = service;
    }

    public int getTicketAmount(String uuid) {
        Document doc = db.getCollection(Paths.PlayerInfo.toString()).find(eq("uuid", uuid)).first();

        return Convert.c(doc.get("tickets"));
    }

    public int addTickets(String uuid, int amount) {
        int current = getTicketAmount(uuid);
        int total = current + amount;

        db.getCollection(Paths.PlayerInfo.toString()).updateOne(
            Filters.eq("uuid", uuid),
            combine(set("tickets", total))
        );

        return total;
    }

    public Map.Entry<Integer, Boolean> removeTickets(String uuid, int amount) {
        int current = getTicketAmount(uuid);
        int total = current - amount;

        if (total < 0) {
            return new AbstractMap.SimpleEntry<>(current, false);
        }

        db.getCollection(Paths.PlayerInfo.toString()).updateOne(
            Filters.eq("uuid", uuid),
            combine(set( "tickets", total))
        );


        return new AbstractMap.SimpleEntry<>(total, true);
    }
}
