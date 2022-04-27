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

import com.example.ttapp.R;
import com.example.ttapp.databinding.FragmentSurveyBinding;
import com.example.ttapp.survey.fragments.NpsFragment;
import com.example.ttapp.survey.fragments.SmileyQuartetFragment;
import com.example.ttapp.survey.fragments.YesNoFragment;
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

        backButton = binding.surveyBackButton;
        nextButton = binding.surveyNextButton;
        questionFragmentContainer = binding.questionFragmentContainer;
        questionTextView = binding.questionTextView;

        surveyViewModel = new ViewModelProvider(requireActivity()).get(SurveyViewModel.class);
        surveyViewModel.loadQuestions(getContext(), getActivity());
        surveyViewModel.getJsonIsRecievedIndcator().observe(getViewLifecycleOwner(), bool ->{
            thingsToDoAfterJsonIsSet();
        });

        return root;
    }

    private void thingsToDoAfterJsonIsSet() {
        surveyViewModel.getCurrentQuestionText().observe(getViewLifecycleOwner(), questionTextView::setText);

        surveyViewModel.getCurrentQuestionType().observe(getViewLifecycleOwner(), questionType -> {
            switch (questionType) {
                case "smiley-quartet":
                    // TODO: Populate the FragmentContainer with appropriate QuestionFragment
                    requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.questionFragmentContainer, new SmileyQuartetFragment()).commit();
                case QuestionType.YES_NO:
                    requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.questionFragmentContainer, new YesNoFragment()).commit();
                case QuestionType.NPS:
                    requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.questionFragmentContainer, new NpsFragment()).commit();
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
    }

    private void next() {
        surveyViewModel.nextQuestion();
    }

    private void previous() {
        surveyViewModel.previousQuestion();
    }

}