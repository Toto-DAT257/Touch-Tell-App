package com.example.ttapp.APIRequester;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.ttapp.survey.model.Survey;

import org.json.JSONObject;

public class TTRequester {

    private static final String TAG = "TTRequester";
    private static final String URL_PREFIX = "https://api.touch-and-tell.se/";
    private static final String URL_CHECK_IN_SUFFIX = "checkin/";
    private static final String URL_LOG_SUFFIX = "log";

    private static TTRequester instance = null;
    private RequestQueue requestQueue;


    private TTRequester() {}

    private TTRequester(Context context) {
        requestQueue = Volley.newRequestQueue(context.getApplicationContext());
    }


    public static synchronized TTRequester initialize(Context context) {
        if (instance == null) {
            instance = new TTRequester(context);
        }
        return instance;
    }

    public static synchronized TTRequester getInstance() {
        if (instance == null)
            throw new IllegalStateException(TTRequester.class.getSimpleName() + " is not initialized," +
                    "call initialize(...) first");
        return instance;
    }

    public void requestQuestionJSONString(String deviceId, final Response<String> response) {
        long unixTime = System.currentTimeMillis();
        String URL = URL_PREFIX + URL_CHECK_IN_SUFFIX + deviceId + "/0.0.0/?nocache=" + unixTime;

        JsonObjectRequest objectRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,
                JSONResponse -> response.response(JSONResponse.toString()),
                error -> {
                    Log.e(TAG, error.toString());
                    response.error(error.toString());
                }
        );
        requestQueue.add(objectRequest);
    }

    public void submitResponse(JSONObject jsonObject) {
        long unixTime = System.currentTimeMillis();
        String URL = URL_PREFIX + URL_LOG_SUFFIX + "?cachekiller=" + unixTime;

        JsonObjectRequest objectRequest = new JsonObjectRequest(
                Request.Method.POST,
                URL,
                jsonObject,
                rest_response -> Log.v(TAG, rest_response.toString()),
                error -> Log.e(TAG, error.toString())
        );
        requestQueue.add(objectRequest);
    }

    public void submitResponse(JSONObject jsonObject, final Response<String> response) {
        long unixTime = System.currentTimeMillis();
        String URL = URL_PREFIX + URL_LOG_SUFFIX + "?cachekiller=" + unixTime;

        JsonObjectRequest objectRequest = new JsonObjectRequest(
                Request.Method.POST,
                URL,
                jsonObject,
                rest_response -> {
                    response.response(rest_response.toString());
                    Log.v(TAG, rest_response.toString());
                },
                error -> {
                    response.error(error.toString());
                    Log.e(TAG, error.toString());
                }
        );
        requestQueue.add(objectRequest);
    }
}
