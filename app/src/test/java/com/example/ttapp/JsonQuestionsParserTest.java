package com.example.ttapp;

import com.example.ttapp.survey.model.JsonQuestionsParser;
import com.example.ttapp.survey.model.Survey;
import com.example.ttapp.survey.model.answers.NPSAnswer;
import com.example.ttapp.survey.model.answers.YesNoAnswer;
import com.fasterxml.jackson.core.JsonProcessingException;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import static org.assertj.core.api.Assertions.*;

public class JsonQuestionsParserTest {

    //JsonQuestionsParser jqp;
    Survey model;

    @Before
    public void init() throws JsonProcessingException {
        byte[] bytes = new byte[0];
        try {
            bytes = Files.readAllBytes(Paths.get("src/test/java/com/example/ttapp/responseNew.json"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String json = new String(bytes);
        String deviceId = "624b4f6fa23e9500043e154b";
        model = new Survey(json, deviceId, "user1");
    }



}
