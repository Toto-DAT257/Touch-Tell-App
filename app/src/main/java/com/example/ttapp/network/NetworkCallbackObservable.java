package com.example.ttapp.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.NetworkRequest;
import android.os.Build;

import java.util.ArrayList;
import java.util.List;

public class NetworkCallbackObservable {

    private static NetworkCallbackObservable instance;

    List<NetworkCallbackObserver> observers = new ArrayList<>();

    public void observe(NetworkCallbackObserver observer) {
        observers.add(observer);
    }

    public static NetworkCallbackObservable getInstance() {
        if (instance == null) {
            throw new IllegalStateException(NetworkCallbackObservable.class.getName() + " is not initialized.");
        }
        return instance;
    }

    public static void initialize(Context context) {
        if (instance == null) {
            instance = new NetworkCallbackObservable(context);
        }
    }

    private NetworkCallbackObservable(Context context) {
        ConnectivityManager.NetworkCallback networkCallback = new ConnectivityManager.NetworkCallback() {
            @Override
            public void onAvailable(Network network) {
                for (NetworkCallbackObserver observer : observers) {
                    observer.onNetworkAvailable();
                }
            }

            @Override
            public void onLost(Network network) {
                for (NetworkCallbackObserver observer : observers) {
                    observer.onNetworkLost();
                }
            }
        };

        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            connectivityManager.registerDefaultNetworkCallback(networkCallback);
        } else {
            NetworkRequest request = new NetworkRequest.Builder()
                    .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET).build();
            connectivityManager.registerNetworkCallback(request, networkCallback);
        }
    }

    public boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}
