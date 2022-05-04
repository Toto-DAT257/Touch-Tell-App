package com.example.ttapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Bundle;

import com.example.ttapp.APIRequester.TTRequester;
import com.example.ttapp.database.MongoDB;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TTRequester.initialize(this.getApplicationContext());
        MongoDB.initialize(this.getApplicationContext());
    }
}