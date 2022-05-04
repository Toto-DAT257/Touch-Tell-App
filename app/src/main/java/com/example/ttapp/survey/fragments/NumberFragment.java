package com.example.ttapp.survey.fragments;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.ttapp.R;

public class NumberFragment extends QuestionFragment {

    private String response;
    private EditText numberResponse;

    @Override
    protected void setView(LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(R.layout.fragment_number, container, false);
    }

    @Override
    protected void initResponseOptions() {
        numberResponse = view.findViewById(R.id.numberResponse);
    }

    @Override
    protected void initSaveResponseObserver() {
        surveyViewModel.getSaveResponse().observe(getViewLifecycleOwner(), bool -> {
            response = numberResponse.getText().toString();
            surveyViewModel.saveResponse(response);
        });
    }

}