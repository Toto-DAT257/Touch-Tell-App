package com.example.ttapp.survey.fragments;

import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.ttapp.R;
import com.example.ttapp.survey.viewmodel.YesNoViewModel;

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

    private ArrayList<Integer> response = new ArrayList<>();

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

    private void initOnClickListeners() {
        yesnoResponseoption1.setOnClickListener(view -> {
            response.set(0, 1);
            surveyViewModel.nextQuestion();
        });

        yesnoResponseoption2.setOnClickListener(view -> {
            response.set(0, 2);
            surveyViewModel.nextQuestion();
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        surveyViewModel.saveResponse(response);
    }

}