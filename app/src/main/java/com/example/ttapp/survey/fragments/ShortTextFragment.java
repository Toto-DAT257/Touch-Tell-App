package com.example.ttapp.survey.fragments;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.ttapp.R;

/**
 * Class for a fragment that presents a shorttext-question
 *
 * Used by: -
 * Uses: -
 *
 * Created by
 * @author Emma Stålberg
 */
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
        surveyViewModel.getSaveResponse().observe(getViewLifecycleOwner(), bool -> {
            response = shortTextResponse.getText().toString();
            surveyViewModel.saveResponse(response);
        });
    }

}