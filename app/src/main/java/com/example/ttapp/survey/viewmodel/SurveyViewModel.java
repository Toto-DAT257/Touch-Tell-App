package com.example.ttapp.survey.viewmodel;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.ttapp.database.MongoDB;
import com.example.ttapp.survey.model.QuestionResponse;
import com.example.ttapp.survey.model.Survey;

import org.bson.Document;

import io.realm.mongodb.RealmResultTask;

/**
 * ViewModel for {@link com.example.ttapp.fragments.SurveyFragment}
 *
 * @author Simon Holst, Amanda Cyrén, Emma Stålberg
 */
public class SurveyViewModel extends ViewModel {

    private Survey survey;
    private final MutableLiveData<String> questionText;
    private final MutableLiveData<String> questionType;
    private final MutableLiveData<Boolean> jsonIsRecieved;

    public SurveyViewModel() {
        jsonIsRecieved = new MutableLiveData<>();
        questionText = new MutableLiveData<>();
        questionType = new MutableLiveData<>();
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
                    survey = new Survey(response.toString());
                    jsonIsRecieved.setValue(true);
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

    private String getAPILink(String deviceId) {
        return "https://api.touch-and-tell.se/checkin/" + deviceId;
    }

    private String getIdentifier(FragmentActivity activity) {
        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        return sharedPref.getString("identifier", null);
    }

    public void putAnswer(QuestionResponse response) {
        survey.putAnswer(getCurrentQuestionId(), response);
    }

    public void nextQuestion() {
        survey.nextQuestion();
        getCurrentQuestionText();
        getCurrentQuestionType();
    }

    public void previousQuestion() {
        survey.previousQuestion();
        getCurrentQuestionText();
        getCurrentQuestionType();
    }

    // Might be a possible solution for getting the
    public MutableLiveData<Boolean> getJsonIsRecievedIndicator() {
        return jsonIsRecieved;
    }

    public MutableLiveData<String> getCurrentQuestionText() {
        questionText.setValue(survey.getCurrentQuestionText());
        return questionText;
    }

    public MutableLiveData<String> getCurrentQuestionType() {
        questionType.setValue(survey.getCurrentQuestionType());
        return questionType;
    }


    public String getCurrentQuestionId() {
        return survey.getCurrentQuestionId();
    }

}
