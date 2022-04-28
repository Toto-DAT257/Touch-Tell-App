package com.example.ttapp.survey.model.jsonparsing;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Class used by JsonQuestionParser to turn the JSON-string from Touch&Tell into java objects
 */
@JsonIgnoreProperties
public class ConditionQuestion {

    @JsonProperty("id")
    public String conditionQuestionId;

    @JsonProperty("options")
    public List<Integer> options;

}
