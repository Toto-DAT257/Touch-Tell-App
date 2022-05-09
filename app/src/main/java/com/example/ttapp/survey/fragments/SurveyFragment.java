package com.example.ttapp.survey.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.lifecycle.Observer;
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
    ImageButton backButton, nextButton, expandCollapseButton;
    Button homeButton;
    FragmentContainerView questionFragmentContainer;
    TextView questionTextView;
    ProgressBar progressBar;
    boolean isExpanded = false;


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
        hideQuestion();
        expandCollapseButton = binding.expandCollapseButton;


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
        setExpandCollapseOnClickListener();


        return root;
    }

    private void hideQuestion() {
        questionFragmentContainer.setVisibility(View.INVISIBLE);
        questionTextView.setVisibility(View.INVISIBLE);
    }

    private void showQuestion() {
        questionFragmentContainer.setVisibility(View.VISIBLE);
        questionTextView.setVisibility(View.VISIBLE);
    }

    private void setHomeOnClickListener() {
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).navigate(R.id.action_surveyFragment_to_homeFragment);
            }
        });
    }

    private void setExpandCollapseOnClickListener() {
        expandCollapseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isExpanded) {
                    collapseQuestionText();
                } else {
                    expandQuestionText();
                }

            }
        });
    }

    private static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    private void collapseQuestionText() {
        questionTextView.setMovementMethod(null);
        questionTextView.setMaxLines(5);
        isExpanded = false;
        expandCollapseButton.setImageResource(R.drawable.ic_round_expand_more_24);
        questionFragmentContainer.setVisibility(View.VISIBLE);
    }

    private void expandQuestionText() {
        ConstraintLayout card = binding.card;
        int h = card.getHeight();
        questionTextView.setMaxHeight(h - dpToPx(84));
        isExpanded = true;
        expandCollapseButton.setImageResource(R.drawable.ic_round_expand_less_24);
        questionFragmentContainer.setVisibility(View.INVISIBLE);
        questionTextView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int lh = questionTextView.getLineHeight();
                if (questionTextView.getLineCount() > h/lh) {
                    questionTextView.setMovementMethod(new ScrollingMovementMethod());
                }
                questionTextView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }

    private void configureQuestionText(String text) {
        resetQuestionTextFormat();
        questionTextView.setText(text);

        questionTextView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (questionTextView.getLineCount() > 4) {
                    questionTextView.setMaxLines(5);
                    questionTextView.setTextSize(20);
                    questionTextView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                        @Override
                        public void onGlobalLayout() {
                            if (questionTextView.getLineCount() > 5) {
                                collapseQuestionText();
                                expandCollapseButton.setVisibility(View.VISIBLE);
                                questionTextView.setEllipsize(TextUtils.TruncateAt.END);
                            }
                            questionTextView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        }
                    });
                }
                questionTextView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }

    private void resetQuestionTextFormat() {
        questionTextView.setEllipsize(null);
        questionTextView.setTextSize(24);
        questionTextView.setMovementMethod(null);
        questionTextView.setMaxLines(4);
        expandCollapseButton.setVisibility(View.INVISIBLE);
    }

    private void thingsToDoAfterJsonIsSet() {
        surveyViewModel.newQuestionText().observe(getViewLifecycleOwner(), text -> {
            configureQuestionText(text);
        });

        surveyViewModel.newQuestionType().observe(getViewLifecycleOwner(), questionType -> {
            progressBar.setProgress(surveyViewModel.getProgressPercentage());
            showQuestion();

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
        getChildFragmentManager().beginTransaction().replace(R.id.questionFragmentContainer, fragment).commit();
    }

}