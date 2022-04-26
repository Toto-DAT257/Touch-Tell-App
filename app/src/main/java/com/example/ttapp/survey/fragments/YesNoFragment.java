package com.example.ttapp.survey.fragments;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.ttapp.R;
import com.example.ttapp.survey.model.answers.YesNoAnswer;
import com.example.ttapp.survey.viewmodel.SurveyViewModel;
import com.example.ttapp.survey.viewmodel.YesNoViewModel;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * Class for a fragment that presents a yes no question
 *
 * Used by: -
 * Uses: -
 *
 * Created by
 * @author Emma Stålberg
 */
public class YesNoFragment extends QuestionFragment {

    private YesNoViewModel yesNoViewModel;
    private String questionId;

    private Button yesnoAnsweroption1;
    private Button yesnoAnsweroption2;

    private int answer;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        yesNoViewModel.answer.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                surveyViewModel.putAnswer(s);
            }
        });

        return inflater.inflate(R.layout.fragment_yes_no, container, false);
    }

    @Override
    protected void setView(LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(R.layout.fragment_yes_no, container, false);
    }

    @Override
    protected void setViewModel() {
        yesNoViewModel = new ViewModelProvider(requireActivity()).get(YesNoViewModel.class);
    }

    @Override
    protected void initAnsweroptions() {
        yesnoAnsweroption1 = view.findViewById(R.id.yesnoAnsweroption1);
        yesnoAnsweroption2 = view.findViewById(R.id.yesnoAnsweroption2);
        initOnClickListeners();
    }

    private void initOnClickListeners() {
        yesnoAnsweroption1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                answer = YesNoAnswer.YES;
            }
        });

        yesnoAnsweroption2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                answer = YesNoAnswer.NO;
            }
        });
    }

    // TODO change try catch
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onStop() {
        super.onStop();
        try {
            yesNoViewModel.SaveAnswer(answer, surveyViewModel.getCurrentQuestionId());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }


}