package com.example.ttapp.jsonparsing;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties
public class Question {

    @JsonProperty("id")
    public String questionId;

    @JsonProperty("text")
    public Languages questionText;

    @JsonProperty("type")
    public String questionType;

    @JsonProperty("values")
    public List<Answers> answers;

    @JsonProperty("conditions")
    public List<Condition> conditions;

}
