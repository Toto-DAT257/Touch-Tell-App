package com.example.ttapp.survey.fragments;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.lifecycle.Observer;

import com.example.ttapp.R;

/**
 * Class for a fragment that presents a number-question
 *
 * Used by: -
 * Uses: -
 *
 * Created by
 * @author Emma Stålberg
 */
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

    @Override
    protected void initResponseObserver() {
        surveyViewModel.containsCommentresponse().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                numberResponse.setText(s);
            }
        });
    }

}