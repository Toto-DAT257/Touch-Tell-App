package com.example.ttapp.survey.fragments;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.navigation.Navigation;

import com.example.ttapp.R;
import com.example.ttapp.survey.viewmodel.SurveyViewModel;

/**
 * Class for a fragment that presents the first question in the survey
 *
 * Used by: -
 * Uses: -
 *
 * Created by
 * @author Emma Stålberg, Amanda Cyrén
 */
public class FirstQuestionFragment extends QuestionFragment{

    private TextView question1;
    private Button firstQAnsweroption1;
    private Button firstQAnsweroption2;
    private SurveyViewModel surveyViewModel;

    @Override
    protected void setView(LayoutInflater inflater, ViewGroup container) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_first_question, container, false);
    }

    @Override
    protected void setQuestion() {
        // is probably going to be changed further on
        question1 = view.findViewById(R.id.question1);
    }

    @Override
    protected void setAnsweroptions() {
        // is probably going to be changed further on

        firstQAnsweroption1 = view.findViewById(R.id.firstQAnsweroption1);
        firstQAnsweroption2 = view.findViewById(R.id.firstQAnsweroption2);
    }

    // Now it only navigates to the final response fragment
    @Override
    protected void setAnsweroptionsOnclicklisteners() {
        firstQAnsweroption1.setOnClickListener(view1 -> {
            Navigation.findNavController(view).navigate(R.id.action_firstQuestionFragment_to_doneWithSurveyFragment);
        });
        firstQAnsweroption2.setOnClickListener(view1 -> {
            Navigation.findNavController(view).navigate(R.id.action_firstQuestionFragment_to_doneWithSurveyFragment);
        });
    }


}
