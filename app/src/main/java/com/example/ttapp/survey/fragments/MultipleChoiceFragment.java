package com.example.ttapp.survey.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.example.ttapp.ListAdapter;
import com.example.ttapp.R;
import com.example.ttapp.survey.model.MultipleChoiceOption;
import com.example.ttapp.survey.model.jsonparsing.ResponseValues;

import java.util.ArrayList;
import java.util.List;

public class MultipleChoiceFragment extends QuestionFragment {

    private final ArrayList<Integer> response = new ArrayList<>();

    private ListAdapter adapter;
    private ListView listView;

    @Override
    protected void setView(LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(R.layout.fragment_multiple_choice, container, false);


        listView = view.findViewById(R.id.multiList);


    }

    private void initClickOnListItem() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ConstraintLayout multiConstraintLayout = view.findViewById(R.id.multiConstraintLayout);
                multiConstraintLayout.setBackgroundResource(R.drawable.background_multibutton_light);

                MultipleChoiceOption option = (MultipleChoiceOption) adapterView.getItemAtPosition(i);
                response.add(option.getValue());
                surveyViewModel.saveResponse(response);
                surveyViewModel.nextQuestion();

                /*
                ImageView multiCheck = view.findViewById(R.id.check_multibutton);


                if (multiCheck.getVisibility() == View.INVISIBLE) {
                    multiCheck.setVisibility(View.VISIBLE);
                    multiConstraintLayout.setBackgroundResource(R.drawable.background_multibutton_light);
                } else {
                    multiCheck.setVisibility(View.INVISIBLE);
                    multiConstraintLayout.setBackgroundResource(R.drawable.background_multibutton);
                }

                 */
            }
        });
    }

    @Override
    protected void initResponseOptions() {
        List<MultipleChoiceOption> options = surveyViewModel.getResponseOptions();

        adapter = new ListAdapter(requireActivity().getApplicationContext(), options);
        listView.setAdapter(adapter);

        initClickOnListItem();

    }

    @Override
    protected void initSaveResponseObserver() {
        surveyViewModel.getSaveResponse().observe(getViewLifecycleOwner(), bool -> {
            surveyViewModel.saveResponse(response);
        });
    }

}