package com.example.ttapp.survey.model;

import com.example.ttapp.survey.model.jsonparsing.Condition;
import com.example.ttapp.survey.model.jsonparsing.ConditionQuestion;
import com.example.ttapp.survey.model.answers.Value;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

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
    private Map<String, String> answers;

    public Survey(String json) {
        answers = new HashMap<>();
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

    private boolean conditionIsMet(String id, List<Integer> options)  {
        if (answers.containsKey(id)){
            int answerValue =  getAnswerValue(answers.get(id));
            return options.contains(answerValue);
        }
        return false;
    }

    private int getAnswerValue(String jsonAnswer) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            Value answerObject = mapper.readValue(jsonAnswer, Value.class); // using a class Value. works for all questionTypes that can be in a condition
            return answerObject.value;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Problem with jackson parsing"); // TODO use better exception maybe
        }
    }


    public void previousQuestion() {
        boolean isFirstQuestion = jsonQuestionsParser.isFirstQuestion(currentQuestionId);
        if (isFirstQuestion) { return; }
        currentQuestionId = jsonQuestionsParser.getPreviousQuestionId(currentQuestionId);
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
