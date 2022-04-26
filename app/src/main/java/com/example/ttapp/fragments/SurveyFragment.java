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
import com.example.ttapp.survey.model.QuestionType;
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

        surveyViewModel.getCurrentQuestionText().observe(getViewLifecycleOwner(), questionTextView::setText);

        surveyViewModel.getCurrentQuestionType().observe(getViewLifecycleOwner(), questionType -> {
            // TODO: Might not recognize change between two identical question types
            switch (questionType) {
                case "smiley-quartet":
                    // TODO: Populate the FragmentContainer with appropriate QuestionFragment
                    // supportFragmentManager.beginTransaction().add(R.id.fragment_container_view, myFragmentInstance).commit()
                case QuestionType.YES_NO:
                case QuestionType.NPS:
                case "multiple-choice":
                case "select-many":
                case "comment":
                case "short-text":
                case "typeahead":
                case "number":
                case "email":
                case "smiley-comment":
            }
        });
        backButton.setOnClickListener(click -> previous());
        nextButton.setOnClickListener(click -> next());

        surveyViewModel.loadQuestions(getContext(), getActivity());
        return root;
    }

    private void next() {
        surveyViewModel.nextQuestion();
    }

    private void previous() {
        surveyViewModel.previousQuestion();
    }

}