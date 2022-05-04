package com.example.ttapp.survey.fragments;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import com.example.ttapp.ListAdapter;
import com.example.ttapp.R;

import java.util.ArrayList;
import java.util.List;

public class MultipleChoiceFragment extends QuestionFragment {

    private final ArrayList<Integer> response = new ArrayList<>();

    private LinearLayout linearLayout;
    private ListAdapter adapter;

    @Override
    protected void setView(LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(R.layout.fragment_multiple_choice, container, false);

        List<String> content = new ArrayList<>();
        content.add("option 1");
        content.add("option 2");

        adapter = new ListAdapter(requireActivity().getApplicationContext(), content);



    }

    @Override
    protected void initResponseOptions() {
        // TODO add logic when design is done. Will probably delete what´s below
//        linearLayout = view.findViewById(R.id.multipleChoiceLinearLayout);
//        for (int i = 0; i < 5; i++) {
//            CheckBox ch = new CheckBox(requireActivity());
//            ch.setText(i);
//            linearLayout.addView(ch);
//        }
    }

    @Override
    protected void initSaveResponseObserver() {
        surveyViewModel.getSaveResponse().observe(getViewLifecycleOwner(), bool -> {
            surveyViewModel.saveResponse(response);
        });
    }

}