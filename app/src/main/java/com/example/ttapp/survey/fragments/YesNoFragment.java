package com.example.ttapp.survey.fragments;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.ttapp.R;

import java.util.ArrayList;

/**
 * Class for a fragment that presents a yes no question
 *
 * Used by: -
 * Uses: -
 *
 * Created by
 * @author Emma Stålberg
 */
public class YesNoFragment extends QuestionFragment {

    private Button yesnoResponseoption1;
    private Button yesnoResponseoption2;

    private final ArrayList<Integer> response = new ArrayList<>();

    @Override
    protected void setView(LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(R.layout.fragment_yes_no, container, false);
    }

    @Override
    protected void initResponseoptions() {
        yesnoResponseoption1 = view.findViewById(R.id.yesnoResponseoption1);
        yesnoResponseoption2 = view.findViewById(R.id.yesnoResponseoption2);
        initOnClickListeners();
    }

    @Override
    protected void initSaveResponseObserver() {
        surveyViewModel.getSaveResponse().observe(getViewLifecycleOwner(), bool -> {
            surveyViewModel.saveResponse(response);
        });
    }

    private void initOnClickListeners() {
        yesnoResponseoption1.setOnClickListener(view -> {
            response.add(1);
            surveyViewModel.saveResponse(response);
            surveyViewModel.nextQuestion();
        });

        yesnoResponseoption2.setOnClickListener(view -> {
            response.add(2);
            surveyViewModel.saveResponse(response);
            surveyViewModel.nextQuestion();
        });
    }

}