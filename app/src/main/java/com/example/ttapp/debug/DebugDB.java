package com.example.ttapp.debug;

import android.util.Log;

import com.example.ttapp.database.IDatabase;
import com.example.ttapp.database.Task;

public class DebugDB implements IDatabase {

    @Override
    public void getDeviceIdTask(String identifier, Task task) {
        task.result("debug");
        Log.v("LOGIN", "Identified as debug");
    }

}
