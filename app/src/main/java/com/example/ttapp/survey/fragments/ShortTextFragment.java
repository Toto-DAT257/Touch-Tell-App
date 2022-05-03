package com.example.ttapp.survey.fragments;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.ttapp.R;

public class ShortTextFragment extends QuestionFragment {

    private EditText shortTextResponse;
    private String response;

    @Override
    protected void setView(LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(R.layout.fragment_short_text, container, false);
    }

    @Override
    protected void initResponseOptions() {
        shortTextResponse = view.findViewById(R.id.shortTextResponse);
    }

    @Override
    protected void initSaveResponseObserver() {

    }

}