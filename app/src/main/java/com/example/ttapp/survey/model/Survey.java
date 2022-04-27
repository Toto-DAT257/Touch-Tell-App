package com.example.ttapp.survey.model;

import com.example.ttapp.survey.model.jsonparsing.Condition;
import com.example.ttapp.survey.model.jsonparsing.ConditionQuestion;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Main class of the model package. This class serves as the interface to be used by clients.
 * Contains the full logic of traversing a survey.
 * <p>
 * This class makes use of the Java Beans PropertyChange-library which lets clients listen to
 * the events fired by this class.
 */
public class Survey {

    private JsonQuestionsParser jsonQuestionsParser;
    private final ArrayList<String> questionsToSend;
    private String currentQuestionId;
    private final Map<String, QuestionResponse> responses;
    private final PropertyChangeSupport support;

    public Survey(String json) {
        responses = new HashMap<>();
        try {
            jsonQuestionsParser = new JsonQuestionsParser(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        currentQuestionId = jsonQuestionsParser.getFirstQuestionId();
        questionsToSend = new ArrayList<>();
        this.support = new PropertyChangeSupport(this);
    }

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }

    public void removePropertyChangeListener(PropertyChangeListener pcl) {
        support.removePropertyChangeListener(pcl);
    }

    public String getCurrentQuestionId() {
        return currentQuestionId;
    }

    public String getCurrentQuestionText() {
        return jsonQuestionsParser.getQuestionText(currentQuestionId);
    }

    public String getCurrentQuestionType() {
        return jsonQuestionsParser.getType(currentQuestionId);
    }

    public void nextQuestion() {
        support.firePropertyChange(SurveyEvent.SAVE_RESPONSE, "must write something", "");

        boolean isLastQuestion = jsonQuestionsParser.isLastQuestion(currentQuestionId);
        if (isLastQuestion) {
            submitAnswers();
            support.firePropertyChange(SurveyEvent.SURVEY_DONE, currentQuestionId, "");
            return;
        }
        String oldQuestionId = currentQuestionId;
        currentQuestionId = calcNextQuestion(currentQuestionId);
        support.firePropertyChange(SurveyEvent.NEW_QUESTION, oldQuestionId, currentQuestionId);
    }

    private String calcNextQuestion(String questionId) {
        String nextQuestionId = jsonQuestionsParser.getNextQuestionId(questionId);
        if (allConditionsAreMet(nextQuestionId)) {
            return nextQuestionId;
        }
        return calcNextQuestion(nextQuestionId);
    }

    private String calcPreviousQuestion(String questionId) {
        String previousQuestionId = jsonQuestionsParser.getPreviousQuestionId(questionId);
        if (allConditionsAreMet(previousQuestionId)) {
            return previousQuestionId;
        }
        return calcPreviousQuestion(previousQuestionId);
    }

    private boolean allConditionsAreMet(String questionId) {
        if (!jsonQuestionsParser.conditionExist(questionId)) return true;
        for (Condition c : jsonQuestionsParser.getConditions(questionId)) {
            for (ConditionQuestion q : c.conditionQuestions) {
                if (!conditionIsMet(q.conditionQuestionId, q.options)) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean conditionIsMet(String id, List<Integer> conditionOptions) {
        if (responses.containsKey(id) && responses.get(id).getAnsweredOptions() != null) {
            for (int a : responses.get(id).getAnsweredOptions()) {
                if (conditionOptions.contains(a)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void previousQuestion() {
        boolean isFirstQuestion = jsonQuestionsParser.isFirstQuestion(currentQuestionId);
        if (isFirstQuestion) {
            return;
        }

        String oldQuestionId = currentQuestionId;
        currentQuestionId = calcPreviousQuestion(currentQuestionId);
        support.firePropertyChange(SurveyEvent.NEW_QUESTION, oldQuestionId, currentQuestionId);
    }

    public void putResponse(String questionId, QuestionResponse response) {
        responses.put(questionId, response);
    }

    public QuestionResponse getResponse(String questionId) {
        return responses.get(questionId);
    }

    public void saveResponse(ArrayList<Integer> answerOption, String comment) {
        if (!comment.isEmpty() || !answerOption.isEmpty()) {
            QuestionResponse questionResponse = createResponseObject(answerOption, comment);
            putResponse(currentQuestionId, questionResponse);
        }
    }

    private QuestionResponse createResponseObject(ArrayList<Integer> answerOption, String comment) {
        return new QuestionResponse(answerOption, comment, getCurrentQuestionType(), currentQuestionId);
    }

    public void submitAnswers() {
        // TODO: Submit answers
    }
}
