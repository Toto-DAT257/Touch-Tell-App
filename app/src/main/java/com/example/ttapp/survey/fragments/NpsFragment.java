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
    private int answer;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_nps, container, false);
    }

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
        answer = npsSeekbar.getProgress();

        npsSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                answer = npsSeekbar.getProgress();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    // TODO change try catch
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onStop() {
        super.onStop();
        try {
            npsViewModel.saveAnswer(answer, surveyViewModel.getCurrentQuestionId());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

}