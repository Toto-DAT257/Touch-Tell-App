package com.example.ttapp.survey.model;

/**
 * Interface solely serving as storage for string values to ensure no typos or non-matching
 * values are present in clients.
 */
public interface SurveyEvent {

    String NEW_QUESTION = "new-question";
    String SURVEY_DONE = "survey-done";
    String SAVE_RESPONSE = "save-response";

}
