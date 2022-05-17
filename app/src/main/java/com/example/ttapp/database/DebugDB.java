package com.example.ttapp.database;

import android.util.Log;

public class DebugDB implements ConcreteDatabase {

    @Override
    public void getDeviceIdTask(String identifier, Task task) {
        task.result("debug");
        Log.v("LOGIN", "Identified as debug");
    }

}
