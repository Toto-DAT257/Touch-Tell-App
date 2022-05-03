package com.example.ttapp.survey.fragments;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.example.ttapp.R;
import com.google.android.material.slider.Slider;

import java.util.ArrayList;

/**
 * Class for a fragment that presents a nps-question
 * <p>
 * Used by: -
 * Uses: -
 * <p>
 * Created by
 *
 * @author Emma Stålberg
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
        slider = view.findViewById(R.id.slider);
        if (response.isEmpty()) {
            response.add((int) slider.getValue());
        } else {
            response.set(0, (int) slider.getValue());
        }
        surveyViewModel.saveResponse(response); // save 5 as default TODO give user opportunity to skip question

        slider.addOnSliderTouchListener(new Slider.OnSliderTouchListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onStartTrackingTouch(@NonNull Slider slider) {

            }

            @SuppressLint("RestrictedApi")
            @Override
            public void onStopTrackingTouch(@NonNull Slider slider) {
                response.set(0, (int) slider.getValue());
                // Save every time on change because listener does not trigger correctly for this fragment
                surveyViewModel.saveResponse(response);
            }
        });
    }

    @Override
    protected void initSaveResponseObserver() {
        surveyViewModel.getSaveResponse().observe(getViewLifecycleOwner(), bool -> {
            surveyViewModel.saveResponse(response); // Not working, very strange. SmileyQuartet seems to trigger instead but to late. cant find the bug.
        });
    }


}