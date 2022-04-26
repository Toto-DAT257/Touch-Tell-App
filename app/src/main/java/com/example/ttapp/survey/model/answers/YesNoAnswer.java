package com.example.ttapp.survey.model.answers;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.ttapp.survey.model.QuestionType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@JsonIgnoreProperties(IAnswer.QUESTION_TYPE)
public class YesNoAnswer implements IAnswer {

    public static int YES = 2;
    public static int NO = 1;

    @JsonProperty(IAnswer.VALUE)
    public int answerValue;

    @JsonProperty(IAnswer.QUESTION)
    public String questionId;

    private final String questionType = QuestionType.YES_NO;


    public YesNoAnswer(int answerValue, String questionId) {
        this.answerValue = answerValue;
        this.questionId = questionId;
    }

    public YesNoAnswer() {}

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public String getAnswerJson() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode answer = mapper.createObjectNode();
        answer.put(IAnswer.QUESTION, questionId);
        answer.put(IAnswer.VALUE, answerValue);
        answer.put(IAnswer.QUESTION_TYPE, questionType);
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(answer);
    }

    @Override
    public String getAnswerType() {
        return this.questionType;
    }

    public void setAnswerTo(int yesOrNo) {
        this.answerValue = yesOrNo;
    }
}
