package com.example.ttapp;

import static org.assertj.core.api.Assertions.*;

import com.example.ttapp.survey.model.Survey;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


public class SurveyTest {

    Survey survey;

    @Before
    public void init() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        byte[] bytes = new byte[0];
        try {
            bytes = Files.readAllBytes(Paths.get("src/test/java/com/example/ttapp/responseNew.json"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String json = new String(bytes);
        survey = new Survey(json);
    }

    @Test
    public void checkCorrectFirstQuestion() {
        String firstQuestionId = survey.getCurrentQuestionId();
        String correctFirstQuestionId = "624c0efc834f260004f91703";
        assertThat(firstQuestionId).isEqualTo(correctFirstQuestionId);
    }

    @Test
    public void checkThatNextAndPreviousQuestionWorks() {
        String firstQuestionId = survey.getCurrentQuestionId();
        survey.nextQuestion();
        String newQuestionId = survey.getCurrentQuestionId();
        assertThat(newQuestionId).isEqualTo("624c10dca23e9500043e1815");
        survey.previousQuestion();
        String previousQuestionId = survey.getCurrentQuestionId();
        assertThat(previousQuestionId).isEqualTo(firstQuestionId);
    }

}
