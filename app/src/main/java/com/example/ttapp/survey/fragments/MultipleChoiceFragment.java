package com.example.ttapp.survey.fragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.example.ttapp.ListAdapter;
import com.example.ttapp.R;
import com.example.ttapp.survey.model.MultipleChoiceOption;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for a fragment that presents a multiplechoice-question
 *
 * Used by: -
 * Uses: -
 *
 * Created by
 * @author Emma Stålberg
 */
public class MultipleChoiceFragment extends QuestionFragment {

    private final ArrayList<Integer> response = new ArrayList<>();

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
            }
        });
    }

    @Override
    protected void initResponseOptions() {
        List<MultipleChoiceOption> options = surveyViewModel.getResponseOptions();
        ListAdapter adapter = new ListAdapter(requireActivity().getApplicationContext(), options);
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