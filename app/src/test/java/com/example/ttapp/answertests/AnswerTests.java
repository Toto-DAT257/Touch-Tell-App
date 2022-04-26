package com.example.ttapp.answertests;

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
        assertThat(answera.answerIsYes).isTrue();
        assertThat(answera.questionId).isEqualTo(questionId);
        assertThat(answerb.answerIsYes).isFalse();
        assertThat(answerb.questionId).isEqualTo(questionId);
    }
}
