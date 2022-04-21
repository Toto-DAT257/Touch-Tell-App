package com.example.ttapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
import org.json.JSONObject;

import io.realm.mongodb.RealmResultTask;


public class HomeFragment extends Fragment {

    TextView textView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        textView = view.findViewById(R.id.text);
        getQuestions();
        return view;
    }

    private void getQuestions() {
        String identifier = getIdentifier();

        MongoDB db = MongoDB.getDatabase(getContext());
        RealmResultTask<Document> task = db.getDeviceIdTask(identifier);
        task.getAsync(result -> {
            if (result.isSuccess()) {
                if (result.get() != null) {
                    Log.e("DEVICE ID", result.get().toString());
                    // In here is were you have access to the deviceID, cannot return, due to async
                    String deviceId = result.get().get("deviceId").toString();
                    String API = getAPILink(deviceId);
                    requestFromAPI(API);

                } else {
                    Log.e("Database", "Identifier not found");
                }
            } else {
                Log.e("Database", "No access");
            }
        });
    }

    private void requestFromAPI(String API) {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        JsonObjectRequest objectRequest = new JsonObjectRequest(
                Request.Method.GET,
                API,
                null,
                response -> {
                    Log.e("Rest Response", response.toString());
                    // In here is were you have access to the JSON response, cannot return, due to async
                    String json = response.toString();
                    textView.setText(json);

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
        String sb = "https://api.touch-and-tell.se/checkin/" + deviceId;
        return sb;
    }

    private String getIdentifier() {
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        return sharedPref.getString("identifier", null);
    }
}