package com.example.ttapp.survey.fragments;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.ttapp.R;
import com.example.ttapp.survey.model.answers.SmileyQuartet;
import com.example.ttapp.survey.viewmodel.SmileyQuartetViewModel;

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

    private Button smileyqAnsweroption1;
    private Button smileyqAnsweroption2;
    private Button smileyqAnsweroption3;
    private Button smileyqAnsweroption4;

    private int answer;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_smiley_quartet, container, false);
    }

    @Override
    protected void setView(LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(R.layout.fragment_smiley_quartet, container, false);
    }

    @Override
    protected void setViewModel() {
        smileyQuartetViewModel = new ViewModelProvider(requireActivity()).get(SmileyQuartetViewModel.class);
    }

    @Override
    protected void initAnsweroptions() {
        smileyqAnsweroption1 = view.findViewById(R.id.smileyqAnsweroption1);
        smileyqAnsweroption2 = view.findViewById(R.id.smileyqAnsweroption2);
        smileyqAnsweroption3 = view.findViewById(R.id.smileyqAnsweroption3);
        smileyqAnsweroption4 = view.findViewById(R.id.smileyqAnsweroption4);

        initOnClickListeners();
    }

    private void initOnClickListeners() {
        smileyqAnsweroption1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                answer = SmileyQuartet.SADDEST;
            }
        });

        smileyqAnsweroption2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                answer = SmileyQuartet.SAD;
            }
        });

        smileyqAnsweroption3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                answer = SmileyQuartet.HAPPY;
            }
        });

        smileyqAnsweroption4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                answer = SmileyQuartet.HAPPIEST;
            }
        });
    }

}