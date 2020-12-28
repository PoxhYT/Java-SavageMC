package com.rosemite.services.friends;

import com.google.gson.Gson;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
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

    // Todo: Do some null checks everywhere later.
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

    public void acceptFriendRequest(String requesterUUID, String friendUUID) {
        Document doc1 = db.getCollection(Paths.Relationships.val).find(
                Filters.eq("uuid", requesterUUID)
        ).first();

        Document doc2 = db.getCollection(Paths.Relationships.val).find(
                Filters.eq("uuid", friendUUID)
        ).first();

        FriendsInfo requesterInfo = getFriendInfo(doc1);
        FriendsInfo friendInfo = getFriendInfo(doc2);

        try {
            requesterInfo.openFriendRequests.remove(friendInfo.uuid);
        } catch (Exception ignore) { }
        try {
            friendInfo.openFriendRequests.remove(requesterInfo.uuid);
        } catch (Exception ignore) { }

        requesterInfo.friends.add(friendUUID);
        friendInfo.friends.add(requesterUUID);

        db.getCollection(Paths.Relationships.val).updateOne(
                Filters.eq("uuid", requesterUUID),
                combine(
                set("openFriendRequests", requesterInfo.openFriendRequests),
                        set("friend", requesterInfo.friends)
                )
        );

        db.getCollection(Paths.Relationships.val).updateOne(
            Filters.eq("uuid", friendUUID),
            combine(
           set("openFriendRequests", friendInfo.openFriendRequests),
                    set("friend", friendInfo.friends)
            )
        );
    }

    public FriendsInfo getPlayerFriendsInfo(String uuid) {
        // Todo: Do some null checks later.
        Document doc = db.getCollection(Paths.Relationships.val).find(
                Filters.eq("uuid", uuid)
        ).first();

        if (doc == null) {
            // Todo: Here...
            return null;
        }

        return getFriendInfo(doc);
    }

    public void denyFriendRequest(String requesterUUID, String friendUUID) {
        // Todo:...
    }

    public void removePlayerFromDenyFriendRequest(String requesterUUID, String friendUUID) {
        // Todo:...
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
