package com.example.ttapp.survey.model.jsonparsing;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * Class used by JsonQuestionParser to turn the JSON-string from Touch&Tell into java objects
 */
@JsonIgnoreProperties
public class ResponseValues {

    @JsonProperty("value")
    public int value;

    @JsonProperty("text")
    public List<Languages> answerText;

}