package com.example.ttapp.survey.model;

public enum QuestionType {

    YES_NO("yes-no"),
    ONE_TO_TEN("nps");

    String type;

    QuestionType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
