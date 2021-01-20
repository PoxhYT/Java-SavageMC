package com.rosemite.services.reward;

import com.google.gson.Gson;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.rosemite.models.reward.RewardInfo;
import com.rosemite.models.service.common.IService;
import com.rosemite.models.service.common.Paths;
import com.rosemite.models.service.player.PlayerInfo;
import com.rosemite.services.main.MainService;
import org.bson.Document;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;

public class RewardService {
    private MongoDatabase db;

    public RewardService(MongoDatabase db, IService mainService) {
        this.db = db;
    }

    public RewardInfo createNewPlayer(String uuid, String displayName) {

        Date date = new Date();
        String pattern = "dd/MM/yyyy H:m";
        DateFormat df = new SimpleDateFormat(pattern);
        String dateAsString = df.format(date);

        RewardInfo rewardInfo = new RewardInfo(
                uuid,
                displayName,
                dateAsString,
                false,
                dateAsString,
                false,
                dateAsString,
                false,
                dateAsString,
                false,
                dateAsString,
                false,
                dateAsString,
                false
        );

        String json = new Gson().toJson(rewardInfo, RewardInfo.class);
        Map<String, Object> doc = new Gson().fromJson(json, Map.class);
        db.getCollection(Paths.RewardInfo.toString()).insertOne(new Document(doc));

        return rewardInfo;
    }

    public RewardInfo getPlayerInfo(String uuid) {
        Document doc = db.getCollection(Paths.RewardInfo.toString()).find(Filters.eq("uuid", uuid)).first();
        if (doc == null) {
            return null;
        }

        return new Gson().fromJson(doc.toJson(), RewardInfo.class);
    }

    public RewardInfo getPlayerInfoByName(String name) {
        Document doc = db.getCollection(Paths.RewardInfo.toString()).find(Filters.eq("displayName", name)).first();
        if (doc == null) {
            return null;
        }

        return new Gson().fromJson(doc.toJson(), RewardInfo.class);
    }

    public void setPlayerTicketDate(String uuid, String date) {
        db.getCollection(Paths.RewardInfo.toString()).updateOne((Filters.eq("uuid", uuid)),
                combine(set("playerTicketReceive", date)));
    }

    public String getPlayerTicketDate(String uuid) {
        Document doc = db.getCollection(Paths.RewardInfo.toString()).find(eq("uuid", uuid)).first();
        return (String) doc.get("playerTicketReceive");
    }

    public void setPlayerTicket(String uuid, boolean receive) {
        db.getCollection(Paths.RewardInfo.toString()).updateOne((Filters.eq("uuid", uuid)),
                combine(set("playerTicketReceived", receive)));
    }

    public void setPlayerCoinsDate(String uuid, String date) {
        db.getCollection(Paths.RewardInfo.toString()).updateOne((Filters.eq("uuid", uuid)),
                combine(set("playerCoinsReceive", date)));
    }

    public String getPlayerCoinsDate(String uuid) {
        Document doc = db.getCollection(Paths.RewardInfo.toString()).find(eq("uuid", uuid)).first();
        return (String) doc.get("playerCoinsReceive");
    }

    public void setPlayerCoins(String uuid, boolean receive) {
        db.getCollection(Paths.RewardInfo.toString()).updateOne((Filters.eq("uuid", uuid)),
                combine(set("playerCoinsReceived", receive)));
    }

    public void setPremiumTicketDate(String uuid, String date) {
        db.getCollection(Paths.RewardInfo.toString()).updateOne((Filters.eq("uuid", uuid)),
                combine(set("premiumTicketReceive", date)));
    }

    public String getPremiumTicketDate(String uuid) {
        Document doc = db.getCollection(Paths.RewardInfo.toString()).find(eq("uuid", uuid)).first();
        return (String) doc.get("premiumTicketReceive");
    }

    public void setPremiumTicket(String uuid, boolean receive) {
        db.getCollection(Paths.RewardInfo.toString()).updateOne((Filters.eq("uuid", uuid)),
                combine(set("premiumTicketReceived", receive)));
    }

    public void setPremiumCoinsDate(String uuid, String date) {
        db.getCollection(Paths.RewardInfo.toString()).updateOne((Filters.eq("uuid", uuid)),
                combine(set("premiumCoinsReceive", date)));
    }

    public String getPremiumCoinsDate(String uuid) {
        Document doc = db.getCollection(Paths.RewardInfo.toString()).find(eq("uuid", uuid)).first();
        return (String) doc.get("premiumCoinsReceive");
    }

    public void setPremiumCoins(String uuid, boolean receive) {
        db.getCollection(Paths.RewardInfo.toString()).updateOne((Filters.eq("uuid", uuid)),
                combine(set("premiumCoinsReceived", receive)));
    }

    public void setSavageTicketDate(String uuid, String date) {
        db.getCollection(Paths.RewardInfo.toString()).updateOne((Filters.eq("uuid", uuid)),
                combine(set("savageTicketReceive", date)));
    }

    public String getSavageTicketDate(String uuid) {
        Document doc = db.getCollection(Paths.RewardInfo.toString()).find(eq("uuid", uuid)).first();
        return (String) doc.get("savageTicketReceive");
    }

    public void setSavageTicket(String uuid, boolean receive) {
        db.getCollection(Paths.RewardInfo.toString()).updateOne((Filters.eq("uuid", uuid)),
                combine(set("savageTicketReceived", receive)));
    }

    public void setSavageCoinsDate(String uuid, String date) {
        db.getCollection(Paths.RewardInfo.toString()).updateOne((Filters.eq("uuid", uuid)),
                combine(set("savageCoinsReceive", date)));
    }

    public String getSavageCoinsDate(String uuid) {
        Document doc = db.getCollection(Paths.RewardInfo.toString()).find(eq("uuid", uuid)).first();
        return (String) doc.get("savageCoinsReceive");
    }

    public void setSavageCoins(String uuid, boolean receive) {
        db.getCollection(Paths.RewardInfo.toString()).updateOne((Filters.eq("uuid", uuid)),
                combine(set("savageCoinsReceived", receive)));
    }


}
