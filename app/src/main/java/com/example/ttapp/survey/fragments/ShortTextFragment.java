package com.example.ttapp.survey.fragments;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import com.example.ttapp.R;

/**
 * Class for a fragment that presents a shortText-question
 * <p>
 * Used by: -
 * Uses: -
 * <p>
 * Created by
 *
 * @author Emma Stålberg
 */
public class ShortTextFragment extends QuestionFragment {

    private EditText shortTextResponse;

    @Override
    protected void setView(LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(R.layout.fragment_short_text, container, false);
    }

    @Override
    protected void initResponseOptions() {
        shortTextResponse = view.findViewById(R.id.shortTextResponse);

        // To have multiline with an action as enter key
        shortTextResponse.setHorizontallyScrolling(false);
        shortTextResponse.setMaxLines(Integer.MAX_VALUE);
        shortTextResponse.setOnEditorActionListener((textView, actionId, keyEvent) -> {
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
        String response = shortTextResponse.getText().toString();
        surveyViewModel.saveResponse(response);
    }

    @Override
    protected void initResponseObserver() {
        surveyViewModel.containsCommentresponse().observe(getViewLifecycleOwner(), s -> shortTextResponse.setText(s));
    }

}