package com.example.ttapp.APIRequester;

import org.json.JSONObject;

/**
 * This is the main interface for this package. This class provides no functionality by itself.
 * It should be supplied with an {@link IAPIRequester} that implements the wanted functionality.
 * <p>
 * How to use:
 * <ol>
 *     <li>Supply the instance by calling setConcreteAPIRequester and passing an {@link IAPIRequester}</li>
 *     <li>Call getInstance() and then call any further desired methods on the instance.</li>
 * </ol>
 */
public class APIRequester {

    private IAPIRequester requester;
    private static APIRequester instance;

    private APIRequester() {
    }

    /**
     * Sets the this class' {@link IAPIRequester} object to the supplied requester. It is the supplied object
     * which provides functionality to this class.
     *
     * @param requester Object which implements desired behaviour.
     */
    public static void setConcreteAPIRequester(IAPIRequester requester) {
        if (instance == null) {
            instance = new APIRequester();
        }
        instance.requester = requester;
    }

    /**
     * Gets the singleton instance.
     *
     * @return the singleton instance.
     */
    public static APIRequester getInstance() {
        assertInitialized();
        return instance;
    }

    private static void assertInitialized() {
        if (instance == null) {
            throw new IllegalStateException(APIRequester.class.getSimpleName() + " has no concrete " +
                    "requester. Call setConcreteAPIRequester(...) first");
        }
    }


    public void requestQuestionJSONString(String deviceId, Response<String> response) {
        requester.requestQuestionJSONString(deviceId, response);
    }


    public void submitResponse(JSONObject jsonObject, boolean saveLocallyOnFail) {
        requester.submitResponse(jsonObject, saveLocallyOnFail);
    }


    public void sendOldResponses() {
        requester.sendOldResponses();
    }


    public void submitResponse(JSONObject jsonObject, boolean saveLocallyOnFail, Response<String> response) {
        requester.submitResponse(jsonObject, saveLocallyOnFail, response);
    }
}
