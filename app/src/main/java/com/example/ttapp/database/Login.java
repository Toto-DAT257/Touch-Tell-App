package com.example.ttapp.database;

import android.content.Context;
import android.util.Log;

import org.bson.Document;

import io.realm.Realm;
import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.Credentials;
import io.realm.mongodb.RealmResultTask;
import io.realm.mongodb.User;
import io.realm.mongodb.mongo.MongoClient;
import io.realm.mongodb.mongo.MongoCollection;
import io.realm.mongodb.mongo.MongoDatabase;
import io.realm.mongodb.mongo.iterable.MongoCursor;

public class Login {

    public void login(Context context) {
        Realm.init(context);
        App app = new App(new AppConfiguration.Builder("touchandtellmobile-zbhsu")
                .build());

        app.loginAsync(Credentials.anonymous(), new App.Callback<User>() {
            @Override
            public void onResult(App.Result<User> result) {
                if (result.isSuccess()) {
                    Log.v("QUICKSTART", "Successfully authenticated");
                }
                else {
                    Log.v("QUICKSTART", "Failed to log in");
                }
            }
        });
        User u = app.currentUser();
        System.out.println(u.getId());
        System.out.println(u.isLoggedIn());

        MongoClient mongoClient = u.getMongoClient("mongodb-atlas");
        MongoDatabase mongoDatabase = mongoClient.getDatabase("TouchAndTell");
        MongoCollection<Document> mongoCollection = mongoDatabase.getCollection("Users");

        Document filter = new Document().append("mail", "toto1@touchandtell.se");
        RealmResultTask<MongoCursor<Document>> find = mongoCollection.find(filter).iterator();
        find.getAsync(result -> {
            if (result.isSuccess()) {
                for (MongoCursor<Document> it = result.get(); it.hasNext(); ) {
                    Document res = it.next();
                    Log.v("Documents found:", res.toString());
                }
            }
            else {
                Log.v("Documents found:", "none. Result was not success");
            }
        });
    }

}
