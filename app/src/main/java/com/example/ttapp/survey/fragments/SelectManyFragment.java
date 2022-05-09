package com.example.ttapp.survey.fragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.constraintlayout.widget.ConstraintLayout;

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

    @Override
    protected void setView(LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(R.layout.fragment_select_many, container, false);
        listView = view.findViewById(R.id.listViewSelectMany);
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
            response.addAll(pressedOptions);
            surveyViewModel.saveResponse(response);
        });
    }

    private void initClickOnListItem() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                MultipleChoiceOption option = (MultipleChoiceOption) adapterView.getItemAtPosition(i);
                ConstraintLayout multiConstraintLayout = view.findViewById(R.id.multiConstraintLayout);
                ImageView multiCheck = view.findViewById(R.id.check_multibutton);

                if (multiCheck.getVisibility() == View.INVISIBLE) {
                    multiCheck.setVisibility(View.VISIBLE);
                    multiConstraintLayout.setBackgroundResource(R.drawable.background_multibutton_light);
                    pressedOptions.add(option.getValue());
                } else {
                    multiCheck.setVisibility(View.INVISIBLE);
                    multiConstraintLayout.setBackgroundResource(R.drawable.background_multibutton);
                    pressedOptions.remove(option.getValue());
                }
            }
        });
    }

}