package com.example.ttapp.survey.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.ttapp.survey.viewmodel.SurveyViewModel;

/**
 * An abstract class for the questionfragments.
 *
 * Used by: -
 * Uses: -
 *
 * Created by
 * @author Emma Stålberg, Amanda Cyrén
 */
public abstract class QuestionFragment extends Fragment {

    View view;
    SurveyViewModel surveyViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        setView(inflater, container);
        setSurveyViewModel();
        initResponseOptions();
        initSaveResponseObserver();

        return view;
    }

    protected abstract void setView(LayoutInflater inflater, ViewGroup container);

    private void setSurveyViewModel() {
        surveyViewModel = new ViewModelProvider(requireActivity()).get(SurveyViewModel.class);
    }

    protected abstract void initResponseOptions();

    protected abstract void initSaveResponseObserver();

}
