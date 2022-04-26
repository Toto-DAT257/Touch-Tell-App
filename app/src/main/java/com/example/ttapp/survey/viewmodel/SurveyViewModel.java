package com.example.ttapp.survey.viewmodel;

import android.content.Context;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModel;

import com.example.ttapp.survey.model.Survey;

/**
 * ViewModel for {@link com.example.ttapp.fragments.SurveyFragment}
 *
 * @author Simon Holst, Amanda Cyrén, Emma Stålberg
 */
public class SurveyViewModel extends ViewModel {

    private Survey survey = new Survey();

    /**
     * Loads the questions from the Touch&Tell API for the logged in user.
     * @param context this fragment's context.
     * @param activity this fragment's activity.
     */
    public void loadQuestions(Context context, FragmentActivity activity) {
        survey.loadQuestions(context, activity);
    }

    public String getQuestions() {
        return survey.getQuestions();
    }
}
