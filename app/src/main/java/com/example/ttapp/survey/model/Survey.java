package com.example.ttapp.survey.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.ttapp.database.MongoDB;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.bson.Document;

import java.util.ArrayList;
import java.util.Map;

import io.realm.mongodb.RealmResultTask;

/**
 * Class for a survey, fetching and storing the information about the questions for the currently logged in user.
 *
 * @author Simon Holst, Amanda Cyrén, Emma Stålberg
 */
public class Survey {

    private String json;
    // TODO: create JsonQuestionParser class instance here
    private ArrayList<String> questionsToSend;
    private String currentQuestion;
    // TODO: create Map<String, Answer> when the Answer interface is created

    public Survey(String json) {
        this.json = json;
        JsonObject convertedObject = new Gson().fromJson(json, JsonObject.class);
        JsonArray questions = convertedObject.getAsJsonArray("questions");
        JsonObject q1 = questions.get(0).getAsJsonObject();
        currentQuestion = q1.getAsJsonArray("text").get(0).getAsJsonObject().get("text").getAsString();
        // String type1 = q1.get("type").getAsString();
        questionsToSend = new ArrayList<>();
    }

    /**
     * Loads the questions from the Touch&Tell API for the logged in user.
     * @param context the fragment that will present the questions context.
     * @param activity the fragment that will present the questions context.
     */
    public void loadQuestions(Context context, FragmentActivity activity) {
        String identifier = getIdentifier(activity);

        MongoDB db = MongoDB.getDatabase(context);
        RealmResultTask<Document> task = db.getDeviceIdTask(identifier);
        task.getAsync(result -> {
            if (result.isSuccess()) {
                if (result.get() != null) {
                    Log.i("DEVICE ID", result.get().toString());
                    // In here is where you have access to the deviceID. Cannot return, due to async
                    String deviceId = result.get().get("deviceId").toString();
                    String API = getAPILink(deviceId);
                    requestFromAPI(API, activity);

                } else {
                    Log.e("Database", "Identifier not found");
                }
            } else {
                Log.e("Database", "No access");
            }
        });
    }

    private void requestFromAPI(String API, Context activity) {
        RequestQueue requestQueue = Volley.newRequestQueue(activity);

        JsonObjectRequest objectRequest = new JsonObjectRequest(
                Request.Method.GET,
                API,
                null,
                response -> {
                    Log.i("Rest Response", response.toString());
                    // In here is were you have access to the JSON response, cannot return, due to async
                    json = response.toString();
                    //textView.setText(json);

                    // Not used now, can be good to se how to query JSON in the future
                    /*
                    JsonObject convertedObject = new Gson().fromJson(json, JsonObject.class);
                    JsonArray questions = convertedObject.getAsJsonArray("questions");
                    JsonObject q1 = questions.get(0).getAsJsonObject();
                    String text = q1.getAsJsonArray("text").get(0).getAsJsonObject().get("text").getAsString();
                    String type1 = q1.get("type").getAsString();
                    */

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Rest Response", error.toString());
                    }
                }
        );

        requestQueue.add(objectRequest);
    }

    @NonNull
    private String getAPILink(String deviceId) {
        return "https://api.touch-and-tell.se/checkin/" + deviceId;
    }

    private String getIdentifier(FragmentActivity activity) {
        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        return sharedPref.getString("identifier", null);
    }

    // Might be a possible solution for getting the questions
    public String getQuestions() {
        return json;
    }

    public String getCurrentQuestion() {
        return currentQuestion;
    }
}
