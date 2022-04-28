package com.example.ttapp.survey.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.ttapp.R;
import com.example.ttapp.databinding.FragmentSurveyBinding;
import com.example.ttapp.survey.fragments.CommentFragment;
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
    Button submitButton;
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
        surveyViewModel.getJsonIsRecievedIndicator().observe(getViewLifecycleOwner(), bool -> {
            thingsToDoAfterJsonIsSet();
        });

        submitButton.setVisibility(View.VISIBLE);

        return root;
    }

    private void thingsToDoAfterJsonIsSet() {
        surveyViewModel.newQuestionText().observe(getViewLifecycleOwner(), questionTextView::setText);

        surveyViewModel.newQuestionType().observe(getViewLifecycleOwner(), questionType -> {
            switch (questionType) {
                case QuestionType.SMILEY_QUARTET:
                    navigate(new SmileyQuartetFragment());
                    break;
                case QuestionType.YES_NO:
                    navigate(new YesNoFragment());
                    break;
                case QuestionType.NPS:
                    navigate(new NpsFragment());
                    break;
                case QuestionType.MULTIPLE_CHOICE:
                    break;
                case QuestionType.SELECT_MANY:
                    break;
                case QuestionType.COMMENT:
                    navigate(new CommentFragment());
                    break;
                case QuestionType.SHORT_TEXT:
                    break;
                case QuestionType.TYPE_AHEAD:
                    break;
                case QuestionType.NUMBER:
                    break;
                case QuestionType.EMAIL:
                    break;
                case QuestionType.SMILEY_COMMENT:
                    break;
            }
        });

        surveyViewModel.surveyIsDone().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).
                            navigate(R.id.action_surveyFragment_to_doneWithSurveyFragment);
                }
            }
        });

        surveyViewModel.isLastQuestion().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {

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

    private void navigate(Fragment fragment) {
        requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.questionFragmentContainer, fragment).commit();
    }

}