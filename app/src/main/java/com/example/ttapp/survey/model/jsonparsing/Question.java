package com.example.ttapp.survey.model.jsonparsing;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Class used by JsonQuestionParser to turn the JSON-string from Touch&Tell into java objects
 */
@JsonIgnoreProperties
public class Question {

    @JsonProperty("id")
    public String questionId;

    @JsonProperty("text")
    public List<Languages> questionText;

    @JsonProperty("type")
    public String questionType;

    @JsonProperty("values")
    public List<ResponseValues> responseValues;

    @JsonProperty("conditions")
    public List<Condition> conditions;

    //@JsonProperty("suggestedAnswers")
    // Needed for typeHead questionType. Don't work in surveyBuilder as of now

}
