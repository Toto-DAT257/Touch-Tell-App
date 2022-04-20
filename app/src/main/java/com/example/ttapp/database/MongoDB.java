package com.example.ttapp.database;

import android.content.Context;
import android.util.Log;

import org.bson.Document;

import io.realm.Realm;
import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.Credentials;
import io.realm.mongodb.RealmResultTask;


/**
 * Singleton class for communicating with the database. This class should be used by client to
 * communicate with the database.
 * <p>
 * "Get" methods will return a {@link RealmResultTask} of the object rather than the object itself.
 * They need to be used asynchronously.
 */
public class MongoDB {

    private static final String APP_ID = "touchandtellmobile-zbhsu";
    private static App APP;
    private static MongoDB singleton;
    private static IUserRepo userRepo;

    /**
     * Gets the app for the database
     *
     * @return the app
     */
    public static App getMongoApp() {
        if (APP == null) {
            APP = new App(new AppConfiguration.Builder(APP_ID)
                    .build());
        }
        return APP;
    }

    /**
     * Gets the singleton of the database. If the database is not yet initialized it will initialize
     * it and then return the ready to use database.
     *
     * @param context required to initialize the Mongo Realm.
     *                Example:
     *                From an activity use 'this'
     *                From a fragment use 'getActivity()' or 'getContext()'
     * @return the database
     */
    public static MongoDB getDatabase(Context context) {
        if (singleton == null) {
            singleton = new MongoDB(context);
        }
        return singleton;
    }

    public static void assertLoggedIn() {
        assertAppNotNull();
        if (APP.currentUser().isLoggedIn()) {
            APP.loginAsync(Credentials.anonymous(), result -> {
                if (result.isSuccess()) {
                    Log.v("LOGIN", "Successfully authenticated");
                } else {
                    Log.v("LOGIN", "Failed to log in");
                }
            });
        }
        else {
            Log.v("LOGIN", "APP was unexpectedly null");
        }
    }

    private static void assertAppNotNull() {
        if (APP == null) {
            APP = new App(new AppConfiguration.Builder(APP_ID)
                    .build());
        }
        else {
            Log.v("DATABASE", "App was not null");
        }
    }

    private MongoDB(Context context) {
        Realm.init(context);
        if (APP == null) {
            APP = new App(new AppConfiguration.Builder(APP_ID)
                    .build());
        }
        if (!APP.currentUser().isLoggedIn()) {
            APP.loginAsync(Credentials.anonymous(), result -> {
                if (result.isSuccess()) {
                    Log.v("LOGIN", "Successfully authenticated");
                } else {
                    Log.v("LOGIN", "Failed to log in");
                }
            });
        }
        userRepo = new MongoUserRepo();
    }

    /**
     * Returns the task for finding the device id necessary to collect questions.
     *
     * @param identifier the unique identifier associated with the user.
     * @return returns task to find device id.
     */
    public RealmResultTask<Document> getDeviceIdTask(String identifier) {
        //assertLoggedIn();
        return userRepo.getDeviceIdTask(identifier);
    }
}
