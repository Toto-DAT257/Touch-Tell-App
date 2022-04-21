package com.example.ttapp.viewmodel;

import android.content.Context;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModel;

import com.example.ttapp.model.Survey;

public class SurveyViewModel extends ViewModel {

    Survey survey = new Survey();

    public void loadQuestions(Context context, FragmentActivity activity) {
        survey.loadQuestions(context, activity);
    }

    public String getQuestions() {
        return survey.getQuestions();
    }

}
