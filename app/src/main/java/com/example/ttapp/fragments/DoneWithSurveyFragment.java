package com.example.ttapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.ttapp.R;
import com.example.ttapp.survey.viewmodel.SurveyViewModel;

/**
 * Class for a fragment that is shown when a survey is done
 */
public class DoneWithSurveyFragment extends Fragment {

    private View view;
    private SurveyViewModel surveyViewModel;
    Button buttonSurveyDone;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_done_with_survey, container, false);

        surveyViewModel = new ViewModelProvider(requireActivity()).get(SurveyViewModel.class);

        buttonSurveyDone = view.findViewById(R.id.buttonSurveyDone);

        buttonSurveyDone.setOnClickListener(view1 -> {
            Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(R.id.action_doneWithSurveyFragment_to_homeFragment);
        });

        return view;
    }

}
