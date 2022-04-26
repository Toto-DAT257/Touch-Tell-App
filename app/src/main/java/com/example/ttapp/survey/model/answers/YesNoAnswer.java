package com.example.ttapp.survey.model.answers;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.ttapp.survey.model.QuestionType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.time.Instant;

@JsonIgnoreProperties(IAnswer.QUESTION_TYPE)
public class YesNoAnswer implements IAnswer {

    public static boolean YES = true;
    public static boolean NO = false;

    @JsonProperty(IAnswer.VALUE)
    public boolean answerIsYes;

    @JsonProperty(IAnswer.QUESTION)
    public String questionId;

    private final String questionType = QuestionType.YES_NO;


    public YesNoAnswer(boolean answerIsYes, String questionId) {
        this.answerIsYes = answerIsYes;
        this.questionId = questionId;
    }

    public YesNoAnswer() {}

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public String getAnswerJson() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode answer = mapper.createObjectNode();
        answer.put(IAnswer.QUESTION, questionId);
        answer.put(IAnswer.VALUE, answerIsYes ? 1 : 0);
        answer.put(IAnswer.QUESTION_TYPE, questionType);
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(answer);
    }

    @Override
    public String getAnswerType() {
        return this.questionType;
    }

    public void setAnswerTo(boolean yesOrNo) {
        this.answerIsYes = yesOrNo;
    }
}
