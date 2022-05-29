package com.example.ttapp.survey.fragments;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.ttapp.R;

import java.util.ArrayList;

/**
 * Class for a fragment that presents a smiley-quartet question
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
                    changeSmiley(1, smileyqResponseoption1);
                    break;
                case 2:
                    changeSmiley(2, smileyqResponseoption2);
                    break;
                case 3:
                    changeSmiley(3, smileyqResponseoption3);
                    break;
                case 4:
                    changeSmiley(4, smileyqResponseoption4);
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

    private void changeSmiley(int chosen, ImageButton chosenSmiley) {
        clearSmileys();
        chosenSmiley.setBackgroundResource(R.drawable.ic_smiley_chosen);
        response.clear();
        response.add(chosen);
    }

    private void initOnClickListeners() {
        smileyqResponseoption1.setOnClickListener(view -> {
            changeSmiley(1, smileyqResponseoption1);
            surveyViewModel.saveResponse(response);
            surveyViewModel.nextQuestion();
        });

        smileyqResponseoption2.setOnClickListener(view -> {
            changeSmiley(2, smileyqResponseoption2);
            surveyViewModel.saveResponse(response);
            surveyViewModel.nextQuestion();
        });

        smileyqResponseoption3.setOnClickListener(view -> {
            changeSmiley(3, smileyqResponseoption3);
            surveyViewModel.saveResponse(response);
            surveyViewModel.nextQuestion();
        });

        smileyqResponseoption4.setOnClickListener(view -> {
            changeSmiley(4, smileyqResponseoption4);
            surveyViewModel.saveResponse(response);
            surveyViewModel.nextQuestion();
        });
    }

}