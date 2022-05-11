package com.example.ttapp.survey.model;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to store the an answer to a question while the survey is still being taken.
 */
public class QuestionResponse {

    private final List<Integer> answeredOptions;
    private final String comment;
    private final String questionType;
    private final String questionId;

    public QuestionResponse(List<Integer> options, String comment, String questionType, String questionId) {
        if (options == null) {
            this.answeredOptions = new ArrayList<Integer>();
        } else {
            this.answeredOptions = options;
        }

        if (comment == null) {
            this.comment = "";
        } else {
            this.comment = comment;
        }
        this.questionType = questionType;
        this.questionId = questionId;
    }

    public boolean isEmpty() {
        return answeredOptions.isEmpty() && comment.isEmpty();
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
