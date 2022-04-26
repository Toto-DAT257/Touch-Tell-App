package com.example.ttapp;

import org.junit.Test;

import com.example.ttapp.survey.model.jsonparsing.Question;
import com.example.ttapp.survey.model.jsonparsing.Survey;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void jsonTest() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        byte[] bytes = new byte[0];
        try {
            bytes = Files.readAllBytes(Paths.get("src/test/java/com/example/ttapp/response.json"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String json = new String(bytes);
        JsonNode rootNode = mapper.readTree(json);
        Survey s = mapper.readValue(json, Survey.class);
        Question q = mapper.readValue(rootNode.get("questions").get(0).toString(), Question.class);
        System.out.println(q.questionId);
        System.out.println(q.questionText.swedish);
    }
}