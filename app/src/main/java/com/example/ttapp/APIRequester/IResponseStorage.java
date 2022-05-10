package com.example.ttapp.APIRequester;

import java.util.List;

public interface IResponseStorage<T> {

    void saveResponse(T objectToSave);

    void removeResponse(T objectToRemove);

    List<T> getAllResponses();

}
