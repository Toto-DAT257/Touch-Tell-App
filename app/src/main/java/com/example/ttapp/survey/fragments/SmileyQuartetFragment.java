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
    private String questionId;

    private Button smileyqAnsweroption1;
    private Button smileyqAnsweroption2;
    private Button smileyqAnsweroption3;
    private Button smileyqAnsweroption4;

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

}