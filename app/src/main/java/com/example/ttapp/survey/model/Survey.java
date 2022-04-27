package com.example.ttapp.survey.model;

import com.example.ttapp.survey.model.jsonparsing.Condition;
import com.example.ttapp.survey.model.jsonparsing.ConditionQuestion;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class for a survey, fetching and storing the information about the questions for the currently logged in user.
 *
 * @author Simon Holst, Amanda Cyrén, Emma Stålberg
 */
public class Survey {

    private String json;
    private JsonQuestionsParser jsonQuestionsParser;
    private ArrayList<String> questionsToSend;
    private String currentQuestionId;
    private Map<String, QuestionResponse> responses;

    public Survey(String json) {
        responses = new HashMap<>();
        this.json = json;
        try {
            jsonQuestionsParser = new JsonQuestionsParser(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        currentQuestionId = jsonQuestionsParser.getFirstQuestionId();
        questionsToSend = new ArrayList<>();
    }

    public String getCurrentQuestionId() {
        return currentQuestionId;
    }

    public String getCurrentQuestionText() {
        return jsonQuestionsParser.getQuestionText(currentQuestionId);
    }

    public String getCurrentQuestionType() {
        return jsonQuestionsParser.getType(currentQuestionId);
    }

    public void nextQuestion() {
        boolean isLastQuestion = jsonQuestionsParser.isLastQuestion(currentQuestionId);
        if (isLastQuestion) {
            submitAnswers();
            return;
        }
        currentQuestionId = jsonQuestionsParser.getNextQuestionId(currentQuestionId);
        if (!allConditionsAreMet()){
            nextQuestion();
        }
    }

    private boolean allConditionsAreMet() {
        if (jsonQuestionsParser.conditionExist(currentQuestionId)){
            for (Condition c : jsonQuestionsParser.getConditions(currentQuestionId)){
                for (ConditionQuestion q : c.conditionQuestion){
                    if (!conditionIsMet(q.conditionQquestionsId, q.options)){
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private boolean conditionIsMet(String id, List<Integer> conditionOptions)  {
        if (responses.containsKey(id)){
            for (int a : responses.get(id).getAnsweredOptions()){
                if (conditionOptions.contains(a)){
                    return true;
                }
            }
        }
        return false;
    }

    public void previousQuestion() {
        boolean isFirstQuestion = jsonQuestionsParser.isFirstQuestion(currentQuestionId);
        if (isFirstQuestion) { return; }
        currentQuestionId = jsonQuestionsParser.getPreviousQuestionId(currentQuestionId);
    }

    public void putAnswer(String questionId, QuestionResponse questionResponse) {
        responses.put(questionId, questionResponse);
    }

    public QuestionResponse getResponse(String questionId) {
        return responses.get(questionId);
    }

    public void submitAnswers() {
        // TODO: Submit answers
    }
}
