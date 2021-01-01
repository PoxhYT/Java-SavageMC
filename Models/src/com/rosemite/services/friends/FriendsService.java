package com.rosemite.services.friends;

import com.google.gson.Gson;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.UpdateResult;
import com.rosemite.helper.Log;
import com.rosemite.models.friends.FriendsInfo;
import com.rosemite.models.friends.ResponseCode;
import com.rosemite.models.service.common.IService;
import com.rosemite.models.service.common.Paths;
import javafx.util.Pair;
import org.bson.Document;

import java.util.List;

import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;

public class FriendsService {
    private final MongoDatabase db;
    private final IService service;

    public FriendsService(MongoDatabase db, IService service) {
        this.service = service;
        this.db = db;
    }

    public void initializeRelation(String uuid, String name) {
        FriendsInfo info = FriendsInfo.initialize(uuid, name);

        db.getCollection(Paths.Relationships.val).insertOne(new Document(info.toJson()));
    }

    public ResponseCode makeFriendRequest(String requesterUUID, String friendUUID) {
        Pair<ResponseCode, FriendsInfo> res = verify(requesterUUID, friendUUID);
        if (res.getKey() != ResponseCode.Successful) {
            return res.getKey();
        }

        ResponseCode code = verifyForAddFriend(res.getValue(), requesterUUID);
        if (code != ResponseCode.Successful) {
            return code;
        }

        List<String> openRequests = res.getValue().openFriendRequests;
        openRequests.add(requesterUUID);

        db.getCollection(Paths.Relationships.val).updateOne(
            Filters.eq("uuid", friendUUID),
            combine(set("openFriendRequests", openRequests))
        );

        return ResponseCode.Successful;
    }

    public ResponseCode acceptFriendRequest(String requesterUUID, String friendUUID) {
        Document doc1 = db.getCollection(Paths.Relationships.val).find(
                Filters.eq("uuid", requesterUUID)
        ).first();

        Document doc2 = db.getCollection(Paths.Relationships.val).find(
                Filters.eq("uuid", friendUUID)
        ).first();

        if (doc1 == null || doc2 == null) {
            return ResponseCode.DocumentUndefined;
        }

        FriendsInfo requesterInfo = getFriendInfo(doc1);
        FriendsInfo friendInfo = getFriendInfo(doc2);

        requesterInfo.openFriendRequests.remove(friendInfo.uuid);
        friendInfo.openFriendRequests.remove(requesterInfo.uuid);

        requesterInfo.friends.add(friendUUID);
        friendInfo.friends.add(requesterUUID);

        db.getCollection(Paths.Relationships.val).updateOne(
                Filters.eq("uuid", requesterUUID),
                combine(
                set("openFriendRequests", requesterInfo.openFriendRequests),
                        set("friends", requesterInfo.friends)
                )
        );

        db.getCollection(Paths.Relationships.val).updateOne(
            Filters.eq("uuid", friendUUID),
            combine(
           set("openFriendRequests", friendInfo.openFriendRequests),
                    set("friends", friendInfo.friends)
            )
        );

        return ResponseCode.Successful;
    }



    public Pair<ResponseCode, FriendsInfo> getPlayerFriendsInfo(String uuid) {
        Document doc = db.getCollection(Paths.Relationships.val).find(
                Filters.eq("uuid", uuid)
        ).first();

        if (doc == null) {
            return new Pair<>(ResponseCode.DocumentUndefined, null);
        }

        return new Pair<>(ResponseCode.Successful, getFriendInfo(doc));
    }

    public ResponseCode denyFriendRequest(String currentUUID, String deniedUUID) {
        Document doc = db.getCollection(Paths.Relationships.val).find(
            Filters.eq("uuid", currentUUID)
        ).first();

        if (doc == null) {
            return ResponseCode.DocumentUndefined;
        }

        FriendsInfo info = getFriendInfo(doc);
        info.deniedFriendRequests.add(deniedUUID);

        db.getCollection(Paths.Relationships.val).updateOne(
            Filters.eq("uuid", currentUUID),
                combine(set("deniedFriendRequests", info.deniedFriendRequests))
        );

        return ResponseCode.Successful;
    }

