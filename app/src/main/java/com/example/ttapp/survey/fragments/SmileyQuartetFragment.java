package com.example.ttapp.survey.fragments;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageButton;
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

    private ImageButton smileyqResponseoption1;
    private ImageButton smileyqResponseoption2;
    private ImageButton smileyqResponseoption3;
    private ImageButton smileyqResponseoption4;

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
    }

    @Override
    protected void initSaveResponseObserver() {
        surveyViewModel.getSaveResponse().observe(getViewLifecycleOwner(), bool -> surveyViewModel.saveResponse(response));
    }

    @Override
    protected void initResponseObserver() {
        surveyViewModel.containsAnsweredOptionsResponse().observe(getViewLifecycleOwner(), integers -> {
            switch (integers.get(0)) {
                case 1:
                    changeSmiley(smileyqResponseoption1);
                    break;
                case 2:
                    changeSmiley(smileyqResponseoption2);
                    break;
                case 3:
                    changeSmiley(smileyqResponseoption3);
                    break;
                case 4:
                    changeSmiley(smileyqResponseoption4);
                    break;
            }
        });
    }

    private void clearSmileys() {
        smileyqResponseoption1.setBackgroundResource(0);
        smileyqResponseoption2.setBackgroundResource(0);
        smileyqResponseoption3.setBackgroundResource(0);
        smileyqResponseoption4.setBackgroundResource(0);
    }

    private void changeSmiley(ImageButton chosenSmiley) {
        clearSmileys();
        chosenSmiley.setBackgroundResource(R.drawable.ic_smiley_chosen);
    }

    private void initOnClickListeners() {
        smileyqResponseoption1.setOnClickListener(view -> {
            changeSmiley(smileyqResponseoption1);
            response.add(1);
            surveyViewModel.saveResponse(response);
            surveyViewModel.nextQuestion();
        });

        smileyqResponseoption2.setOnClickListener(view -> {
            changeSmiley(smileyqResponseoption2);
            response.add(2);
            surveyViewModel.saveResponse(response);
            surveyViewModel.nextQuestion();
        });

        smileyqResponseoption3.setOnClickListener(view -> {
            changeSmiley(smileyqResponseoption3);
            response.add(3);
            surveyViewModel.saveResponse(response);
            surveyViewModel.nextQuestion();
        });

        smileyqResponseoption4.setOnClickListener(view -> {
            changeSmiley(smileyqResponseoption4);
            response.add(4);
            surveyViewModel.saveResponse(response);
            surveyViewModel.nextQuestion();
        });
    }

}