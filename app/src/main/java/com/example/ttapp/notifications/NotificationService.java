package com.example.ttapp.notifications;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;

public class NotificationService extends FirebaseMessagingService {

    @Override
    public void onNewToken(@NonNull String token) {
        Log.d("NOTIFICATION","Refreshed token: " + token);
        super.onNewToken(token);
    }

}
