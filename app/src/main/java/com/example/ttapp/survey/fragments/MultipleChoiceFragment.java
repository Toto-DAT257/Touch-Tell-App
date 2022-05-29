package com.example.ttapp.survey.fragments;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.ttapp.ListAdapter;
import com.example.ttapp.R;
import com.example.ttapp.survey.model.MultipleChoiceOption;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for a fragment that presents a multiplechoice-question
 */
public class MultipleChoiceFragment extends QuestionFragment {

    private final ArrayList<Integer> response = new ArrayList<>();

    private ListView listView;
    private List<MultipleChoiceOption> options;

    @Override
    protected void setView(LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(R.layout.fragment_multiple_choice, container, false);
        listView = view.findViewById(R.id.multiList);
    }

    private void initClickOnListItem() {
        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            MultipleChoiceOption option = (MultipleChoiceOption) adapterView.getItemAtPosition(i);
            response.add(option.getValue());
            surveyViewModel.saveResponse(response);
            clearSelected();
            option.setSelected(true);
            redrawList();
            surveyViewModel.nextQuestion();
        });
    }

    private void clearSelected() {
        for (MultipleChoiceOption o : options) {
            o.setSelected(false);
        }
    }

    private void redrawList() {
        ListAdapter adapter = new ListAdapter(requireActivity().getApplicationContext(), options);
        listView.setAdapter(adapter);
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
        surveyViewModel.getSaveResponse().observe(getViewLifecycleOwner(), bool -> surveyViewModel.saveResponse(response));
    }

    @Override
    protected void initResponseObserver() {
        surveyViewModel.containsAnsweredOptionsResponse().observe(getViewLifecycleOwner(), integers -> options.get(integers.get(0)-1).setSelected(true));
    }

}