package com.example.ttapp;

import android.app.Activity;
import android.content.Context;

import com.example.ttapp.APIRequester.APIRequester;
import com.example.ttapp.debug.DebugRequester;
import com.example.ttapp.APIRequester.TTRequester;
import com.example.ttapp.database.Database;
import com.example.ttapp.debug.DebugDB;
import com.example.ttapp.database.MongoDB;

/**
 * Class for handling the application state.
 */
public class ApplicationState {

    public enum State {
        DEBUG,
        PRODUCTION
    }

    /**
     * Makes necessary changes to the backend depending on the state supplied.
     *
     * @param state the state that application should enter.
     */
    public static void enterState(State state) {
        switch (state) {
            case DEBUG:
                Database.setConcreteDatabase(new DebugDB());
                APIRequester.setConcreteAPIRequester(DebugRequester.getInstance());
                break;
            case PRODUCTION:
                Database.setConcreteDatabase(MongoDB.getInstance());
                APIRequester.setConcreteAPIRequester(TTRequester.getInstance());
                break;
        }
    }

    /**
     * Enters an application state determined by the identification of the user.
     *
     * @param identifier the identifier for the user.
     */
    public static void enterStateByIdentifier(String identifier) {
        if (identifier.equalsIgnoreCase("debug")) {
            enterState(State.DEBUG);
        } else {
            enterState(State.PRODUCTION);
        }
    }

    /**
     * Supplies the backend with the necessary context.
     *
     * @param activity the activity that the backend should attach to.
     */
    public static void initializeComponentsRequiringActivity(Activity activity) {
        TTRequester.initialize(
                activity.getApplicationContext(),
                activity.getPreferences(Context.MODE_PRIVATE));
        DebugRequester.initialize(activity.getApplicationContext());
        MongoDB.initialize(activity.getApplicationContext());
    }
}
