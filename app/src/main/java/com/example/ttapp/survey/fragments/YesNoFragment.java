package com.example.ttapp.survey.fragments;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.lifecycle.Observer;

import com.example.ttapp.R;

import java.util.ArrayList;
import java.util.List;

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

    private Button yesnoResponseoption1;
    private Button yesnoResponseoption2;

    private final ArrayList<Integer> response = new ArrayList<>();

    @Override
    protected void setView(LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(R.layout.fragment_yes_no, container, false);
    }

    @Override
    protected void initResponseOptions() {
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

    @Override
    protected void initResponseObserver() {
        // todo
        surveyViewModel.containsAnsweredOptionsResponse().observe(getViewLifecycleOwner(), new Observer<List<Integer>>() {
            @Override
            public void onChanged(List<Integer> integers) {
                if (integers.get(0) == 1) {
                    yesnoResponseoption2.setBackgroundResource(R.drawable.button_state_pressed);
                } else {
                    yesnoResponseoption1.setBackgroundResource(R.drawable.button_state_pressed);
                }
            }
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