    public ResponseCode removePlayerFromDenyFriendRequest(String currentUUID, String deniedUUID) {
        Document doc = db.getCollection(Paths.Relationships.val).find(
                Filters.eq("uuid", currentUUID)
        ).first();

        if (doc == null) {
            return ResponseCode.DocumentUndefined;
        }

        FriendsInfo info = getFriendInfo(doc);
        info.deniedFriendRequests.remove(deniedUUID);

        db.getCollection(Paths.Relationships.val).updateOne(
                Filters.eq("uuid", currentUUID),
                combine(set("deniedFriendRequests", info.deniedFriendRequests))
        );

        return ResponseCode.Successful;
    }

    public ResponseCode removeFriend(String requesterUUID, String friendUUID) {
        Pair<ResponseCode, Boolean> value = areFriends(requesterUUID, friendUUID);

        if (value.getKey() != ResponseCode.Successful || value.getValue() == null || !value.getValue()) {
            return value.getKey();
        }

        Document doc1 = db.getCollection(Paths.Relationships.val).find(
            Filters.eq("uuid", requesterUUID)
        ).first();

        Document doc2 = db.getCollection(Paths.Relationships.val).find(
            Filters.eq("uuid", friendUUID)
        ).first();

        if (doc1 == null || doc2 == null) {
            return ResponseCode.DocumentUndefined;
        }

        List<String> infoRequester = getFriendInfo(doc1).friends;
        List<String> infoFriend = getFriendInfo(doc2).friends;

        infoRequester.remove(friendUUID);
        infoFriend.remove(requesterUUID);

        db.getCollection(Paths.Relationships.val).updateOne(
                Filters.eq("uuid", requesterUUID),
            combine( set("friends", infoRequester))
        );

        db.getCollection(Paths.Relationships.val).updateOne(
            Filters.eq("uuid", friendUUID),
            combine( set("friends", infoFriend))
        );

        return ResponseCode.Successful;
    }

    private Pair<ResponseCode, FriendsInfo> verify(String requesterUUID, String friendUUID) {
        if (requesterUUID.equalsIgnoreCase(friendUUID)) {
            return new Pair<>(ResponseCode.AddedYourSelf, null);
        }

        Document doc = db.getCollection(Paths.Relationships.val).find(Filters.eq("uuid", friendUUID)).first();
        if (doc == null) {
            return new Pair<>(ResponseCode.DocumentUndefined, null);
        }

        FriendsInfo info = new Gson().fromJson(doc.toJson(), FriendsInfo.class);

        return new Pair<>(ResponseCode.Successful, info);
    }

    private Pair<ResponseCode, Boolean> areFriends(String requesterUUID, String friendUUID) {
        Pair<ResponseCode, FriendsInfo> res = verify(requesterUUID, friendUUID);

        if (res.getKey() != ResponseCode.Successful) {
            return new Pair<>(res.getKey(), null);
        }

        FriendsInfo info = res.getValue();
        if (!info.friends.contains(requesterUUID)) {
            return new Pair<>(ResponseCode.AreNotFriends, false);
        }

        return new Pair<>(ResponseCode.Successful, true);
    }

    private ResponseCode verifyForAddFriend(FriendsInfo info, String requesterUUID) {
        if (info.deniedFriendRequests.contains(requesterUUID)) {
            return ResponseCode.IsUnderDeniedFriends;
        } else if (info.openFriendRequests.contains(requesterUUID)) {
            return ResponseCode.RequestStillPending;
        } else if (info.friends.contains(requesterUUID)) {
            return ResponseCode.AreAlreadyFriends;
        }

        return ResponseCode.Successful;
    }

    private FriendsInfo getFriendInfo(String json) {
        return new Gson().fromJson(json, FriendsInfo.class);
    }

    private FriendsInfo getFriendInfo(Document doc) {
        return getFriendInfo(doc.toJson());
    }
}
