package com.example.ttapp.survey.fragments;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModelProvider;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import com.example.ttapp.R;
import com.example.ttapp.survey.viewmodel.NpsViewModel;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.ArrayList;

/**
 * Class for a fragment that presents a nps-question
 *
 * Used by: -
 * Uses: -
 *
 * Created by
 * @author Emma Stålberg
 */
public class NpsFragment extends QuestionFragment {

    private NpsViewModel npsViewModel;

    private SeekBar npsSeekbar;
    private ArrayList<Integer> answer;

    @Override
    protected void setView(LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(R.layout.fragment_nps, container, false);
    }

    @Override
    protected void setViewModel() {
        npsViewModel = new ViewModelProvider(requireActivity()).get(NpsViewModel.class);
    }

    @Override
    protected void initAnsweroptions() {
        npsSeekbar = view.findViewById(R.id.npsSeekbar);
        answer = new ArrayList<Integer>();
        answer.set(0, npsSeekbar.getProgress());

        npsSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                answer.set(0, npsSeekbar.getProgress());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        surveyViewModel.saveAnswer(answer, surveyViewModel.getCurrentQuestionId());
    }

}