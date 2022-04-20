package com.example.ttapp.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.ttapp.database.MongoDB;

import org.bson.Document;


import io.realm.mongodb.RealmResultTask;

/**
 * ViewModel for {@link com.example.ttapp.fragments.RegisterFragment}
 */
public class RegisterViewModel extends ViewModel {

    MongoDB database;
    MutableLiveData<Boolean> identified;
    MutableLiveData<Boolean> databaseAccess;

    public void setDatabase(MongoDB database) {
        this.database = database;
    }

    public RegisterViewModel() {
        identified = new MutableLiveData<>();
        databaseAccess = new MutableLiveData<>();
    }

    public MutableLiveData<Boolean> getIdentified() {
        return identified;
    }

    public MutableLiveData<Boolean> getDatabaseAccess() {
        return databaseAccess;
    }

    public void identify(String identifier) {
        RealmResultTask<Document> task = database.getDeviceIdTask(identifier);
        task.getAsync(result -> {
            if (result.isSuccess()) {
                if (result.get() != null) {
                    identified.setValue(true);
                    Log.v("LOGIN", "Identifier " + result.get().toString());
                } else {
                    identified.setValue(false);
                    Log.v("LOGIN", "Identifier not registered");
                }
            } else {
                Log.v("LOGIN", "Database access failed." + result.getError().toString());
                MongoDB.getMongoApp().currentUser().logOutAsync(result1 -> Log.v("LOGOUT:", String.valueOf(result1.isSuccess())));
                databaseAccess.setValue(false);
            }
        });
    }
}
