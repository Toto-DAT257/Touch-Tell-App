package com.example.ttapp.survey.fragments;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.ttapp.R;

public class EmailFragment extends QuestionFragment {

    private EditText emailResponse;
    private String response;

    @Override
    protected void setView(LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(R.layout.fragment_email, container, false);
    }

    @Override
    protected void initResponseOptions() {
        emailResponse = view.findViewById(R.id.emailResponse);
    }

    @Override
    protected void initSaveResponseObserver() {
        surveyViewModel.getSaveResponse().observe(getViewLifecycleOwner(), bool -> {
            response = emailResponse.getText().toString();
            surveyViewModel.saveResponse(response);
        });
    }

}