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
        // todo
        surveyViewModel.containsAnsweredOptionsResponse().observe(getViewLifecycleOwner(), integers -> {
            if (integers.get(0) == NO) {
                responseOptionNo.setBackgroundResource(R.drawable.no_button_state_pressed);
                responseOptionNo.setTextColor(getResources().getColor(R.color.grey));
            } else {
                responseOptionYes.setBackgroundResource(R.drawable.yes_button_state_pressed);
                responseOptionYes.setTextColor(getResources().getColor(R.color.grey));
            }
        });
    }

    private void initOnClickListeners() {
        responseOptionYes.setOnClickListener(view -> {
            response.add(YES);
            surveyViewModel.saveResponse(response);
            surveyViewModel.nextQuestion();
        });

        responseOptionNo.setOnClickListener(view -> {
            response.add(NO);
            surveyViewModel.saveResponse(response);
            surveyViewModel.nextQuestion();
        });
    }

}