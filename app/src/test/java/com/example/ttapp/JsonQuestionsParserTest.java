package com.example.ttapp;

import com.example.ttapp.survey.model.JsonQuestionsParser;
import com.fasterxml.jackson.core.JsonProcessingException;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import static org.assertj.core.api.Assertions.*;

public class JsonQuestionsParserTest {

    @Test
    public void getTitle() throws JsonProcessingException {
        byte[] bytes = new byte[0];
        try {
            bytes = Files.readAllBytes(Paths.get("src/test/java/com/example/ttapp/responseNew.json"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String json = new String(bytes);

        JsonQuestionsParser jqp = new JsonQuestionsParser(json);
        assertThat(jqp.getQuestionText(jqp.getFirstQuestionId())).isEqualTo("Hur mycket gillar du Toto?");
    }

}
