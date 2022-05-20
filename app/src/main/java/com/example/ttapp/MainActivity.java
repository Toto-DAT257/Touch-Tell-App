package com.example.ttapp;

import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ttapp.APIRequester.APIRequester;
import com.example.ttapp.debug.DebugRequester;
import com.example.ttapp.APIRequester.TTRequester;
import com.example.ttapp.database.MongoDB;
import com.example.ttapp.network.NetworkCallbackObservable;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ApplicationState.initializeComponentsRequiringActivity(this);
        ApplicationState.enterStateByIdentifier(this.getPreferences(Context.MODE_PRIVATE));
        APIRequester.getInstance().sendOldResponses();
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