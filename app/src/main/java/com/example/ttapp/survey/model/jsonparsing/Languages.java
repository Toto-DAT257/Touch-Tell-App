package com.example.ttapp.survey.model.jsonparsing;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties
public class Languages {

    @JsonProperty("language")
    public String language;

    @JsonProperty("text")
    public String text;

}

