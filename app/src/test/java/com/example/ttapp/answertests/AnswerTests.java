package com.example.ttapp.answertests;

import com.example.ttapp.survey.model.answers.NPSAnswer;
import com.example.ttapp.survey.model.answers.YesNoAnswer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

public class AnswerTests {

    @Test
    public void recreateObjectFromJson() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        String questionId = "thisistestquestionid";

        YesNoAnswer a = new YesNoAnswer(YesNoAnswer.YES, questionId);
        YesNoAnswer b = new YesNoAnswer(YesNoAnswer.NO, questionId);
        String jsona = a.getAnswerJson();
        String jsonb = b.getAnswerJson();

        YesNoAnswer answera = mapper.readValue(jsona, YesNoAnswer.class);
        YesNoAnswer answerb = mapper.readValue(jsonb, YesNoAnswer.class);
        assertThat(answera.answerValue).isEqualTo(YesNoAnswer.YES);
        assertThat(answera.questionId).isEqualTo(questionId);
        assertThat(answerb.answerValue).isEqualTo(YesNoAnswer.NO);
        assertThat(answerb.questionId).isEqualTo(questionId);
    }

    @Test
    public void recreateNPSObjectFromJson() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        NPSAnswer a = new NPSAnswer(1, "npsquestionid");
        String jsona = a.getAnswerJson();
        NPSAnswer answerObject = mapper.readValue(jsona, NPSAnswer.class);
        assertThat(answerObject.value).isEqualTo(a.value);
        assertThat(answerObject.questionId).isEqualTo(a.questionId);
    }
}
