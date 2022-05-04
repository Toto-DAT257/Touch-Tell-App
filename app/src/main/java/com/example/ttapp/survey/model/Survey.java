package com.example.ttapp.survey.model;

import com.example.ttapp.APIRequester.TTRequester;
import com.example.ttapp.survey.model.jsonparsing.Condition;
import com.example.ttapp.survey.model.jsonparsing.ConditionQuestion;
import com.example.ttapp.survey.util.SurveyEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.json.JSONException;
import org.json.JSONObject;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Main class of the model package. This class serves as the interface to be used by clients.
 * Contains the full logic for traversing a survey.
 * <p>
 * This class makes use of the Java Beans PropertyChange-library which lets clients listen to
 * the events fired by this class.
 */
public class Survey {

    private JsonQuestionsParser jsonQuestionsParser;
    private String currentQuestionId;
    private final Map<String, QuestionResponse> responses;
    private final PropertyChangeSupport support;
    private final String deviceId;
    private final String identifier;

    public Survey(String json, String deviceId, String identifier) {
        responses = new HashMap<>();
        try {
            jsonQuestionsParser = new JsonQuestionsParser(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        currentQuestionId = jsonQuestionsParser.getFirstQuestionId();
        this.deviceId = deviceId;
        this.identifier = identifier;
        this.support = new PropertyChangeSupport(this);
    }

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }

    public void removePropertyChangeListener(PropertyChangeListener pcl) {
        support.removePropertyChangeListener(pcl);
    }

    protected String getCurrentQuestionId() {
        return currentQuestionId;
    }

    public String getCurrentQuestionText() {
        return jsonQuestionsParser.getQuestionText(currentQuestionId);
    }

    public String getCurrentQuestionType() {
        return jsonQuestionsParser.getType(currentQuestionId);
    }

    public Boolean isLastQuestion() {
        return jsonQuestionsParser.isLastQuestion(currentQuestionId);
    }

    /**
     * Changes the current question to the next with one exception; if
     * the current question is the last one then no change will be made. In the process of doing this
     * a maximum of three events are announced to listeners:
     * <ol>
     *     <li>A save response event telling all listeners they must save their answers now otherwise
     *     the circumstancing info will be lost. This event is always announced.</li>
     *     <li>If the current question is the last an event signalling that the survey is completed
     *     will be fired</li>
     *     <li>If the question is changed a new question event is fired</li>
     * </ol>
     */
    public void nextQuestion() {
        support.firePropertyChange(SurveyEvent.SAVE_RESPONSE, "must write something", "");

        if (isLastQuestion()) {
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
                if (!conditionIsMet(q.conditionQuestionId, q.options) || !allConditionsAreMet(q.conditionQuestionId)) {
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

    /**
     * Changes the current question to the previous one and fires a new question event. If the
     * current question is the first one then nothing is done.
     */
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

    /**
     * Saves an answer to to the current question.
     *
     * @param answerOption The answer-values to the question.
     * @param comment      The comment answer to the question.
     */
    public void saveResponse(ArrayList<Integer> answerOption, String comment) {
        if (!comment.isEmpty() || !answerOption.isEmpty()) {
            QuestionResponse questionResponse = createResponseObject(answerOption, comment);
            putResponse(currentQuestionId, questionResponse);
        }
    }

    protected Map<String, QuestionResponse> getResponses() {
        return responses;
    }

    private QuestionResponse createResponseObject(ArrayList<Integer> answerOption, String comment) {
        return new QuestionResponse(answerOption, comment, getCurrentQuestionType(), currentQuestionId);
    }

    protected List<QuestionResponse> getResponsesToSend() {
        List<QuestionResponse> toSend = new ArrayList<>();
        for (QuestionResponse r : responses.values()) {
            if (allConditionsAreMet(r.getQuestionId())) {
                toSend.add(r);
            }
        }
        return toSend;
    }

    protected String buildJsonResponse(List<QuestionResponse> responsesToSend) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode response = mapper.createObjectNode();
        response.put("deviceId", deviceId);

        ArrayNode questions = mapper.createArrayNode();
        for (QuestionResponse q : responsesToSend) {
            ObjectNode question = mapper.createObjectNode();
            question.put("device", deviceId);
            question.put("question", q.getQuestionId());
            question.put("questionType", q.getQuestionType());
            if (!q.getComment().isEmpty()) {
                question.put("comment", q.getComment());
            }
            if (!q.getAnsweredOptions().isEmpty()) {
                ArrayNode options = mapper.createArrayNode();
                for (int o : q.getAnsweredOptions()) {
                    options.add(o);
                }
                question.set("option", options);
            }
            question.put("version", "0.0.0");
            long unixTime = System.currentTimeMillis();
            question.put("time", unixTime);
            question.putArray("tags").add(identifier);
            questions.add(question);
        }

        ObjectNode respondersObject = mapper.createObjectNode();
        respondersObject.set("responses", questions);
        response.putArray("responders").add(respondersObject);

        String s = null;
        try {
            s = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(response);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return s;
    }

    /**
     * Submits the gathered answers to Touch&Tell.
     */
    public void submitResponse() {
        List<QuestionResponse> responsesToSend = getResponsesToSend();
        if (responsesToSend.isEmpty()) {
            return;
        }
        String json = buildJsonResponse(responsesToSend);
        JSONObject toSend = null;
        try {
            toSend = new JSONObject(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        TTRequester ttRequester = TTRequester.getInstance();
        ttRequester.submitResponse(toSend);
    }
}
