package com.example.ttapp.survey.fragments.util;

/**
 * Interface solely serving as storage for string values to ensure no typos or non-matching
 * values are present in clients.
 */
public interface QuestionType {
    String YES_NO = "yes-no";
    String NPS = "nps";
    String MULTIPLE_CHOICE = "multiple-choice";
    String SMILEY_QUARTET = "smiley-quartet";
    String SELECT_MANY = "select-many";
    String COMMENT = "comment";
    String SHORT_TEXT = "short-text";
    String TYPE_AHEAD = "typeahead";
    String NUMBER = "number";
    String EMAIL = "email";
    String SMILEY_COMMENT = "smiley-comment";
}
