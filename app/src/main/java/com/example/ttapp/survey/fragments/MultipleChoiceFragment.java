package com.example.ttapp.survey.fragments;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.ttapp.R;

import java.util.ArrayList;

/**
 * Class for a fragment that presents a multiplechoice-question
 *
 * Used by: -
 * Uses: -
 *
 * Created by
 * @author Emma Stålberg
 */
public class MultipleChoiceFragment extends QuestionFragment {

    private final ArrayList<Integer> response = new ArrayList<>();

    private LinearLayout linearLayout;

    @Override
    protected void setView(LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(R.layout.fragment_multiple_choice, container, false);
    }

    @Override
    protected void initResponseOptions() {
        // TODO add logic when design is done. Will probably delete what´s below
//        linearLayout = view.findViewById(R.id.multipleChoiceLinearLayout);
//        for (int i = 0; i < 5; i++) {
//            CheckBox ch = new CheckBox(requireActivity());
//            ch.setText(i);
//            linearLayout.addView(ch);
//        }
    }

    @Override
    protected void initSaveResponseObserver() {
        surveyViewModel.getSaveResponse().observe(getViewLifecycleOwner(), bool -> {
            surveyViewModel.saveResponse(response);
        });
    }

}