package com.example.ttapp.APIRequester;

import org.json.JSONObject;

public interface IAPIRequester {
    void requestQuestionJSONString(String deviceId, Response<String> response);

    void submitResponse(JSONObject jsonObject, boolean saveLocallyOnFail);

    void sendOldResponses();

    void submitResponse(JSONObject jsonObject, boolean saveLocallyOnFail, Response<String> response);
}
