package com.example.ttapp.survey.viewmodel;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.ttapp.database.MongoDB;
import com.example.ttapp.survey.fragments.SurveyFragment;
import com.example.ttapp.survey.model.QuestionResponse;
import com.example.ttapp.survey.model.Survey;
import com.example.ttapp.survey.model.SurveyEvent;

import org.bson.Document;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import java.util.ArrayList;

import io.realm.mongodb.RealmResultTask;

/**
 * ViewModel for {@link SurveyFragment} and all types of question fragments
 * <p>
 * This class makes use of the Java Beans PropertyChange-library which lets it subscribe to other
 * objects and listen to their events.
 */
public class SurveyViewModel extends ViewModel implements PropertyChangeListener {

    private Survey survey;
    private final MutableLiveData<String> questionText;
    private final MutableLiveData<String> questionType;
    private final MutableLiveData<Boolean> jsonIsReceived;
    private final MutableLiveData<Boolean> surveyIsDone;
    private final MutableLiveData<Boolean> saveResponse;
    private final MutableLiveData<Boolean> isLastQuestion;

    public SurveyViewModel() {
        jsonIsReceived = new MutableLiveData<>();
        questionText = new MutableLiveData<>();
        questionType = new MutableLiveData<>();
        surveyIsDone = new MutableLiveData<>();
        saveResponse = new MutableLiveData<>();
        isLastQuestion = new MutableLiveData<>();
    }

    /**
     * Loads the questions from the Touch&Tell API for the logged in user.
     *
     * @param context  the fragment that will present the questions context.
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

                    requestFromAPI(activity, deviceId, identifier);

                } else {
                    Log.e("Database", "Identifier not found");
                }
            } else {
                Log.e("Database", "No access");
            }
        });
    }

    private void requestFromAPI(Context activity, String deviceId, String identifier) {
        String API = getAPILink(deviceId);
        RequestQueue requestQueue = Volley.newRequestQueue(activity);

        JsonObjectRequest objectRequest = new JsonObjectRequest(
                Request.Method.GET,
                API,
                null,
                response -> {
                    Log.i("Rest Response", response.toString());
                    // In here is were you have access to the JSON response, cannot return, due to async
                    survey = new Survey(response.toString(), deviceId, identifier);
                    survey.addPropertyChangeListener(this);
                    questionType.setValue(survey.getCurrentQuestionType());
                    questionText.setValue(survey.getCurrentQuestionText());
                    jsonIsReceived.setValue(true);
                },
                error -> Log.e("Rest Response", error.toString())
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

    public void nextQuestion() {
        survey.nextQuestion();
    }

    public void previousQuestion() {
        survey.previousQuestion();
    }

    public LiveData<Boolean> getJsonIsReceivedIndicator() {
        return jsonIsReceived;
    }

    public LiveData<String> newQuestionText() {
        return questionText;
    }

    public LiveData<String> newQuestionType() {
        return questionType;
    }

    public LiveData<Boolean> surveyIsDone() {
        return surveyIsDone;
    }

    public LiveData<Boolean> isLastQuestion() {
        isLastQuestion.setValue(true);
        return isLastQuestion;
    }

    @Override
    public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
        switch (propertyChangeEvent.getPropertyName()) {
            case SurveyEvent.SURVEY_DONE: {
                surveyIsDone.setValue(true);
                break;
            }
            case SurveyEvent.NEW_QUESTION: {
                if (survey.isLastQuestion()) {
                    isLastQuestion();
                }
                questionType.setValue(survey.getCurrentQuestionType());
                questionText.setValue(survey.getCurrentQuestionText());
                break;
            }
            case SurveyEvent.SAVE_RESPONSE: {
                saveResponse.setValue(true);
            }
        }
    }

    public MutableLiveData<Boolean> getSaveResponse() {
        return saveResponse;
    }

    public void saveResponse(ArrayList<Integer> responseOption) {
        saveResponse(responseOption, "");
    }

    public void saveResponse(String comment) {
        saveResponse(new ArrayList<>(), comment);
    }

    public void saveResponse(ArrayList<Integer> responseOption, String comment) {
        survey.saveResponse(responseOption, comment);
    }

    public void submitResponse(Activity context){
        survey.submitResponse(context);
    }

}
