package com.example.ttapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.ttapp.R;
import com.example.ttapp.viewmodel.SurveyViewModel;

public class FirstQuestionFragment extends QuestionFragment{

    private View view;
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

    }

    @Override
    protected void setAnsweroptions() {
        firstQAnsweroption1 = view.findViewById(R.id.firstQAnsweroption1);
        firstQAnsweroption2 = view.findViewById(R.id.firstQAnsweroption2);
    }

}
