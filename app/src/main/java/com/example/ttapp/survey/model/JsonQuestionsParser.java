package com.example.ttapp.survey.model;

import com.example.ttapp.survey.model.jsonparsing.Condition;
import com.example.ttapp.survey.model.jsonparsing.Languages;
import com.example.ttapp.survey.model.jsonparsing.Question;
import com.example.ttapp.survey.model.jsonparsing.Survey;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.MissingResourceException;

public class JsonQuestionsParser {

    private String json;
    private List<String> questionOrder;
    private Survey survey;

    private static String SWEDISH = "sv";

    public  JsonQuestionsParser(String json) throws JsonProcessingException {
        this.json = json;
        this.survey = createSurveyObject(json);
        this.questionOrder = createQuestionOrder();
    }

    private Survey createSurveyObject(String json) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
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
        for (Languages l : getQuestion(id).questionText){
            if (l.language.equals(SWEDISH)){
                return l.text;
            }
        }
        throw new IndexOutOfBoundsException("swedish translation does not exist");
    }

    private Question getQuestion(String id){
        return survey.questions.get(getQuestionNumber(id));
    }

    public String getType(String id){
        return survey.questions.get(getQuestionNumber(id)).questionType;
    }

    public boolean conditionExist(String id){
        return getQuestion(id).conditions != null;
    }

    public List<Condition> getConditions(String id){
        if (!conditionExist(id)){
            throw new IllegalArgumentException("This question has no conditions");
        }
        return getQuestion(id).conditions;
    }

}
