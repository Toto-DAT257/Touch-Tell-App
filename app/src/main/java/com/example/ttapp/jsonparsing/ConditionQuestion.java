package com.example.ttapp.jsonparsing;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties
public class ConditionQuestion {

    @JsonProperty("id")
    public List<ConditionQuestion> conditionQquestionsId;

    @JsonProperty("options")
    public List<Integer> options;

}
