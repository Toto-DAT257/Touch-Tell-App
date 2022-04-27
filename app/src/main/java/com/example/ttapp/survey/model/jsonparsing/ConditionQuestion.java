package com.example.ttapp.survey.model.jsonparsing;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties
public class ConditionQuestion {

    @JsonProperty("id")
    public String conditionQuestionId;

    @JsonProperty("options")
    public List<Integer> options;

}
