package com.example.ttapp.APIRequester;

import org.json.JSONObject;

public class APIRequester implements IAPIRequester {

    private IAPIRequester requester;
    private static APIRequester instance;

    private APIRequester() {}

    public static void setConcreteAPIRequester(IAPIRequester requester) {
        if (instance == null) {
            instance = new APIRequester();
        }
        instance.requester = requester;
    }

    public static APIRequester getInstance() {
        assertInitialized();
        return instance;
    }

    private static void assertInitialized() {
        if (instance == null) {
            throw new IllegalStateException(APIRequester.class.getSimpleName() + " is not initialized," +
                    "call initialize(...) first");
        }
    }

    @Override
    public void requestQuestionJSONString(String deviceId, Response<String> response) {
        requester.requestQuestionJSONString(deviceId, response);
    }

    @Override
    public void submitResponse(JSONObject jsonObject, boolean saveLocallyOnFail) {
        requester.submitResponse(jsonObject, saveLocallyOnFail);
    }

    @Override
    public void sendOldResponses() {
        requester.sendOldResponses();
    }

    @Override
    public void submitResponse(JSONObject jsonObject, boolean saveLocallyOnFail, Response<String> response) {
        requester.submitResponse(jsonObject, saveLocallyOnFail, response);
    }
}
