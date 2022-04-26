package com.example.ttapp.survey.model.answers;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;

@JsonIgnoreProperties(IAnswer.QUESTION_TYPE)
public class SmileyQuartet implements IAnswer{

    public static int SADDEST = 1;
    public static int SAD = 2;
    public static int HAPPY = 3;
    public static int HAPPIEST = 4;

    @JsonProperty(IAnswer.VALUE)
    private int answer;

    @JsonProperty(IAnswer.QUESTION)
    public String questionId;

    @Override
    public String getAnswerJson() throws JsonProcessingException {
        return null;
    }

    @Override
    public String getAnswerType() {
        return null;
    }
}
