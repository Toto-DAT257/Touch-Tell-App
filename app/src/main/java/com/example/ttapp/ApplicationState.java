package com.example.ttapp;

import com.example.ttapp.APIRequester.APIRequester;
import com.example.ttapp.APIRequester.DebugRequester;
import com.example.ttapp.APIRequester.TTRequester;
import com.example.ttapp.database.Database;
import com.example.ttapp.database.DebugDB;
import com.example.ttapp.database.MongoDB;

public class ApplicationState {

    public enum State {
        DEBUG,
        PRODUCTION
    }

    public static void enterState(State state) {
        switch (state) {
            case DEBUG:
                Database.setConcreteDatabase(new DebugDB());
                APIRequester.setConcreteAPIRequester(new DebugRequester());
                break;
            case PRODUCTION:
                Database.setConcreteDatabase(MongoDB.getInstance());
                APIRequester.setConcreteAPIRequester(TTRequester.getInstance());
                break;
        }
    }

    public static void enterStateByIdentifier(String identifier) {
        if (identifier.equalsIgnoreCase("debug")) {
            enterState(State.DEBUG);
        } else {
            enterState(State.PRODUCTION);
        }
    }
}
