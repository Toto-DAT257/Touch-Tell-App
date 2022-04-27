package com.example.ttapp.survey.model;

import java.util.List;

/**
 * This class is used to store the an answer to a question while the survey is still being taken.
 */
public class QuestionResponse {

    private List<Integer> answeredOptions;
    private String comment;
    private String questionType;
    private String questionId;

    public QuestionResponse(List<Integer> options, String comment, String questionType, String questionId) {
        this.answeredOptions = options;
        this.comment = comment;
        this.questionType = questionType;
        this.questionId = questionId;
    }

    public List<Integer> getAnsweredOptions() {
        return answeredOptions;
    }

    public String getComment() {
        return comment;
    }

    public String getQuestionType() {
        return questionType;
    }

    public String getQuestionId() {
        return questionId;
    }
}
