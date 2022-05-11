package com.example.ttapp.survey.fragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;

import com.example.ttapp.ListAdapter;
import com.example.ttapp.R;
import com.example.ttapp.survey.model.MultipleChoiceOption;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Class for a fragment that presents a selectmany-question
 *
 * Used by: -
 * Uses: -
 *
 * Created by
 * @author Emma Stålberg
 */
public class SelectManyFragment extends QuestionFragment {

    private final ArrayList<Integer> response = new ArrayList<>();

    private ListView listView;
    private final Set<Integer> pressedOptions = new HashSet<>();

    private List<MultipleChoiceOption> options;

    @Override
    protected void setView(LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(R.layout.fragment_select_many, container, false);
        listView = view.findViewById(R.id.listViewSelectMany);
    }

    @Override
    protected void initResponseOptions() {
        options = surveyViewModel.getResponseOptions();
        ListAdapter adapter = new ListAdapter(requireActivity().getApplicationContext(), options);
        listView.setAdapter(adapter);
        initClickOnListItem();
    }

    @Override
    protected void initSaveResponseObserver() {
        surveyViewModel.getSaveResponse().observe(getViewLifecycleOwner(), bool -> {
            response.addAll(pressedOptions);
            surveyViewModel.saveResponse(response);
        });
    }

    @Override
    protected void initResponseObserver() {
        // todo
        surveyViewModel.containsAnsweredOptionsResponse().observe(getViewLifecycleOwner(), new Observer<List<Integer>>() {
            @Override
            public void onChanged(List<Integer> integers) {
                for (int i = 0; i < integers.size(); i++) {
                    System.out.println(integers.get(i));
                    options.get(integers.get(i)-1).setSelected(true);
                    pressedOptions.add(integers.get(i));
                }
            }
        });
    }

    private void initClickOnListItem() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                MultipleChoiceOption option = (MultipleChoiceOption) adapterView.getItemAtPosition(i);
                ImageView multiCheck = view.findViewById(R.id.check_multibutton);

                if (multiCheck.getVisibility() == View.INVISIBLE) {
                    pressedOptions.add(option.getValue());
                    option.setSelected(true);
                } else {
                    pressedOptions.remove(option.getValue());
                    option.setSelected(false);
                }

                redrawList();
            }
        });
    }

    private void redrawList() {
        ListAdapter adapter = new ListAdapter(requireActivity().getApplicationContext(), options);
        listView.setAdapter(adapter);
    }

}