package com.example.ttapp.survey.fragments;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.ttapp.R;

/**
 * Class for a fragment that presents a comment-question
 *
 * Used by: -
 * Uses: -
 *
 * Created by
 * @author Emma Stålberg
 */
public class CommentFragment extends QuestionFragment {

    private EditText commentResponse;
    private String response;

    @Override
    protected void setView(LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(R.layout.fragment_comment, container, false);
    }

    @Override
    protected void initResponseoptions() {
        commentResponse = view.findViewById(R.id.commentResponse);
        initSaveResponseObserver();
    }

    @Override
    protected void initSaveResponseObserver(){
        surveyViewModel.getSaveResponse().observe(getViewLifecycleOwner(), bool -> {
            response = commentResponse.getText().toString();
            surveyViewModel.saveResponse(response);
        });
    }

}