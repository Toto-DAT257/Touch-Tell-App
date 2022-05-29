package com.example.ttapp.survey.fragments;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import com.example.ttapp.R;

/**
 * Class for a fragment that presents a comment-question
 */
public class CommentFragment extends QuestionFragment {

    private EditText commentResponse;

    @Override
    protected void setView(LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(R.layout.fragment_comment, container, false);
    }

    @Override
    protected void initResponseOptions() {
        commentResponse = view.findViewById(R.id.commentResponse);

        // To have multiline with an action as enter key
        commentResponse.setHorizontallyScrolling(false);
        commentResponse.setMaxLines(Integer.MAX_VALUE);
        commentResponse.setOnEditorActionListener((textView, actionId, keyEvent) -> {
            boolean handled = false;
            if (actionId == EditorInfo.IME_ACTION_GO) {
                closeKeyboard();
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
        String response = commentResponse.getText().toString();
        surveyViewModel.saveResponse(response);
    }

    @Override
    protected void initResponseObserver() {
        surveyViewModel.containsCommentresponse().observe(getViewLifecycleOwner(), s -> commentResponse.setText(s));
    }

}