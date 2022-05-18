package com.example.ttapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;

import com.example.ttapp.APIRequester.APIRequester;
import com.example.ttapp.debug.DebugRequester;
import com.example.ttapp.APIRequester.TTRequester;
import com.example.ttapp.database.MongoDB;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ApplicationState.initializeComponentsRequiringActivity(this);
        ApplicationState.enterState(ApplicationState.State.PRODUCTION);
        APIRequester.getInstance().sendOldResponses();
    }

}