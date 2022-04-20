package com.example.ttapp.database;

import org.bson.Document;

import io.realm.mongodb.RealmResultTask;

/**
 * Interface for the user repository connected to TouchAndTell/Users in MongoDB atlas
 */
public interface IUserRepo {

    RealmResultTask<Document> getDeviceIdTask(String identifier);

}