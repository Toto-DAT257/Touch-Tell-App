package com.example.ttapp.survey.fragments;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;


import com.example.ttapp.R;

import java.util.ArrayList;

/**
 * Class for a fragment that presents a yesno-question
 * <p>
 * Used by: -
 * Uses: -
 * <p>
 * Created by
 *
 * @author Emma Stålberg
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
                responseOptionNo.setBackgroundResource(R.drawable.yesno_button_state_pressed);
                response.add(NO);
            } else {
                responseOptionYes.setBackgroundResource(R.drawable.yesno_button_state_pressed);
                response.add(YES);
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
            changeUIAnswerTo(NO);
            response.add(NO);
            surveyViewModel.saveResponse(response);
            surveyViewModel.nextQuestion();
        });

        responseOptionYes.setOnClickListener(view -> {
            changeUIAnswerTo(YES);
            response.add(YES);
            surveyViewModel.saveResponse(response);
            surveyViewModel.nextQuestion();
        });
    }

}