package com.example.ttapp.survey.model;

import com.example.ttapp.survey.model.jsonparsing.Question;
import com.example.ttapp.survey.model.jsonparsing.Survey;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

public class JsonQuestionsParser {

    private String json;
    private List<String> questionOrder;
    private Survey survey;

    public  JsonQuestionsParser(String json) throws JsonProcessingException {
        this.json = json;
        this.survey = createSurveyObject(json);
        this.questionOrder = createQuestionOrder();
    }

    private Survey createSurveyObject(String json) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        JsonNode rootNode = mapper.readTree(json);
        return mapper.readValue(json, Survey.class);
    }

    private List<String> createQuestionOrder(){
        List<String> ids = new ArrayList<>();
        for (Question q : survey.questions){
            ids.add(q.questionId);
        }
        return ids;
    }

    public String getFirstQuestionId(){
        return questionOrder.get(0);
    }

    public boolean isLastQuestion(String id){
        return questionOrder.get(questionOrder.size()-1).equals(id);
    }

    public boolean isFirstQuestion(String id){
        return questionOrder.get(0).equals(id);
    }

    public String getNextQuestionId(String currentId){
        if (isLastQuestion(currentId)){
            throw new IndexOutOfBoundsException("currentId is the last question, no next question exist");
        }
        for (int i = 0; i < questionOrder.size(); i++){
            String id = questionOrder.get(i);
            if (id.equals(currentId)){
                return questionOrder.get(i+1);
            }
        }
        throw new IllegalArgumentException("currentId does not exist in the survey");
    }

    public String getPreviousQuestionId(String currentId){
        if (isFirstQuestion(currentId)){
            throw new IndexOutOfBoundsException("currentId is the firs question, no previous question exist");
        }
        for (int i = 0; i < questionOrder.size(); i++){
            String id = questionOrder.get(i);
            if (id.equals(currentId)){
                return questionOrder.get(i-1);
            }
        }
        throw new IllegalArgumentException("currentId does not exist in the survey");
    }

    private int getQuestionNumber(String id){
        int number = 0;
        for (int i = 0; i < questionOrder.size(); i++){
            if (questionOrder.get(i).equals(id)){
                return number;
            }
            number++;
        }
        throw new IllegalArgumentException("id does not exist in the survey");
    }

    public String getQuestionText(String id){
        return survey.questions.get(getQuestionNumber(id)).questionText.swedish;
    }



}
