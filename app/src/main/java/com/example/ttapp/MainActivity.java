package com.example.ttapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;

import com.example.ttapp.APIRequester.TTRequester;
import com.example.ttapp.database.Database;
import com.example.ttapp.database.DebugDB;
import com.example.ttapp.database.MongoDB;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TTRequester.initialize(this.getApplicationContext());
        TTRequester.enableLocalStorage(this.getPreferences(Context.MODE_PRIVATE));
        TTRequester.getInstance().sendOldResponses();
        Database.initialize(new MongoDB(this.getApplicationContext()));
    }

}