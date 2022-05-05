package com.example.ttapp.survey.fragments;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.ttapp.R;

import java.util.ArrayList;

/**
 * Class for a fragment that presents a smiley-quartet question
 * <p>
 * Used by: -
 * Uses: -
 * <p>
 * Created by
 *
 * @author Emma Stålberg
 */
public class SmileyQuartetFragment extends QuestionFragment {

    private ImageView smileyqResponseoption1;
    private ImageView smileyqResponseoption2;
    private ImageView smileyqResponseoption3;
    private ImageView smileyqResponseoption4;

    private final ArrayList<Integer> response = new ArrayList<>();

    @Override
    protected void setView(LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(R.layout.fragment_smiley_quartet, container, false);
    }

    @Override
    protected void initResponseOptions() {
        smileyqResponseoption1 = view.findViewById(R.id.smileyqResponseoption1);
        smileyqResponseoption2 = view.findViewById(R.id.smileyqResponseoption2);
        smileyqResponseoption3 = view.findViewById(R.id.smileyqResponseoption3);
        smileyqResponseoption4 = view.findViewById(R.id.smileyqResponseoption4);

        initOnClickListeners();
        initSaveResponseObserver();
    }

    @Override
    protected void initSaveResponseObserver() {
        surveyViewModel.getSaveResponse().observe(getViewLifecycleOwner(), bool -> {
            surveyViewModel.saveResponse(response);
        });
    }

    private void initOnClickListeners() {
        smileyqResponseoption1.setOnClickListener(view -> {
            response.add(1);
            surveyViewModel.saveResponse(response);
            surveyViewModel.nextQuestion();
        });

        smileyqResponseoption2.setOnClickListener(view -> {
            response.add(2);
            surveyViewModel.saveResponse(response);
            surveyViewModel.nextQuestion();
        });

        smileyqResponseoption3.setOnClickListener(view -> {
            response.add(3);
            surveyViewModel.saveResponse(response);
            surveyViewModel.nextQuestion();
        });

        smileyqResponseoption4.setOnClickListener(view -> {
            response.add(4);
            surveyViewModel.saveResponse(response);
            surveyViewModel.nextQuestion();
        });
    }

}