package com.example.ttapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.ttapp.viewmodel.SurveyViewModel;

public abstract class QuestionFragment extends Fragment {

    View view;
    SurveyViewModel surveyViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        setView(inflater, container);
        setSurveyViewModel();
        setQuestion();
        setAnsweroptions();

        return view;
    }

    protected abstract void setView(LayoutInflater inflater, ViewGroup container);

    private void setSurveyViewModel() {
        surveyViewModel = new ViewModelProvider(requireActivity()).get(SurveyViewModel.class);
    }

    protected abstract void setQuestion();

    protected abstract void setAnsweroptions();

}
