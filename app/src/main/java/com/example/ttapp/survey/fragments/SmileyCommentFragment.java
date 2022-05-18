package com.example.ttapp.survey.fragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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

    private ImageButton smileyCResponseoption1;
    private ImageButton smileyCResponseoption2;
    private ImageButton smileyCResponseoption3;
    private ImageButton smileyCResponseoption4;

    private TableLayout tableSmileys;
    private EditText smileyComment;
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
        smileyComment = view.findViewById(R.id.smileyCComment);
        smileyComment.setVisibility(View.INVISIBLE);
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

        // To have multiline with an action as enter key
        smileyComment.setHorizontallyScrolling(false);
        smileyComment.setMaxLines(Integer.MAX_VALUE);
        smileyComment.setOnEditorActionListener((textView, actionId, keyEvent) -> {
            boolean handled = false;
            if (actionId == EditorInfo.IME_ACTION_GO) {
                save();
                surveyViewModel.nextQuestion();
                handled = true;
            }
            return handled;
        });
    }

    @Override
    protected void initSaveResponseObserver() {
        surveyViewModel.getSaveResponse().observe(getViewLifecycleOwner(), bool -> save());
    }

    private void save() {
        comment = smileyComment.getText().toString();
        surveyViewModel.saveResponse(responseOption, comment);
    }

    @Override
    protected void initResponseObserver() {
        surveyViewModel.containsCommentresponse().observe(getViewLifecycleOwner(), s -> smileyComment.setText(s));

        surveyViewModel.containsAnsweredOptionsResponse().observe(getViewLifecycleOwner(), integers -> {
            switch (integers.get(0)) {
                case 1:
                    changeSmiley(1, smileyCResponseoption1);
                    break;
                case 2:
                    changeSmiley(2, smileyCResponseoption2);
                    break;
                case 3:
                    changeSmiley(3, smileyCResponseoption3);
                    break;
                case 4:
                    changeSmiley(4, smileyCResponseoption4);
                    break;
            }
        });
    }

    private void clearSmileys() {
        smileyCResponseoption1.setBackgroundResource(0);
        smileyCResponseoption2.setBackgroundResource(0);
        smileyCResponseoption3.setBackgroundResource(0);
        smileyCResponseoption4.setBackgroundResource(0);
    }

    private void changeSmiley(int i, ImageView chosenSmiley) {
        clearSmileys();
        chosenSmiley.setBackgroundResource(R.drawable.ic_smiley_chosen);
        responseOption.clear();
        responseOption.add(i);
        buttonCommentSmiley.setVisibility(View.VISIBLE);
    }

    private void initOnClickListeners() {

        smileyCResponseoption1.setOnClickListener(view -> changeSmiley(1, smileyCResponseoption1));

        smileyCResponseoption2.setOnClickListener(view -> changeSmiley(2, smileyCResponseoption2));

        smileyCResponseoption3.setOnClickListener(view -> changeSmiley(3, smileyCResponseoption3));

        smileyCResponseoption4.setOnClickListener(view -> changeSmiley(4, smileyCResponseoption4));

        buttonCommentSmiley.setOnClickListener(view -> {
            buttonCommentSmiley.setVisibility(View.INVISIBLE);
            buttonBackSmiley.setVisibility(View.VISIBLE);
            tableSmileys.setVisibility(View.INVISIBLE);
            smileyComment.setVisibility(View.VISIBLE);
        });

        buttonBackSmiley.setOnClickListener(view -> {
            buttonCommentSmiley.setVisibility(View.VISIBLE);
            buttonBackSmiley.setVisibility(View.INVISIBLE);
            tableSmileys.setVisibility(View.VISIBLE);
            smileyComment.setVisibility(View.INVISIBLE);
        });

    }

}