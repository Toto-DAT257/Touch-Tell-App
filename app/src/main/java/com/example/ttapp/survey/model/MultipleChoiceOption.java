package com.example.ttapp.survey.model;

/**
 * Class used for holding a option to a multiple choice question
 */
public class MultipleChoiceOption {

    private final String text;
    private final int value;
    private boolean isSelected;

    public MultipleChoiceOption(String text, int value) {
        this.text = text;
        this.value = value;
        this.isSelected = false;
    }

    public String getText() {
        return text;
    }

    public int getValue() {
        return value;
    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public boolean isSelected() {
        return isSelected;
    }
}
