package com.example.ttapp.survey.util;

/**
 * Interface solely serving as storage for string values to ensure no typos or non-matching
 * values are present in clients.
 */
public interface SurveyEvent {

    String NEXT_QUESTION = "next-question";
    String PREVIOUS_QUESTION = "previous-question";
    String SURVEY_DONE = "survey-done";
    String SAVE_RESPONSE = "save-response";

}
