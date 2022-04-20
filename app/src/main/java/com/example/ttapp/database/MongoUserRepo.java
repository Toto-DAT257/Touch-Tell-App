package com.example.ttapp.database;

import org.bson.Document;
import io.realm.mongodb.RealmResultTask;
import io.realm.mongodb.User;
import io.realm.mongodb.mongo.MongoClient;
import io.realm.mongodb.mongo.MongoCollection;
import io.realm.mongodb.mongo.MongoDatabase;


public class MongoUserRepo implements IUserRepo {

    private static final String DATABASE = "TouchAndTell";
    private static final String COLLECTION = "Users";

    @Override
    public RealmResultTask<Document> getDeviceIdTask(String identifier) {
        MongoCollection<Document> userCollection = getCollection();
        return getDeviceId(identifier, userCollection);
    }

    private RealmResultTask<Document> getDeviceId(String identifier, MongoCollection<Document> userCollection) {
        Document filter = new Document().append("mail", identifier);
        return userCollection.findOne(filter);
    }

    private MongoCollection<Document> getCollection() {
        User u = MongoDB.getMongoApp().currentUser();
        MongoClient mongoClient = u.getMongoClient("mongodb-atlas");
        MongoDatabase mongoDatabase = mongoClient.getDatabase(DATABASE);
        MongoCollection<Document> userCollection = mongoDatabase.getCollection(COLLECTION);
        return userCollection;
    }



}
