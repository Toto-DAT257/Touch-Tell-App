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

public class MongoDB {

    private static final String APP_ID = "touchandtellmobile-zbhsu";
    private static App APP;
    private final IUserRepo userRepo;

    public static App getMongoApp() {
        if (APP == null) {
            APP = new App(new AppConfiguration.Builder(APP_ID)
                    .build());
        }
        return APP;
    }

    public MongoDB(Context context) {
        Realm.init(context);
        if (APP == null) {
            APP = new App(new AppConfiguration.Builder(APP_ID)
                    .build());
        }
        if (APP.currentUser() != null) {
            APP.loginAsync(Credentials.anonymous(), new App.Callback<User>() {
                @Override
                public void onResult(App.Result<User> result) {
                    if (result.isSuccess()) {
                        Log.v("LOGIN", "Successfully authenticated");
                    }
                    else {
                        Log.v("LOGIN", "Failed to log in");
                    }
                }
            });
        }
        userRepo = new MongoUserRepo();
    }

    public RealmResultTask<Document> getDeviceIdTask(String identifier) {
        return userRepo.getDeviceIdTask(identifier);
    }
}
