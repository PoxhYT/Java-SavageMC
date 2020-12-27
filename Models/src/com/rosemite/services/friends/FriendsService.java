package com.rosemite.services.friends;

import com.mongodb.client.MongoDatabase;
import com.rosemite.models.service.common.IService;
import com.rosemite.services.main.MainService;

public class FriendsService {
    private MongoDatabase db;
    private IService service;

    public FriendsService(MongoDatabase db, IService service) {
        this.service = service;
        this.db = db;
    }
}
