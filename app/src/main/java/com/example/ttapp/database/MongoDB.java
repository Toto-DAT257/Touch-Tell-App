package com.example.ttapp.database;

import android.content.Context;
import android.util.Log;

import org.bson.Document;

import java.util.Arrays;

import io.realm.Realm;
import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.Credentials;
import io.realm.mongodb.RealmResultTask;
import io.realm.mongodb.User;


/**
 * Singleton class for communicating with the database. This class should be used by clients to
 * communicate with the Mongo Realm database.
 * <p>
 * "Get" methods will return a {@link RealmResultTask} of the object rather than the object itself.
 * They need to be used asynchronously.
 */
public class MongoDB implements IDatabase {

    private static final String APP_ID = "touchandtellmobile-zbhsu";
    private static App APP;
    private static MongoDB instance;
    private final IUserRepo userRepo;

    /**
     * Gets the app for the database
     *
     * @return the app
     */
    protected static App getMongoApp() {
        if (APP == null) {
            APP = new App(new AppConfiguration.Builder(APP_ID)
                    .build());
        }
        return APP;
    }

    public static MongoDB getInstance() {
        assertInitialized();
        return instance;
    }

    public static void initialize(Context context) {
        instance = new MongoDB(context);
    }

    private static User getUser() {
        return getMongoApp().currentUser();
    }

    private static void assertLoggedIn() {
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

    private static void assertInitialized() {
        if (instance == null) {
            throw new IllegalStateException(MongoDB.class.getSimpleName() + " is not initialized," +
                    "call initialize(...) first");
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
    protected static void assertDatabaseAccess() {
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

    public void getDeviceIdTask(String identifier, final Task task) {
        userRepo.getDeviceIdRealmTask(identifier).getAsync(result -> {
            if (result.isSuccess()) {
                if (result.get() != null) {
                    String deviceId = result.get().get("deviceId").toString();
                    task.result(deviceId);
                    Log.v("LOGIN", "Identifier " + deviceId);
                } else {
                    task.result("");
                    Log.v("LOGIN", "Identifier not registered");
                }
            } else {
                task.error("No database access");
                Log.v("LOGIN", "Database access failed." + result.getError().toString());
                MongoDB.getUser().logOutAsync(result1 -> Log.v("LOGOUT:", String.valueOf(result1.isSuccess())));
            }
        });
    }
}
