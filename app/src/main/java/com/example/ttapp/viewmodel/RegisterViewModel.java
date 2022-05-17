package com.example.ttapp.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.ttapp.database.Database;
import com.example.ttapp.database.DebugDB;
import com.example.ttapp.database.MongoDB;
import com.example.ttapp.database.Task;

/**
 * ViewModel for {@link com.example.ttapp.fragments.RegisterFragment}
 */
public class RegisterViewModel extends ViewModel {

    Database database;
    MutableLiveData<Boolean> identified;
    MutableLiveData<Boolean> databaseAccess;

    public void setDatabase(Database database) {
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
        if (identifier.equals("debug")) {
            Database.setConcreteDatabase(new DebugDB());
        }
        database.getDeviceIdTask(identifier, new Task() {
            @Override
            public void result(String deviceId) {
                if (!deviceId.isEmpty()) {
                    identified.setValue(true);
                } else {
                    identified.setValue(false);
                }
            }

            @Override
            public void error(String error) {
                databaseAccess.setValue(false);
            }
        });
    }
}
