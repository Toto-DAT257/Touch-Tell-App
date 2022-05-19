package com.example.ttapp.APIRequester;

import android.content.SharedPreferences;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Concrete IResponseStorage class utilizing Android's {@link SharedPreferences} to store data.
 * <p>
 * Maximum capacity is 10 elements. Realistically only 1 should be needed.
 */
public class PreferenceStorage implements IResponseStorage<JSONObject> {

    private final SharedPreferences preferences;
    private static final String RESPONSE_SAVE_PREFIX = "saved-response-number-";
    private static final String TAG = "PreferenceStorage";

    public PreferenceStorage(SharedPreferences preferences) {
        this.preferences = preferences;
    }

    /**
     * Saves the {@link JSONObject} as a string in {@link SharedPreferences}. If maximum capacity is
     * reached no data will be saved.
     *
     * @param response the object to be saved.
     */
    @Override
    public void saveResponse(JSONObject response) {
        for (int i = 1; i < 10; i++) {
            String JSONString = preferences.getString(RESPONSE_SAVE_PREFIX + i, "");
            if (JSONString.isEmpty()) {
                preferences.edit().putString(RESPONSE_SAVE_PREFIX + i, response.toString()).apply();
                break;
            }
        }
    }

    /**
     * Removes the corresponding object in stored in {@link SharedPreferences} if there is one.
     *
     * @param objectToRemove the object to be removed from storage.
     */
    @Override
    public void removeResponse(JSONObject objectToRemove) {
        for (int i = 1; i < 10; i++) {
            String JSONString = preferences.getString(RESPONSE_SAVE_PREFIX + i, "");
            if (JSONString.equals(objectToRemove.toString())) {
                preferences.edit().remove(RESPONSE_SAVE_PREFIX + i).apply();
                break;
            }
        }
    }

    /**
     * Gets all the responses stored in {@link SharedPreferences}
     *
     * @return all responses stored in {@link SharedPreferences}
     */
    @Override
    public List<JSONObject> getAllResponses() {
        List<JSONObject> oldResponses = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            try {
                String JSONString = preferences.getString(RESPONSE_SAVE_PREFIX + i, "");
                if (!JSONString.isEmpty()) {
                    oldResponses.add(new JSONObject(JSONString));
                }
            } catch (JSONException e) {
                Log.e(TAG, "Couldn't serialize saved json response");
            }
        }
        return oldResponses;
    }
}
