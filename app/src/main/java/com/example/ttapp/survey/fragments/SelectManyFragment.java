package com.example.ttapp.survey.fragments;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.ttapp.R;

public class SelectManyFragment extends QuestionFragment {

    @Override
    protected void setView(LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(R.layout.fragment_select_many, container, false);
    }

    @Override
    protected void initResponseOptions() {

    }

    @Override
    protected void initSaveResponseObserver() {

    }

}