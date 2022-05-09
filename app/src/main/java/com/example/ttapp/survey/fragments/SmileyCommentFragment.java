package com.example.ttapp.survey.fragments;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.lifecycle.Observer;

import com.example.ttapp.R;

import java.util.ArrayList;

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

        // todo answeredoptions
    }

}