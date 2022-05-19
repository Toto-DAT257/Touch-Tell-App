package com.example.ttapp.APIRequester;

public interface Response<T> {

    void response(T object);

    void error(T object);

}
