package de.poxh.services.services.ticket;

import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import de.poxh.services.helper.Convert;
import de.poxh.services.main.MainService;
import de.poxh.services.models.common.Paths;
import javafx.util.Pair;
import org.bson.Document;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;

public class TicketService {
    private final MongoDatabase db;
    private final MainService service;

    public TicketService(MongoDatabase db, MainService service) {
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

    public Pair<Integer, Boolean> removeTickets(String uuid, int amount) {
        int current = getTicketAmount(uuid);
        int total = current - amount;

        if (total < 0) {
            return new Pair<>(current, false);
        }

        db.getCollection(Paths.PlayerInfo.toString()).updateOne(
                Filters.eq("uuid", uuid),
                combine(set( "tickets", total))
        );

        return new Pair<>(total, true);
    }
}
