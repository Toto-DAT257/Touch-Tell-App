package com.example.ttapp.survey.model.jsonparsing;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Class used by JsonQuestionParser to turn the JSON-string from Touch&Tell into java objects
 */
@JsonIgnoreProperties
public class Languages {

    @JsonProperty("language")
    public String language;

    @JsonProperty("text")
    public String text;

}

