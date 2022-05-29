package com.example.ttapp.survey.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.ttapp.survey.viewmodel.SurveyViewModel;

/**
 * An abstract class for the question fragments.
 */
public abstract class QuestionFragment extends Fragment {

    View view;
    SurveyViewModel surveyViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        setView(inflater, container);
        setSurveyViewModel();
        initResponseOptions();
        initSaveResponseObserver();
        initResponseObserver();
        repopulate();

        return view;
    }

    protected abstract void setView(LayoutInflater inflater, ViewGroup container);

    private void setSurveyViewModel() {
        surveyViewModel = new ViewModelProvider(requireActivity()).get(SurveyViewModel.class);
    }

    protected abstract void initResponseOptions();

    protected abstract void initSaveResponseObserver();

    protected abstract void initResponseObserver();

    protected void repopulate() {
        surveyViewModel.repopulate();
    }

    public void closeKeyboard() {
        View view = requireActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager manager = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

}
