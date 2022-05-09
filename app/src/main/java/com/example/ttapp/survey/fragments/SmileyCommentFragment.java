package com.example.ttapp.survey.fragments;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.lifecycle.Observer;

import com.example.ttapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for a fragment that presents a smileycomment-question
 *
 * Used by: -
 * Uses: -
 *
 * Created by
 * @author Emma Stålberg
 */
public class SmileyCommentFragment extends QuestionFragment {

    private ImageView smileyCResponseoption1;
    private ImageView smileyCResponseoption2;
    private ImageView smileyCResponseoption3;
    private ImageView smileyCResponseoption4;

    private EditText smileyCComment;

    ArrayList<Integer> responseOption = new ArrayList<>();
    String comment;

    @Override
    protected void setView(LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(R.layout.fragment_smiley_comment, container, false);
    }

    @Override
    protected void initResponseOptions() {
        smileyCComment = view.findViewById(R.id.smileyCComment);

        smileyCResponseoption1 = view.findViewById(R.id.smileyCResponseoption1);
        smileyCResponseoption2 = view.findViewById(R.id.smileyCResponseoption2);
        smileyCResponseoption3 = view.findViewById(R.id.smileyCResponseoption3);
        smileyCResponseoption4 = view.findViewById(R.id.smileyCResponseoption4);

        initOnClickListeners();
        // TODO implement logic when design is done
    }

    @Override
    protected void initSaveResponseObserver() {
        surveyViewModel.getSaveResponse().observe(getViewLifecycleOwner(), bool -> {
            //comment = smileyCommentResponse.getText().toString(); // TODO change to actual ID when design is done
            // TODO set responseOption when design is done
            surveyViewModel.saveResponse(responseOption, comment);
        });
    }

    private void initOnClickListeners() {

    }

    @Override
    protected void initResponseObserver() {
        surveyViewModel.containsCommentresponse().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                smileyCComment.setText(s);
            }
        });

        surveyViewModel.containsAnsweredOptionsResponse().observe(getViewLifecycleOwner(), new Observer<List<Integer>>() {
            @Override
            public void onChanged(List<Integer> integers) {
                switch (integers.get(0)) {
                    case 1:
                        setSmiley1Chosen();
                        break;
                    case 2:
                        setSmiley2Chosen();
                        break;
                    case 3:
                        setSmiley3Chosen();
                        break;
                    case 4:
                        setSmiley4Chosen();
                        break;
                }
            }
        });
    }

    private void setSmiley1Chosen() {
        smileyCResponseoption2.setBackgroundResource(R.drawable.ic_angry_not_chosen);
        smileyCResponseoption3.setBackgroundResource(R.drawable.ic_happy_not_chosen);
        smileyCResponseoption4.setBackgroundResource(R.drawable.ic_most_happy_not_chosen);
    }

    private void setSmiley2Chosen() {
        smileyCResponseoption1.setBackgroundResource(R.drawable.ic_most_angry_not_chosen);
        smileyCResponseoption3.setBackgroundResource(R.drawable.ic_happy_not_chosen);
        smileyCResponseoption4.setBackgroundResource(R.drawable.ic_most_happy_not_chosen);
    }

    private void setSmiley3Chosen() {
        smileyCResponseoption1.setBackgroundResource(R.drawable.ic_most_angry_not_chosen);
        smileyCResponseoption2.setBackgroundResource(R.drawable.ic_angry_not_chosen);
        smileyCResponseoption4.setBackgroundResource(R.drawable.ic_most_happy_not_chosen);
    }

    private void setSmiley4Chosen() {
        smileyCResponseoption1.setBackgroundResource(R.drawable.ic_most_angry_not_chosen);
        smileyCResponseoption2.setBackgroundResource(R.drawable.ic_angry_not_chosen);
        smileyCResponseoption3.setBackgroundResource(R.drawable.ic_happy_not_chosen);
    }

}