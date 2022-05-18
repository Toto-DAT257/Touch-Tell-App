package com.example.ttapp.survey.fragments;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

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

    @Override
    protected void setView(LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(R.layout.fragment_email, container, false);
    }

    @Override
    protected void initResponseOptions() {
        emailResponse = view.findViewById(R.id.emailResponse);
        emailResponse.setOnEditorActionListener((textView, actionId, keyEvent) -> {
            boolean handled = false;
            if (actionId == EditorInfo.IME_ACTION_GO) {
                save();
                surveyViewModel.nextQuestion();
                handled = true;
            }
            return handled;
        });
    }

    @Override
    protected void initSaveResponseObserver() {
        surveyViewModel.getSaveResponse().observe(getViewLifecycleOwner(), bool -> save());
    }

    private void save() {
        String response = emailResponse.getText().toString();
        surveyViewModel.saveResponse(response);
    }

    @Override
    protected void initResponseObserver() {
        surveyViewModel.containsCommentresponse().observe(getViewLifecycleOwner(), s -> emailResponse.setText(s));
    }

}