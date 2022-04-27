package com.example.ttapp.survey.fragments;

import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.ttapp.R;
import com.example.ttapp.survey.viewmodel.CommentViewModel;

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

    private CommentViewModel commentViewModel;

    private EditText commentAnswer;
    private String answer;

    @Override
    protected void setView(LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(R.layout.fragment_comment, container, false);
    }

    @Override
    protected void setViewModel() {
        commentViewModel = new ViewModelProvider(requireActivity()).get(CommentViewModel.class);
    }

    @Override
    protected void initAnsweroptions() {
        commentAnswer = view.findViewById(R.id.commentAnswer);
    }

    @Override
    public void onStop() {
        super.onStop();
        answer = commentAnswer.getText().toString();
        surveyViewModel.saveAnswer(answer);
    }

}