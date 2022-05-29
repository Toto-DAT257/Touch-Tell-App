package com.example.ttapp.survey.fragments;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.example.ttapp.R;
import com.google.android.material.slider.Slider;

import java.util.ArrayList;

/**
 * Class for a fragment that presents a nps-question
 */
public class NpsFragment extends QuestionFragment {

    private Slider slider;

    private final ArrayList<Integer> response = new ArrayList<>();

    @Override
    protected void setView(LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(R.layout.fragment_nps, container, false);
    }

    @Override
    protected void initResponseOptions() {
        slider = view.findViewById(R.id.NPSslider);

        if (response.isEmpty()) {
            response.add((int) slider.getValue());
        } else {
            response.set(0, (int) slider.getValue());
        }

        slider.addOnSliderTouchListener(new Slider.OnSliderTouchListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onStartTrackingTouch(@NonNull Slider slider) {

            }

            @SuppressLint("RestrictedApi")
            @Override
            public void onStopTrackingTouch(@NonNull Slider slider) {
                response.set(0, (int) slider.getValue());
                surveyViewModel.saveResponse(response);
            }
        });
    }

    @Override
    protected void initSaveResponseObserver() {
        surveyViewModel.getSaveResponse().observe(getViewLifecycleOwner(), bool -> surveyViewModel.saveResponse(response));
    }

    @Override
    protected void initResponseObserver() {
        surveyViewModel.containsAnsweredOptionsResponse().observe(getViewLifecycleOwner(), integers -> {
            response.set(0, integers.get(0));
            slider.setValue(response.get(0));
        });
    }

}