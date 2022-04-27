package com.example.ttapp.survey.model;

import java.util.List;

public class Response {

    private List<Integer> options;
    private String comment;
    private String questionType;
    private String questionId;

    public Response(List<Integer> options, String comment, String questionType, String questionId) {
        this.options = options;
        this.comment = comment;
        this.questionType = questionType;
        this.questionId = questionId;
    }
}
