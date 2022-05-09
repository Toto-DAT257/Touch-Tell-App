package com.example.ttapp.survey.viewmodel;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.ttapp.APIRequester.Response;
import com.example.ttapp.APIRequester.TTRequester;
import com.example.ttapp.database.MongoDB;
import com.example.ttapp.survey.fragments.SurveyFragment;
import com.example.ttapp.survey.model.MultipleChoiceOption;
import com.example.ttapp.survey.model.QuestionResponse;
import com.example.ttapp.survey.model.Survey;
import com.example.ttapp.survey.model.jsonparsing.ResponseValues;
import com.example.ttapp.survey.util.SurveyEvent;

import org.bson.Document;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import java.util.ArrayList;
import java.util.List;

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
    private MutableLiveData<String> commentResponse;
    private MutableLiveData<List<Integer>> answeroptionsResponse;

    public SurveyViewModel() {
        jsonIsReceived = new MutableLiveData<>();
        questionText = new MutableLiveData<>();
        questionType = new MutableLiveData<>();
        surveyIsDone = new MutableLiveData<>();
        saveResponse = new MutableLiveData<>();
        isLastQuestion = new MutableLiveData<>();
        commentResponse = new MutableLiveData<>();
        answeroptionsResponse = new MutableLiveData<>();
    }

    public void resetSurvey() {
        jsonIsReceived.setValue(false);
        surveyIsDone.setValue(false);
        isLastQuestion.setValue(false);
    }

    /**
     * Loads the questions from the Touch&Tell API for the logged in user.
     *
     * @param identifier the identifier for the deviceId you want questions for.
     */
    public void loadQuestions(String identifier) {
        MongoDB db = MongoDB.getInstance();
        RealmResultTask<Document> task = db.getDeviceIdTask(identifier);
        task.getAsync(result -> {
            if (result.isSuccess()) {
                if (result.get() != null) {
                    Log.i("DEVICE ID", result.get().toString());
                    String deviceId = result.get().get("deviceId").toString();
                    requestFromAPI(deviceId, identifier);

                } else {
                    Log.e("Database", "Identifier not found");
                }
            } else {
                Log.e("Database", "No access");
            }
        });
    }

    private void requestFromAPI(String deviceId, String identifier) {
        SurveyViewModel listener = this;
        TTRequester ttRequester = TTRequester.getInstance();
        ttRequester.requestQuestionJSONString(deviceId, new Response<String>() {
            @Override
            public void response(String json) {
                survey = new Survey(json, deviceId, identifier);
                survey.addPropertyChangeListener(listener);
                questionType.setValue(survey.getCurrentQuestionType());
                questionText.setValue(survey.getCurrentQuestionText());
                jsonIsReceived.setValue(true);
            }
            @Override
            public void error(String object) { }
        });
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
                    isLastQuestion.setValue(true);
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
        clearResponses();
    }

    private void clearResponses() {
        commentResponse = new MutableLiveData<>();
        answeroptionsResponse = new MutableLiveData<>();
    }

    public void submitResponse(){
        survey.submitResponse();
    }

    public List<MultipleChoiceOption> getResponseOptions(){
        return survey.getResponseOptions();
    }

    public int getProgressPercentage(){
        return survey.getProgressPercentage();
    }

    public void repopulate() {
        String currentQResponseComment = survey.getCurrentQResponseComment();
        List<Integer> currentQResponseAnsweredOptions = survey.getCurrentQResponseAnsweredOptions();
        if (!currentQResponseComment.isEmpty()) {
            commentResponse.setValue(currentQResponseComment);
            containsCommentresponse();
        }

        if(!currentQResponseAnsweredOptions.isEmpty()) {
            answeroptionsResponse.setValue(currentQResponseAnsweredOptions);
            containsAnsweredOptionsResponse();
        }
    }

    public LiveData<String> containsCommentresponse() {
        return commentResponse;
    }

    public LiveData<List<Integer>> containsAnsweredOptionsResponse() {
        return answeroptionsResponse;
    }

}
