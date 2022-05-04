package com.example.ttapp.survey.fragments;

import android.content.Context;
import android.content.SharedPreferences;
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
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.ttapp.R;
import com.example.ttapp.databinding.FragmentSurveyBinding;
import com.example.ttapp.survey.fragments.util.QuestionType;
import com.example.ttapp.survey.viewmodel.SurveyViewModel;

/**
 * Main fragment for the survey. Is responsible for hosting question fragments in the same package
 * such as:
 * <ul>
 *     <li>{@link CommentFragment}</li>
 *     <li>{@link YesNoFragment}</li>
 *     <li>{@link NpsFragment}</li>
 * </ul>
 *
 * @author Simon Holst, Amanda Cyrén, Emma Stålberg
 */
public class SurveyFragment extends Fragment {

    FragmentSurveyBinding binding;
    SurveyViewModel surveyViewModel;
    ImageButton backButton;
    ImageButton nextButton;
    Button homeButton;
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
        homeButton = binding.home;

        surveyViewModel = new ViewModelProvider(requireActivity()).get(SurveyViewModel.class);
        SharedPreferences sharedPref = requireActivity().getPreferences(Context.MODE_PRIVATE);
        String identifier = sharedPref.getString("identifier", "");

        surveyViewModel.loadQuestions(identifier);
        surveyViewModel.getJsonIsReceivedIndicator().observe(getViewLifecycleOwner(), bool -> thingsToDoAfterJsonIsSet());

        setHomeOnClickListener();
        return root;
    }

    private void setHomeOnClickListener(){
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).navigate(R.id.action_surveyFragment_to_homeFragment);
            }
        });
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

        surveyViewModel.surveyIsDone().observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean) {
                Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).navigate(R.id.action_surveyFragment_to_doneWithSurveyFragment);
                surveyViewModel.submitResponse();
            }
        });

        surveyViewModel.isLastQuestion().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                // to be done further on, submitbutton
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