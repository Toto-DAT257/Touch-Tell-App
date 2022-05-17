package com.example.ttapp.APIRequester;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.List;

/**
 * Singleton for making requests to Touch&Tell's API. Before usage the instance must be initialized.
 */
public class TTRequester implements IAPIRequester {

    private static final String TAG = "TTRequester";
    private static final String URL_PREFIX = "https://api.touch-and-tell.se/";
    private static final String URL_CHECK_IN_SUFFIX = "checkin/";
    private static final String URL_LOG_SUFFIX = "log";
    private static TTRequester instance;
    private static IResponseStorage<JSONObject> storage;
    private final RequestQueue requestQueue;


    private TTRequester(Context context) {
        requestQueue = Volley.newRequestQueue(context.getApplicationContext());
    }

    /**
     * Initializes the requester, enabling api calls to Touch&Tell.
     *
     * @param context The application context necessary for Volley.
     */
    public static void initialize(Context context) {
        if (instance == null) {
            instance = new TTRequester(context);
        }
    }

    public static void initialize(Context context, SharedPreferences sharedPreferences) {
        TTRequester.initialize(context);
        enableLocalStorage(sharedPreferences);
    }

    /**
     * Returns the requester-singleton given that it has been initialized.
     *
     * @return the requester-singleton
     */
    public static TTRequester getInstance() {
        if (instance == null)
            throw new IllegalStateException(TTRequester.class.getSimpleName() + " is not initialized," +
                    "call initialize(...) first");
        return instance;
    }

    /**
     * Enables saving of data locally if a submission fails.
     * @param sharedPreferences SharedPreference object to determine where the data should be saved.
     */
    public static void enableLocalStorage(SharedPreferences sharedPreferences) {
        storage = new PreferenceStorage(sharedPreferences);
    }

    /**
     * Makes a request for the survey for the given deviceId.
     *
     * @param deviceId the deviceId you want questions for.
     * @param response the response-object defined by the client, determining how to evaluate the
     *                 response.
     */
    @Override
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
     *
     * @param jsonObject the document to send.
     * @param saveLocallyOnFail whether the data should be stored locally if the request fails.
     */
    @Override
    public void submitResponse(JSONObject jsonObject, boolean saveLocallyOnFail) {
        JsonObjectRequest objectRequest = new JsonObjectRequest(
                Request.Method.POST,
                getSubmissionURL(),
                jsonObject,
                rest_response -> Log.v(TAG, rest_response.toString()),
                error -> {
                    Log.e(TAG, error.toString());
                    if (saveLocallyOnFail) {
                        saveResponse(jsonObject);
                    }
                }
        );
        requestQueue.add(objectRequest);
    }

    /**
     * Sends all locally saved data to Touch&Tell if possible. If successful the saved data will be
     * cleared.
     */
    @Override
    public void sendOldResponses() {
        if (storage == null) {
            Log.e("TAG", "Cannot send old responses since local save is not enabled.");
            return;
        }

        List<JSONObject> oldResponses = storage.getAllResponses();

        for (JSONObject oldResponse : oldResponses) {
            submitResponse(oldResponse, false, new Response<String>() {
                @Override
                public void response(String object) {
                    storage.removeResponse(oldResponse);
                }

                @Override
                public void error(String object) {
                }
            });
        }
    }

    /**
     * Submits a json-document to Touch&Tells API.
     *
     * @param jsonObject the document to send.
     * @param saveLocallyOnFail whether the data should be stored locally if the request fails.
     * @param response   the response object defined by the client, determining how to evaluate the
     *                   response.
     */
    @Override
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
                        saveResponse(jsonObject);
                    }
                }
        );
        requestQueue.add(objectRequest);
    }

    private String getSubmissionURL() {
        long unixTime = System.currentTimeMillis();
        return URL_PREFIX + URL_LOG_SUFFIX + "?cachekiller=" + unixTime;
    }

    private void saveResponse(JSONObject jsonObject) {
        if (storage == null) {
            Log.e("TAG", "Cannot save response since local save is not enabled.");
            return;
        }
        Log.e(TAG, "response could not be sent, saving response locally instead");
        storage.saveResponse(jsonObject);
    }
}
