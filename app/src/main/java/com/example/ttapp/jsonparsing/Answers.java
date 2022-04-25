package com.example.ttapp.jsonparsing;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties
public class Answers {

    @JsonProperty("value")
    public int value;

    @JsonProperty("text")
    public Languages answerText;

}