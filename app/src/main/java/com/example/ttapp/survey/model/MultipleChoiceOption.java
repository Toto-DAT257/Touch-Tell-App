package com.example.ttapp.survey.model;

/**
 * Class used for holding a option to a multiple choice question
 */
public class MultipleChoiceOption {

    private String text;
    private int value;

    public MultipleChoiceOption(String text, int value) {
        this.text = text;
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public int getValue() {
        return value;
    }
}
