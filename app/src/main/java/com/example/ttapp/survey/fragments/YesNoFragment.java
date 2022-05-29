package com.example.ttapp.survey.fragments;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;


import com.example.ttapp.R;

import java.util.ArrayList;

/**
 * Class for a fragment that presents a yes / no question
 */
public class YesNoFragment extends QuestionFragment {

    private Button responseOptionYes;
    private Button responseOptionNo;
    private static final int NO = 1;
    private static final int YES = 2;


    private final ArrayList<Integer> response = new ArrayList<>();

    @Override
    protected void setView(LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(R.layout.fragment_yes_no, container, false);
    }

    @Override
    protected void initResponseOptions() {
        responseOptionYes = view.findViewById(R.id.responseOptionYes);
        responseOptionNo = view.findViewById(R.id.responseOptionNo);
        initOnClickListeners();
    }

    @Override
    protected void initSaveResponseObserver() {
        surveyViewModel.getSaveResponse().observe(getViewLifecycleOwner(), bool -> surveyViewModel.saveResponse(response));
    }

    @Override
    protected void initResponseObserver() {
        surveyViewModel.containsAnsweredOptionsResponse().observe(getViewLifecycleOwner(), integers -> {
            if (integers.get(0) == NO) {
                chooseAnswer(NO);
            } else {
                chooseAnswer(YES);
            }
        });
    }

    private void changeUIAnswerTo(int answer) {
        if(answer == NO) {
            responseOptionNo.setBackgroundResource(R.drawable.yesno_button_state_pressed);
            responseOptionYes.setBackgroundResource(R.drawable.yesno_button_state_not_pressed);
        }
        if(answer == YES){
            responseOptionNo.setBackgroundResource(R.drawable.yesno_button_state_not_pressed);
            responseOptionYes.setBackgroundResource(R.drawable.yesno_button_state_pressed);
        }
    }

    private void initOnClickListeners() {
        responseOptionNo.setOnClickListener(view -> {
            chooseAnswer(NO);
            surveyViewModel.saveResponse(response);
            surveyViewModel.nextQuestion();
        });

        responseOptionYes.setOnClickListener(view -> {
            chooseAnswer(YES);
            surveyViewModel.saveResponse(response);
            surveyViewModel.nextQuestion();
        });
    }

    private void chooseAnswer(int answer) {
        changeUIAnswerTo(answer);
        addResponse(answer);
    }

    private void addResponse(int answer) {
        response.clear();
        response.add(answer);
    }

}