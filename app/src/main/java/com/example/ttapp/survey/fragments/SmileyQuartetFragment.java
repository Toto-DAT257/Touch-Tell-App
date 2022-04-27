package com.example.ttapp.survey.fragments;

import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.ttapp.R;
import com.example.ttapp.survey.model.answers.SmileyQuartet;
import com.example.ttapp.survey.viewmodel.SmileyQuartetViewModel;

import java.util.ArrayList;

/**
 * Class for a fragment that presents a smiley-quartet question
 *
 * Used by: -
 * Uses: -
 *
 * Created by
 * @author Emma Stålberg
 */
public class SmileyQuartetFragment extends QuestionFragment {

    private SmileyQuartetViewModel smileyQuartetViewModel;

    private Button smileyqResponseoption1;
    private Button smileyqResponseoption2;
    private Button smileyqResponseoption3;
    private Button smileyqResponseoption4;

    private ArrayList<Integer> response = new ArrayList<>();

    @Override
    protected void setView(LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(R.layout.fragment_smiley_quartet, container, false);
    }

    @Override
    protected void setViewModel() {
        smileyQuartetViewModel = new ViewModelProvider(requireActivity()).get(SmileyQuartetViewModel.class);
    }

    @Override
    protected void initResponseoptions() {
        smileyqResponseoption1 = view.findViewById(R.id.smileyqResponseoption1);
        smileyqResponseoption2 = view.findViewById(R.id.smileyqResponseoption2);
        smileyqResponseoption3 = view.findViewById(R.id.smileyqResponseoption3);
        smileyqResponseoption4 = view.findViewById(R.id.smileyqResponseoption4);

        initOnClickListeners();
    }

    private void initOnClickListeners() {
        smileyqResponseoption1.setOnClickListener(view -> {
            if (response.isEmpty()){
                response.add(1);
            } else {
                response.set(0, 1);
            }
            surveyViewModel.nextQuestion();
        });

        smileyqResponseoption2.setOnClickListener(view -> {
            if (response.isEmpty()){
                response.add(1);
            } else {
                response.set(0, 2);
            }
            surveyViewModel.nextQuestion();
        });

        smileyqResponseoption3.setOnClickListener(view -> {
            if (response.isEmpty()){
                response.add(1);
            } else {
                response.set(0, 3);
            }
            surveyViewModel.nextQuestion();
        });

        smileyqResponseoption4.setOnClickListener(view -> {
            if (response.isEmpty()){
                response.add(1);
            } else {
                response.set(0, 4);
            }
            surveyViewModel.nextQuestion();
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        surveyViewModel.saveResponse(response);
    }

}