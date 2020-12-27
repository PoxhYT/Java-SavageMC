package com.rosemite.models.friends;

import java.util.List;

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
}
