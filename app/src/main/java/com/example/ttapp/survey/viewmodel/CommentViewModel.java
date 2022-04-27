package com.example.ttapp.survey.viewmodel;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * ViewModel for {@link com.example.ttapp.survey.fragments.CommentFragment}
 *
 * @author Emma Stålberg
 */
public class CommentViewModel extends ViewModel {

    public MutableLiveData<String> answer = new MutableLiveData<>();

    // TODO handle exception
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void saveAnswer(int answerOption, String questionID) throws JsonProcessingException {
        CommentAnswer answerObject = new CommentAnswer(answerOption, questionID);
        answer.setValue(answerObject.getAnswerJson());
    }

}