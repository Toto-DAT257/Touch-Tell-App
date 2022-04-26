package com.example.ttapp.jsonparsing;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties
public class Condition {

    @JsonProperty("questions")
    public List<ConditionQuestion> conditionQuestion;
}
