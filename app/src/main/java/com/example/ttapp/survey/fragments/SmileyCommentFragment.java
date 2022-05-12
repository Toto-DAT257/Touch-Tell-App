package com.example.ttapp.survey.fragments;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableLayout;

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
    private TableLayout tableSmileys;

    private EditText smileyCComment;
    private Button buttonCommentSmiley;
    private Button buttonBackSmiley;

    ArrayList<Integer> responseOption = new ArrayList<>();
    String comment;

    @Override
    protected void setView(LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(R.layout.fragment_smiley_comment, container, false);
    }

    @Override
    protected void initResponseOptions() {
        smileyCComment = view.findViewById(R.id.smileyCComment);
        smileyCComment.setVisibility(View.INVISIBLE);

        tableSmileys = view.findViewById(R.id.tableSmileys);
        smileyCResponseoption1 = view.findViewById(R.id.smileyCResponseoption1);
        smileyCResponseoption2 = view.findViewById(R.id.smileyCResponseoption2);
        smileyCResponseoption3 = view.findViewById(R.id.smileyCResponseoption3);
        smileyCResponseoption4 = view.findViewById(R.id.smileyCResponseoption4);

        buttonCommentSmiley = view.findViewById(R.id.buttonCommentSmiley);
        buttonCommentSmiley.setVisibility(View.INVISIBLE);
        buttonBackSmiley = view.findViewById(R.id.buttonBackSmiley);
        buttonBackSmiley.setVisibility(View.INVISIBLE);
        initOnClickListeners();
    }

    @Override
    protected void initSaveResponseObserver() {
        surveyViewModel.getSaveResponse().observe(getViewLifecycleOwner(), bool -> {
            comment = smileyCComment.getText().toString();
            // TODO set responseOption when design is done

            surveyViewModel.saveResponse(responseOption, comment); //responseOption.get(responseOption.size()-1)
        });
    }

    private void responseClearer (ArrayList<Integer> responseOption ) {
        responseOption.clear();
    }

    private void initOnClickListeners() {
        smileyCResponseoption1.setOnClickListener(view -> {
            responseClearer(responseOption);
            responseOption.add(1);
            buttonCommentSmiley.setVisibility(View.VISIBLE);
        });

        smileyCResponseoption2.setOnClickListener(view -> {
            responseClearer(responseOption);
            responseOption.add(2);
            buttonCommentSmiley.setVisibility(View.VISIBLE);
        });

        smileyCResponseoption3.setOnClickListener(view -> {
            responseClearer(responseOption);
            responseOption.add(3);
            buttonCommentSmiley.setVisibility(View.VISIBLE);
        });

        smileyCResponseoption4.setOnClickListener(view -> {
            responseClearer(responseOption);  
            responseOption.add(4);
            buttonCommentSmiley.setVisibility(View.VISIBLE);
        });

        buttonCommentSmiley.setOnClickListener(view ->{
            buttonCommentSmiley.setVisibility(View.INVISIBLE);
            buttonBackSmiley.setVisibility(View.VISIBLE);
            tableSmileys.setVisibility(View.INVISIBLE);
            smileyCComment.setVisibility(View.VISIBLE);
        });

        buttonBackSmiley.setOnClickListener(view -> {
            buttonCommentSmiley.setVisibility(View.VISIBLE);
            buttonBackSmiley.setVisibility(View.INVISIBLE);
            tableSmileys.setVisibility(View.VISIBLE);
            smileyCComment.setVisibility(View.INVISIBLE);
        });

    }

}