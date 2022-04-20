package com.example.ttapp.database;

import org.bson.Document;

import io.realm.mongodb.RealmResultTask;

public interface IUserRepo {

    RealmResultTask<Document> getDeviceIdTask(String identifier);

}
