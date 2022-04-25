package com.example.ttapp.jsonparsing;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties
public class Languages {

    @JsonProperty("se")
    public String swedish;

    @JsonProperty("en")
    public String english;

}

