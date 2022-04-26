package com.example.ttapp.survey.model.answers;

import com.example.ttapp.survey.model.QuestionType;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface IAnswer {

    String QUESTION_TYPE = "questionType";
    String VERSION = "version";
    String TIME = "time";
    String DEVICE = "device";
    String QUESTION = "question";
    String COMMENT = "comment";
    String TAGS = "tags";
    String VALUE = "value";

    String getAnswerJson() throws JsonProcessingException;
    String getAnswerType();

}
