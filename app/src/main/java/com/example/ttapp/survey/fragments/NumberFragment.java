package com.example.ttapp.survey.fragments;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

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

    private EditText numberResponse;

    @Override
    protected void setView(LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(R.layout.fragment_number, container, false);
    }

    @Override
    protected void initResponseOptions() {
        numberResponse = view.findViewById(R.id.numberResponse);
        numberResponse.setOnEditorActionListener((textView, actionId, keyEvent) -> {
            boolean handled = false;
            if (actionId == EditorInfo.IME_ACTION_NEXT) {
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
        String response = numberResponse.getText().toString();
        surveyViewModel.saveResponse(response);
    }

    @Override
    protected void initResponseObserver() {
        surveyViewModel.containsCommentresponse().observe(getViewLifecycleOwner(), s -> numberResponse.setText(s));
    }

}