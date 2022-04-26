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
import com.example.ttapp.survey.model.answers.IAnswer;
import com.fasterxml.jackson.core.JsonProcessingException;
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
    private JsonQuestionsParser jsonQuestionsParser;
    private ArrayList<String> questionsToSend;
    private String currentQuestion;
    private Map<String, String> answers;

    public Survey(String json) {
        this.json = json;
        try {
            jsonQuestionsParser = new JsonQuestionsParser(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        currentQuestion = jsonQuestionsParser.getFirstQuestionId();
        questionsToSend = new ArrayList<>();
    }

    public String getCurrentQuestionId() {
        return currentQuestion;
    }

    public String getCurrentQuestionText() {
        return jsonQuestionsParser.getQuestionText(currentQuestion);
    }

    public String getCurrentQuestionType() {
        return jsonQuestionsParser.getType(currentQuestion);
    }

    public void nextQuestion() {
        boolean isLastQuestion = jsonQuestionsParser.isLastQuestion(currentQuestion);
        if (isLastQuestion) { submitAnswers(); return; }
        currentQuestion = jsonQuestionsParser.getNextQuestionId(currentQuestion);
    }

    public void previousQuestion() {
        boolean isFirstQuestion = jsonQuestionsParser.isFirstQuestion(currentQuestion);
        if (isFirstQuestion) { return; }
        currentQuestion = jsonQuestionsParser.getPreviousQuestionId(currentQuestion);
    }

    public void putAnswer(String questionId, String answerInJson) {
        answers.put(questionId, answerInJson);
    }

    public String getAnswer(String questionId) {
        return answers.get(questionId);
    }

    public void submitAnswers() {
        // TODO: Submit answers
    }
}
