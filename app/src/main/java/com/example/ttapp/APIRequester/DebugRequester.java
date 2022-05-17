package com.example.ttapp.APIRequester;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

public class DebugRequester implements IAPIRequester {

    private static final String TAG = "DebugRequester";
    private static DebugRequester instance;
    private static Context context;

    private DebugRequester(Context appContext) {
        context = appContext.getApplicationContext();
    }

    public static void initialize(Context context) {
        if (instance == null) {
            instance = new DebugRequester(context);
        }
    }

    public static DebugRequester getInstance() {
        if (instance == null)
            throw new IllegalStateException(DebugRequester.class.getSimpleName() + " is not initialized," +
                    "call initialize(...) first");
        return instance;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void requestQuestionJSONString(String deviceId, Response<String> response) {
        String surveyJSON = loadJSONFromAsset();
        response.response(surveyJSON);
    }

    private String loadJSONFromAsset() {
        String json;
        try {
            InputStream is = context.getAssets().open("debug-questions.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            Log.v(TAG, "Could not find JSON file with survey questions"
                    + "\n"
                    + Arrays.toString(ex.getStackTrace()));
            return null;
        }
        return json;
    }

    @Override
    public void submitResponse(JSONObject jsonObject, boolean saveLocallyOnFail) {

    }

    @Override
    public void sendOldResponses() {

    }

    @Override
    public void submitResponse(JSONObject jsonObject, boolean saveLocallyOnFail, Response<String> response) {

    }
}
