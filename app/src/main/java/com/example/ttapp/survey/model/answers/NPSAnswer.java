package com.example.ttapp.survey.model.answers;

import com.example.ttapp.survey.model.QuestionType;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class NPSAnswer implements IAnswer {

    @JsonProperty(IAnswer.VALUE)
    public int value;

    @JsonProperty(IAnswer.QUESTION)
    public String questionId;

    private final String questionType = QuestionType.NPS;

    public NPSAnswer(int value, String questionId) {
        this.value = value;
        this.questionId = questionId;
    }

    public NPSAnswer() {
    }

    @Override
    public String getAnswerJson() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode answer = mapper.createObjectNode();
        answer.put(IAnswer.QUESTION, questionId);
        answer.put(IAnswer.VALUE, value);
        answer.put(IAnswer.QUESTION_TYPE, questionType);
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(answer);
    }

    @Override
    public String getAnswerType() {
        return this.questionType;
    }
}
