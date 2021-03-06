package com.example.ttapp;

import static org.assertj.core.api.Assertions.*;

import com.example.ttapp.survey.model.QuestionResponse;
import com.example.ttapp.survey.model.Survey;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class SurveyTest {

    TestSurveyClass survey;

    @Before
    public void init() throws IOException {
        String json = new String(Files.readAllBytes(Paths.get("src/test/java/com/example/ttapp/responseNew.json")));
        String deviceId = "624b4f6fa23e9500043e154b";
        survey = new TestSurveyClass(json, deviceId, "user1");
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

    @Test
    public void checkThatCorrectAnswersAreSaved() {
        ArrayList<Integer> answerOption = new ArrayList<>();
        answerOption.add(1);
        survey.saveResponse(answerOption, "");
        assertThat(survey.getResponses().get(survey.getCurrentQuestionId()).getAnsweredOptions()).isEqualTo(answerOption);
    }

    @Test
    public void checkThatAnswersAreAlteredWhenTheSameQuestionIsAnsweredTwice() {
        ArrayList<Integer> answerOption1 = new ArrayList<>();
        ArrayList<Integer> answerOption2 = new ArrayList<>();

        answerOption1.add(1);
        survey.saveResponse(answerOption1, "");
        answerOption2.add(2);
        survey.saveResponse(answerOption2, "");
        assertThat(survey.getResponses().get(survey.getCurrentQuestionId()).getAnsweredOptions()).isEqualTo(answerOption2);
    }

    @Test
    public void checkThatCorrectAnswersAreSent() {
        ArrayList<Integer> answerOption = new ArrayList<>();
        // Question 1
        addAnswerOptionAndSaveAndMoveOn(1, "");
        // Question 2
        addAnswerOptionAndSaveAndMoveOn(-1, "Hej! ;)");
        // Question 3
        addAnswerOptionAndSaveAndMoveOn(2,"");
        // Question 4 (Conditional)
        addAnswerOptionAndSaveAndMoveOn(3,"");
        // Question 5
        String questionIdToBeExcluded = survey.getCurrentQuestionId();
        addAnswerOptionAndSaveAndMoveOn(2,"");
        // Move back 2 steps and avoid the conditional
        survey.previousQuestion();
        survey.previousQuestion();
        // Question 4 (Conditional)
        addAnswerOptionAndSaveAndMoveOn(1,"");
        // Skip to Question 6 directly
        answerOption.add(3);
        survey.saveResponse(new ArrayList<>(answerOption),"");

        List<QuestionResponse> questionResponses = survey.getResponsesToSend();
        for (QuestionResponse qr : questionResponses) {
            assertThat(qr.getQuestionId()).isNotEqualTo(questionIdToBeExcluded);
        }
        assertThat(questionResponses.size()).isEqualTo(5);
    }

    private void addAnswerOptionAndSaveAndMoveOn(int answerOption, String comment) {
        List<Integer> answerOptions = new ArrayList<>();
        if (answerOption != -1) answerOptions.add(answerOption);
        survey.saveResponse(new ArrayList<>(answerOptions), comment);
        survey.nextQuestion();
    }

    private class TestSurveyClass extends Survey{

        public TestSurveyClass(String json, String deviceId, String identifier) {
            super(json, deviceId, identifier);
        }

        public String getCurrentQuestionId() {
            return super.getCurrentQuestionId();
        }

        public String buildJsonResponse(List<QuestionResponse> responseToSend) {return super.buildJsonResponse(responseToSend); }

        public Map<String, QuestionResponse> getResponses() { return super.getResponses(); }

        public List<QuestionResponse> getResponsesToSend() { return super.getResponsesToSend(); }
    }

}
