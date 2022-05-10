package com.example.ttapp.survey.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

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
    ProgressBar progressBar, loading;
    ConstraintLayout separator;


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
        progressBar = binding.progressBar;
        separator = binding.separator;
        loading = binding.loadingProgressBar;

        hideQuestion();

        surveyViewModel = new ViewModelProvider(requireActivity()).get(SurveyViewModel.class);
        SharedPreferences sharedPref = requireActivity().getPreferences(Context.MODE_PRIVATE);
        String identifier = sharedPref.getString("identifier", "");

        surveyViewModel.loadQuestions(identifier);
        surveyViewModel.getJsonIsReceivedIndicator().observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean) {
                thingsToDoAfterJsonIsSet();
            }
        });

        setHomeOnClickListener();
        return root;
    }

    private void hideQuestion() {
        questionFragmentContainer.setVisibility(View.INVISIBLE);
        questionTextView.setVisibility(View.INVISIBLE);
        separator.setVisibility(View.INVISIBLE);
        loading.setVisibility(View.VISIBLE);
    }

    private void showQuestion() {
        questionFragmentContainer.setVisibility(View.VISIBLE);
        questionTextView.setVisibility(View.VISIBLE);
        separator.setVisibility(View.VISIBLE);
        loading.setVisibility(View.INVISIBLE);
    }

    private void setHomeOnClickListener() {
        homeButton.setOnClickListener(view -> {
            surveyViewModel.resetSurvey();
            Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).navigate(R.id.action_surveyFragment_to_homeFragment);
        });
    }

    private void thingsToDoAfterJsonIsSet() {
        surveyViewModel.newQuestionText().observe(getViewLifecycleOwner(), questionTextView::setText);

        surveyViewModel.newQuestionType().observe(getViewLifecycleOwner(), questionType -> {
            progressBar.setProgress(surveyViewModel.getProgressPercentage());
            showQuestion();

            switch (questionType) {
                case QuestionType.SMILEY_QUARTET:
                    // start animation
                    navigate(new SmileyQuartetFragment()); //
                    break;
                case QuestionType.YES_NO:
                    navigate(new YesNoFragment());
                    break;
                case QuestionType.NPS:
                    navigate(new NpsFragment());
                    break;
                case QuestionType.MULTIPLE_CHOICE:
                    navigate(new MultipleChoiceFragment());
                    break;
                case QuestionType.SELECT_MANY:
                    navigate(new SelectManyFragment());
                    break;
                case QuestionType.COMMENT:
                    navigate(new CommentFragment());
                    break;
                case QuestionType.SHORT_TEXT:
                    navigate(new ShortTextFragment());
                    break;
                case QuestionType.NUMBER:
                    navigate(new NumberFragment());
                    break;
                case QuestionType.EMAIL:
                    navigate(new EmailFragment());
                    break;
                case QuestionType.SMILEY_COMMENT:
                    navigate(new SmileyCommentFragment());
                    break;
            }
        });

        surveyViewModel.surveyIsDone().observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean) {
                surveyViewModel.resetSurvey();
                hideQuestion();
                Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).navigate(R.id.action_surveyFragment_to_doneWithSurveyFragment);
                surveyViewModel.submitResponse();
            }
        });

        surveyViewModel.isLastQuestion().observe(getViewLifecycleOwner(), aBoolean -> {
            // to be done further on, submitButton
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

        if (questionFragmentContainer.getFragment() != null) {
            FragmentTransaction ft = getChildFragmentManager().beginTransaction();
            if (surveyViewModel.isTransitionForward()) {
                ft.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
            } else {
                ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
            }
            ft.replace(R.id.questionFragmentContainer, fragment);
            ft.commit();
        } else {
            getChildFragmentManager().beginTransaction().replace(R.id.questionFragmentContainer, fragment).commit();
        }

    }

}