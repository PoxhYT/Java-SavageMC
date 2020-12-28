package com.rosemite.models.friends;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FriendsInfo {
    public final String playername; // Player name
    public final String uuid; // Player UUID
    public final List<String> friends; // Player Friends
    public final List<String> openFriendRequests; // FriendRequests that haven't been denied or accepted yet
    public final List<String> deniedFriendRequests; // FriendRequests that have been denied

    public FriendsInfo(String playername, String uuid, List<String> friends, List<String> openFriendRequests, List<String> deniedFriendRequests) {
        this.playername = playername;
        this.uuid = uuid;
        this.friends = friends;
        this.openFriendRequests = openFriendRequests;
        this.deniedFriendRequests = deniedFriendRequests;
    }

    private FriendsInfo(String playername, String uuid) {
        this.playername = playername;
        this.uuid = uuid;
        this.deniedFriendRequests = new ArrayList<>();
        this.openFriendRequests = new ArrayList<>();
        this.friends = new ArrayList<>();
    }

    public static FriendsInfo initialize(String uuid, String name) {
        return new FriendsInfo(uuid, name);
    }

    public Map<String, Object> toJson() {
        String jsonStr = new Gson().toJson(this);
        return new Gson().fromJson(jsonStr, Map.class);
    }
}
