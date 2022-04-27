package com.example.ttapp.survey.fragments;

import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.SeekBar;

import com.example.ttapp.R;
import com.example.ttapp.survey.viewmodel.NpsViewModel;

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
    private ArrayList<Integer> response = new ArrayList<>();

    @Override
    protected void setView(LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(R.layout.fragment_nps, container, false);
    }

    @Override
    protected void setViewModel() {
        npsViewModel = new ViewModelProvider(requireActivity()).get(NpsViewModel.class);
    }

    @Override
    protected void initResponseoptions() {
        npsSeekbar = view.findViewById(R.id.npsSeekbar);
        if (response.isEmpty()) {
            response.add(npsSeekbar.getProgress());
        } else {
            response.set(0, npsSeekbar.getProgress());
        }

        npsSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                response.set(0, npsSeekbar.getProgress());
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
    protected void initSaveResponseObserver() {
        surveyViewModel.getSaveResponse().observe(getViewLifecycleOwner(), bool -> {
            surveyViewModel.saveResponse(response);
        });
    }



}