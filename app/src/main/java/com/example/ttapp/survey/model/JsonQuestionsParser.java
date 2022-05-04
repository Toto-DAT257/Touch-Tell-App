package com.example.ttapp.survey.model;

import com.example.ttapp.survey.model.jsonparsing.Condition;
import com.example.ttapp.survey.model.jsonparsing.Languages;
import com.example.ttapp.survey.model.jsonparsing.Question;
import com.example.ttapp.survey.model.jsonparsing.ResponseValues;
import com.example.ttapp.survey.model.jsonparsing.Survey;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for the JSON-string of questions received from Touch&Tell.
 * Parses JSON-string in order to return the desired information to clients.
 */
public class JsonQuestionsParser {

    private final List<String> questionOrder;
    private final Survey survey;

    private static final String SWEDISH = "sv";


    protected JsonQuestionsParser(String json) throws JsonProcessingException {
        this.survey = createSurveyObject(json);
        this.questionOrder = createQuestionOrder();
    }

    /**
     * Creates a Survey object from a json string using jackson library
     * @param json the json string
     * @return Survey object
     * @throws JsonProcessingException
     */
    private Survey createSurveyObject(String json) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper.readValue(json, Survey.class);
    }

    /**
     * Create a list of all the questionId:s in the survey. Used to to know the question order
     * @return the list of questionId:s
     */
    private List<String> createQuestionOrder() {
        List<String> ids = new ArrayList<>();
        for (Question q : survey.questions) {
            ids.add(q.questionId);
        }
        return ids;
    }

    /**
     * Gets the questionId of the first question in the survey
     * @return the id of the first question
     */
    public String getFirstQuestionId() {
        return questionOrder.get(0);
    }

    /**
     * Checks if a question is the last question of the survey
     * @param id id of the question
     * @return true if the question is last,  false otherwise
     */
    public boolean isLastQuestion(String id) {
        return questionOrder.get(questionOrder.size() - 1).equals(id);
    }

    /**
     * Checks if a question is the first question of the survey
     * @param id id of the question
     * @return true if the question is first,  false otherwise
     */
    public boolean isFirstQuestion(String id) {
        return questionOrder.get(0).equals(id);
    }
    /**
     * Gets the questionId of the question coming next after a question
     * @param currentId id of the question
     * @return the id of the next coming question
     * Trows exception if the question is last in the survey or the question don't exist in the survey
     */
    public String getNextQuestionId(String currentId) {
        if (isLastQuestion(currentId)) {
            throw new IndexOutOfBoundsException("currentId is the last question, no next question exist");
        }
        for (int i = 0; i < questionOrder.size(); i++) {
            String id = questionOrder.get(i);
            if (id.equals(currentId)) {
                return questionOrder.get(i + 1);
            }
        }
        throw new IllegalArgumentException("currentId does not exist in the survey");
    }

    /**
     * Gets the questionId of a the question preceding a question
     * @param currentId id of the question
     * @return the id of the previous question
     * Trows exception if the question is first in the survey or the question don't exist in the survey
     */
    public String getPreviousQuestionId(String currentId) {
        if (isFirstQuestion(currentId)) {
            throw new IndexOutOfBoundsException("currentId is the firs question, no previous question exist");
        }
        for (int i = 0; i < questionOrder.size(); i++) {
            String id = questionOrder.get(i);
            if (id.equals(currentId)) {
                return questionOrder.get(i - 1);
            }
        }
        throw new IllegalArgumentException("currentId does not exist in the survey");
    }

    private int getQuestionNumber(String id) {
        int number = 0;
        for (int i = 0; i < questionOrder.size(); i++) {
            if (questionOrder.get(i).equals(id)) {
                return number;
            }
            number++;
        }
        throw new IllegalArgumentException("id does not exist in the survey");
    }

    /**
     * Gets the question text of a question
     * @param id id of the question
     * @return the question text of the question
     * Trows exception if the question is missing a swedish translation (default language)
     */
    public String getQuestionText(String id) {
        for (Languages l : getQuestion(id).questionText) {
            if (l.language.equals(SWEDISH)) {
                return l.text;
            }
        }
        throw new IndexOutOfBoundsException("swedish translation does not exist");
    }

    private Question getQuestion(String id) {
        return survey.questions.get(getQuestionNumber(id));
    }

    /**
     * Gets the type of a question
     * @param id id of the question
     * @return the type of the question
     */
    public String getType(String id) {
        return survey.questions.get(getQuestionNumber(id)).questionType;
    }

    public List<ResponseValues> getResponseValues(String id){
        Question q = getQuestion(id);
        return q.responseValues;
    }

    /**
     * Checks if a question has any conditions
     * @param id id of the question
     * @return true if condition exist, false otherwise
     */
    public boolean conditionExist(String id) {
        return getQuestion(id).conditions != null;
    }

    /**
     * returns a list with conditions for a question
     * @param id id of the question
     * @return list with conditions
     * Throws exception if the question dose not have any conditions
     */
    public List<Condition> getConditions(String id) {
        if (!conditionExist(id)) {
            throw new IllegalArgumentException("This question has no conditions");
        }
        return getQuestion(id).conditions;
    }

}
