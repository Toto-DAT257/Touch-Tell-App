package com.example.ttapp.survey.fragments;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.lifecycle.Observer;

import com.example.ttapp.R;

/**
 * Class for a fragment that presents an email-question
 *
 * Used by: -
 * Uses: -
 *
 * Created by
 * @author Emma Stålberg
 */
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

    @Override
    protected void initResponseObserver() {
        surveyViewModel.containsCommentresponse().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                emailResponse.setText(s);
            }
        });
    }

}