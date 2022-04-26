package com.example.ttapp.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.ttapp.databinding.FragmentSurveyBinding;
import com.example.ttapp.survey.viewmodel.SurveyViewModel;

/**
 * Class for a fragment that fetches the survey questions.
 *
 * @author Simon Holst, Amanda Cyrén, Emma Stålberg
 */
public class SurveyFragment extends Fragment {

    FragmentSurveyBinding binding;
    SurveyViewModel surveyViewModel;
    Button backButton;
    Button nextButton;
    FragmentContainerView questionFragmentContainer;
    TextView questionTextView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentSurveyBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();

        surveyViewModel = new ViewModelProvider(requireActivity()).get(SurveyViewModel.class);
        backButton = binding.surveyBackButton;
        nextButton = binding.surveyNextButton;
        questionFragmentContainer = binding.questionFragmentContainer;
        questionTextView = binding.questionTextView;

        surveyViewModel.getCurrentQuestion().observe(getViewLifecycleOwner(), questionTextView::setText);
        backButton.setOnClickListener(click -> previous());
        nextButton.setOnClickListener(click -> next());

        return root;
    }

    private void next() {
        // TODO: attempt to move to next question
    }

    private void previous() {
        // TODO: attempt to move to previous question
    }

}