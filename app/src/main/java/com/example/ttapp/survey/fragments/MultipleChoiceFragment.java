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

import java.util.ArrayList;
import java.util.List;

public class MultipleChoiceFragment extends QuestionFragment {

    private final ArrayList<Integer> response = new ArrayList<>();

    private LinearLayout linearLayout;
    private ListAdapter adapter;
    private ListView listView;

    @Override
    protected void setView(LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(R.layout.fragment_multiple_choice, container, false);

        List<String> content = new ArrayList<>();
        content.add("option 1");
        content.add("option 2");

        listView = view.findViewById(R.id.multiList);

        adapter = new ListAdapter(requireActivity().getApplicationContext(), content);
        listView.setAdapter(adapter);

        initClickOnListItem();

    }

    private void initClickOnListItem() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // adapterView.getItemAtPosition(i);
                ImageView multiCheck = view.findViewById(R.id.check_multibutton);
                ConstraintLayout multiConstraintLayout = view.findViewById(R.id.multiConstraintLayout);

                if (multiCheck.getVisibility() == View.INVISIBLE) {
                    multiCheck.setVisibility(View.VISIBLE);
                    multiConstraintLayout.setBackgroundResource(R.drawable.background_multibutton_light);
                } else {
                    multiCheck.setVisibility(View.INVISIBLE);
                    multiConstraintLayout.setBackgroundResource(R.drawable.background_multibutton);
                }
            }
        });
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