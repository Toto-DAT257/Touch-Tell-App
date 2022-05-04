package com.example.ttapp.database;

import android.content.Context;
import android.util.Log;

import com.example.ttapp.APIRequester.TTRequester;

import org.bson.Document;

import java.util.Arrays;

import io.realm.Realm;
import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.Credentials;
import io.realm.mongodb.RealmResultTask;


/**
 * Singleton class for communicating with the database. This class should be used by clients to
 * communicate with the Mongo Realm database.
 * <p>
 * "Get" methods will return a {@link RealmResultTask} of the object rather than the object itself.
 * They need to be used asynchronously.
 */
public class MongoDB {

    private static final String APP_ID = "touchandtellmobile-zbhsu";
    private static App APP;
    private static MongoDB instance;
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
     * Gets the database instance.
     * @return the database instance.
     */
    public static MongoDB getInstance() {
        if (instance == null)
            throw new IllegalStateException(MongoDB.class.getSimpleName() + " is not initialized," +
                    "call initialize(...) first");
        return instance;
    }

    /**
     * Initializes the database. This is required before using the database.
     *
     * @param context required to initialize the Mongo Realm.
     *                Example:
     *                From an activity use 'this'
     *                From a fragment use 'getActivity()' or 'getContext()'
     * @return the database
     */
    public static MongoDB initialize(Context context) {
        if (instance == null) {
            instance = new MongoDB(context);
        }
        return instance;
    }

    public static void assertLoggedIn() {
        assertAppNotNull();
        if (APP.currentUser() == null) {
            Log.v("LOGIN", "User was null, trying to log in");
            APP.login(Credentials.anonymous());
        } else {
            Log.v("LOGIN", "User was logged in:" + APP.currentUser().getId());
        }
    }

    private static void assertAppNotNull() {
        if (APP == null) {
            APP = new App(new AppConfiguration.Builder(APP_ID)
                    .build());
        } else {
            Log.v("DATABASE", "App was not null");
        }
    }

    private MongoDB(Context context) {
        Realm.init(context);
        Thread t = new Thread() {
            public void run() {
                assertAppNotNull();
                assertLoggedIn();
            }
        };
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            Log.v("THREAD", "Thread was interrupted");
        }
        userRepo = new MongoUserRepo();
    }

    /**
     * Used to ensure that the user is logged in and can access the database.
     * If that is not the case it will run a new thread trying to log the user in before continuing.
     */
    public static void assertDatabaseAccess() {
        boolean access = false;
        try {
            if (APP.currentUser().isLoggedIn()) {
                access = true;
            }
        } catch (NullPointerException e) {
            Log.v("DATABASE-ACCESS:", "Either realm-app was or current user was null. See stacktrace" + Arrays.toString(e.getStackTrace()));
        }
        if (!access) {
            Thread t = new Thread() {
                public void run() {
                    assertAppNotNull();
                    assertLoggedIn();
                }
            };
            t.start();
            try {
                t.join();
            } catch (InterruptedException e) {
                Log.v("THREAD", "Thread was interrupted");
            }
        }
    }

    /**
     * Returns the task for finding the device id necessary to collect questions.
     *
     * @param identifier the unique identifier associated with the user.
     * @return returns task to find device id.
     */
    public RealmResultTask<Document> getDeviceIdTask(String identifier) {
        return userRepo.getDeviceIdTask(identifier);
    }
}
