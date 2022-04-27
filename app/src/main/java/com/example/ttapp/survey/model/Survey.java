package com.example.ttapp.survey.model;

import com.example.ttapp.survey.model.jsonparsing.Condition;
import com.example.ttapp.survey.model.jsonparsing.ConditionQuestion;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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

    // currentId is already changed when saving the answer to the previous question
    // so we save the old id in this variable responseQuestionId
    private String responseQuestionId;

    private Map<String, QuestionResponse> responses;
    private PropertyChangeSupport support;

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
        this.support = new PropertyChangeSupport(this);
    }

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }

    public void removePropertyChangeListener(PropertyChangeListener pcl) {
        support.removePropertyChangeListener(pcl);
    }

    public String getCurrentQuestionId() {
        return currentQuestionId;
    }

    public String getCurrentQuestionText() {
        return jsonQuestionsParser.getQuestionText(currentQuestionId);
    }

    public String getResponseQuestionId(){
        return responseQuestionId;
    }

    public String getResponseQuestionType(){
        return jsonQuestionsParser.getType(responseQuestionId);
    }

    public String getCurrentQuestionType() {
        return jsonQuestionsParser.getType(currentQuestionId);
    }

    public void nextQuestion() {
        boolean isLastQuestion = jsonQuestionsParser.isLastQuestion(currentQuestionId);
        if (isLastQuestion) {
            submitAnswers();
            support.firePropertyChange(SurveyEvent.SURVEY_DONE, currentQuestionId, "");
            return;
        }
        String oldQuestionId = currentQuestionId;
        responseQuestionId = currentQuestionId;
        currentQuestionId = calcNextQuestion(currentQuestionId);
        support.firePropertyChange(SurveyEvent.NEW_QUESTION, oldQuestionId, currentQuestionId);
    }

    private String calcNextQuestion(String questionId) {
        String nextQuestionId = jsonQuestionsParser.getNextQuestionId(questionId);
        if (allConditionsAreMet(nextQuestionId)){
            return nextQuestionId;
        }
        return calcNextQuestion(nextQuestionId);
    }

    private String calcPreviousQuestion(String questionId) {
        String previousQuestionId = jsonQuestionsParser.getPreviousQuestionId(questionId);
        if (allConditionsAreMet(previousQuestionId)){
            return previousQuestionId;
        }
        return calcPreviousQuestion(previousQuestionId);
    }

    private boolean allConditionsAreMet(String questionId) {
        if (!jsonQuestionsParser.conditionExist(questionId)) return true;
            for (Condition c : jsonQuestionsParser.getConditions(questionId)){
                for (ConditionQuestion q : c.conditionQuestion){
                    if (!conditionIsMet(q.conditionQquestionsId, q.options)){
                        return false;
                    }
                }
            }
        return true;
    }

    private boolean conditionIsMet(String id, List<Integer> conditionOptions)  {
        if (responses.containsKey(id) && responses.get(id).getAnsweredOptions() != null){
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

        String oldQuestionId = currentQuestionId;
        currentQuestionId = calcPreviousQuestion(currentQuestionId);
        support.firePropertyChange(SurveyEvent.NEW_QUESTION, oldQuestionId, currentQuestionId);
    }

    public void putResponse(String questionId, QuestionResponse response) {
        responses.put(questionId, response);
    }

    public QuestionResponse getResponse(String questionId) {
        return responses.get(questionId);
    }

    public void saveResponse(ArrayList<Integer> answeroption, String comment) {
        if (comment != null || !answeroption.isEmpty()) {
            QuestionResponse questionResponse = createResponseObject(answeroption, comment);
            putResponse(getResponseQuestionId(), questionResponse);
        }
    }

    private QuestionResponse createResponseObject(ArrayList<Integer> answeroption, String comment) {
        return new QuestionResponse(answeroption, comment, getResponseQuestionType(), getResponseQuestionId());
    }

    public void submitAnswers() {
        // TODO: Submit answers
    }




   
}
