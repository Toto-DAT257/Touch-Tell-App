package com.example.ttapp;

import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ttapp.APIRequester.TTRequester;
import com.example.ttapp.database.MongoDB;
import com.example.ttapp.network.NetworkCallbackObservable;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TTRequester.initialize(this.getApplicationContext());
        TTRequester.enableLocalStorage(this.getPreferences(Context.MODE_PRIVATE));
        TTRequester.getInstance().sendOldResponses();
        MongoDB.initialize(this.getApplicationContext());
        NetworkCallbackObservable.initialize(this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (getCurrentFocus() == null) {
            return true;
        }
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        return true;
    }

}