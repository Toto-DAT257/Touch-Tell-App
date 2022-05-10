package com.example.ttapp.APIRequester;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.ttapp.survey.model.Survey;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Singleton for making requests to Touch&Tell's API. Before usage the instance must be initialized.
 */
public class TTRequester {

    private static final String TAG = "TTRequester";
    private static final String URL_PREFIX = "https://api.touch-and-tell.se/";
    private static final String URL_CHECK_IN_SUFFIX = "checkin/";
    private static final String URL_LOG_SUFFIX = "log";
    private static final String RESPONSE_SAVE_PREFIX = "saved-response-number-";

    private static TTRequester instance = null;
    private RequestQueue requestQueue;
    private static SharedPreferences sharedPreferences = null;


    private TTRequester() {}

    private TTRequester(Context context) {
        requestQueue = Volley.newRequestQueue(context.getApplicationContext());
    }

    /**
     * Initializes the requester, enabling api calls to Touch&Tell.
     * @param context The application context necessary for Volley.
     * @return returns the requester-singleton.
     */
    public static synchronized TTRequester initialize(Context context) {
        if (instance == null) {
            instance = new TTRequester(context);
        }
        return instance;
    }

    public static synchronized void enableLocalSave(SharedPreferences sharedPreferences) {
        if (instance == null)
            throw new IllegalStateException(TTRequester.class.getSimpleName() + " is not initialized," +
                    "call initialize(...) first");
        TTRequester.sharedPreferences = sharedPreferences;
    }

    /**
     * Returns the requester-singleton given that it has been initialized.
     * @return the requester-singleton
     */
    public static synchronized TTRequester getInstance() {
        if (instance == null)
            throw new IllegalStateException(TTRequester.class.getSimpleName() + " is not initialized," +
                    "call initialize(...) first");
        return instance;
    }

    /**
     * Makes a request for the survey for the given deviceId.
     * @param deviceId the deviceId you want questions for.
     * @param response the response-object defined by the client, determining how to evaluate the
     *                 response.
     */
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

    /**
     * Submits a json-document to Touch&Tells API.
     * @param jsonObject the document to send.
     */
    public void submitResponse(JSONObject jsonObject, boolean saveLocallyOnFail) {
        JsonObjectRequest objectRequest = new JsonObjectRequest(
                Request.Method.POST,
                getSubmissionURL(),
                jsonObject,
                rest_response -> Log.v(TAG, rest_response.toString()),
                error -> {
                    Log.e(TAG, error.toString());
                    if (saveLocallyOnFail) {
                        onResponseFail(jsonObject);
                    }
                }
        );
        requestQueue.add(objectRequest);
    }


    public void sendOldResponses() {

        if (sharedPreferences == null) {
            Log.e("TAG", "Cannot send old responses since local save is not enabled.");
            return;
        }

        List<JSONObject> oldResponses = getOldResponsesToSend();
        int index = 1;

        for (JSONObject oldResponse : oldResponses) {

            int finalIndex = index;
            submitResponse(oldResponse, false, new Response<String>() {
                @Override
                public void response(String object) {
                    removeOldResponse(finalIndex);
                }

                @Override
                public void error(String object) {

                }
            });
            index++;
        }
    }

    /**
     * Submits a json-document to Touch&Tells API.
     * @param jsonObject the document to send.
     * @param response the response object defined by the client, determining how to evaluate the
     *                 response.
     */
    public void submitResponse(JSONObject jsonObject, boolean saveLocallyOnFail, final Response<String> response) {

        JsonObjectRequest objectRequest = new JsonObjectRequest(
                Request.Method.POST,
                getSubmissionURL(),
                jsonObject,
                rest_response -> {
                    response.response(rest_response.toString());
                    Log.v(TAG, rest_response.toString());
                },
                error -> {
                    response.error(error.toString());
                    Log.e(TAG, error.toString());
                    if (saveLocallyOnFail) {
                        onResponseFail(jsonObject);
                    }
                }
        );
        requestQueue.add(objectRequest);
    }

    private String getSubmissionURL() {
        long unixTime = System.currentTimeMillis();
        return URL_PREFIX + URL_LOG_SUFFIX + "?cachekiller=" + unixTime;
    }

    private void saveResponse(String response) {
        if (sharedPreferences == null) {
            Log.e(TAG, "Cannot save response; local save is not enabled");
            return;
        }

        int responseSaveSuffix = 1;
        String JSONString = sharedPreferences.getString(RESPONSE_SAVE_PREFIX + responseSaveSuffix, "");

        while (!JSONString.isEmpty()) {
            responseSaveSuffix++;
            JSONString = sharedPreferences.getString(RESPONSE_SAVE_PREFIX + responseSaveSuffix, "");
        }
        sharedPreferences.edit().putString(RESPONSE_SAVE_PREFIX + responseSaveSuffix, response).apply();
    }

    private void removeOldResponse(int index) {
        sharedPreferences.edit().remove(RESPONSE_SAVE_PREFIX + index).apply();
    }

    private List<JSONObject> getOldResponsesToSend() {
        List<JSONObject> oldResponses = new ArrayList<>();
        int responseSaveSuffix = 1;
        String JSONString = sharedPreferences.getString(RESPONSE_SAVE_PREFIX + responseSaveSuffix, "");

        while (!JSONString.isEmpty()) {
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(JSONString);
                oldResponses.add(jsonObject);
            } catch (JSONException e) {
                Log.e(TAG, "Couldn't serialize saved json response");
            }
            responseSaveSuffix++;
            JSONString = sharedPreferences.getString(RESPONSE_SAVE_PREFIX + responseSaveSuffix, "");
        }

        return oldResponses;
    }

    private void onResponseFail(JSONObject jsonObject) {
        Log.e(TAG, "response was not sent, saving response in preferences");
        saveResponse(jsonObject.toString());
    }
}
