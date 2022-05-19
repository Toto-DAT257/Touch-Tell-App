package com.example.ttapp.database;

import android.util.Log;

import org.bson.Document;

import io.realm.mongodb.RealmResultTask;
import io.realm.mongodb.User;
import io.realm.mongodb.mongo.MongoClient;
import io.realm.mongodb.mongo.MongoCollection;
import io.realm.mongodb.mongo.MongoDatabase;

/**
 * Implementation of {@link IUserRepo} for MongoDB Realm.
 */
public class MongoUserRepo implements IUserRepo {

    private static final String DATABASE = "TouchAndTell";
    private static final String COLLECTION = "Users";

    @Override
    public RealmResultTask<Document> getDeviceIdRealmTask(String identifier) {
        MongoCollection<Document> userCollection = getCollection();
        return getDeviceId(identifier, userCollection);
    }

    private RealmResultTask<Document> getDeviceId(String identifier, MongoCollection<Document> userCollection) {
        Document filter = new Document().append("mail", identifier);
        return userCollection.findOne(filter);
    }

    private MongoCollection<Document> getCollection() {
        // TODO null error handling
        MongoDB.assertDatabaseAccess();
        User u = MongoDB.getMongoApp().currentUser();
        MongoClient mongoClient = u.getMongoClient("mongodb-atlas");
        MongoDatabase mongoDatabase = mongoClient.getDatabase(DATABASE);
        return mongoDatabase.getCollection(COLLECTION);
    }

    MongoUserRepo() {
    }

}
