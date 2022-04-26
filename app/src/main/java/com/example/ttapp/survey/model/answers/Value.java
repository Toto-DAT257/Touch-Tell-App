package com.example.ttapp.survey.model.answers;

import com.example.ttapp.survey.model.answers.IAnswer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties
public class Value {

    @JsonProperty(IAnswer.VALUE)
    public int value;

}
