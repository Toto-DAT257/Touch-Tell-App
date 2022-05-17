package com.example.ttapp.APIRequester;

import org.json.JSONObject;

public class DebugRequester implements IAPIRequester {
    @Override
    public void requestQuestionJSONString(String deviceId, Response<String> response) {
        response.response("json response from DebugRequester");
    }

    @Override
    public void submitResponse(JSONObject jsonObject, boolean saveLocallyOnFail) {

    }

    @Override
    public void sendOldResponses() {

    }

    @Override
    public void submitResponse(JSONObject jsonObject, boolean saveLocallyOnFail, Response<String> response) {

    }
}
