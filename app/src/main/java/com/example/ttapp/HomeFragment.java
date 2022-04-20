package com.example.ttapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

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
        extracted();
        return view;
    }
    private void extracted() {
        String identifier = getIdentifier();
        MongoDB db = MongoDB.getDatabase(getContext());
        RealmResultTask<Document> task = db.getDeviceIdTask(identifier);
        task.getAsync(result -> {
            if (result.isSuccess()){
                if (result.get() != null){
                    String deviceId = result.get().get("deviceId").toString();
                    Log.e("LOGIN", "Identifier " + result.get().toString());

                    StringBuilder sb = new StringBuilder();
                    sb.append("https://api.touch-and-tell.se/checkin/");
                    sb.append(deviceId);
                    String API = sb.toString();

                    RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

                    JsonObjectRequest objectRequest = new JsonObjectRequest(
                            Request.Method.GET,
                            API,
                            null,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    Log.e("Rest Response", response.toString());
                                    String json = response.toString();
                                    textView.setText(json);

                                    JsonObject convertedObject = new Gson().fromJson(json, JsonObject.class);
                                    JsonArray questions = convertedObject.getAsJsonArray("questions");
                                    JsonObject q1 = questions.get(0).getAsJsonObject();
                                    String text = q1.getAsJsonArray("text").get(0).getAsJsonObject().get("text").getAsString();
                                    String type1 = q1.get("type").getAsString();
                                    //question.setText(text);
                                    //type.setText(type1);
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Log.e("Rest Response", error.toString());
                                }
                            }
                    );

                    requestQueue.add(objectRequest);
                } else {
                    Log.e("Null", "Null");
                }
            } else {
                Log.e("Database", "Not success");
            }
        });
    }

    private String getIdentifier() {
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        return sharedPref.getString("identifier", null);
    }
}