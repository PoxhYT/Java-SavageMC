package com.rosemite.models.friends;

public enum ResponseCode {
    IsUnderDeniedFriends,
    DocumentUndefined,
    AreAlreadyFriends,
    AreNotFriends,
    RequestStillPending,
    AddedYourSelf,
    Successful;
//    WasAddedToDeniedFriends(201),
//    DocumentUndefined(202),
//    AreAlreadyFriends(203),
//    RequestStillPending(204),
//    AddedYourSelf(205),
//    Successful(200);

//    public final int code;
//    ResponseCode(int code) { this.code = code; }
}
