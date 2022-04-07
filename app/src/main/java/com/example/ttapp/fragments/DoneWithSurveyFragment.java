package com.example.ttapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.ttapp.R;
import com.example.ttapp.viewmodel.SurveyViewModel;

/**
 * Class for a fragment that is shown when a survey is done
 *
 * Used by: -
 * Uses: -
 *
 * Created by
 * @author Emma Stålberg, Amanda Cyrén
 */
public class DoneWithSurveyFragment extends Fragment {

    private View view;
    private SurveyViewModel surveyViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_done_with_survey, container, false);

        surveyViewModel = new ViewModelProvider(requireActivity()).get(SurveyViewModel.class);

        return view;
    }

}
