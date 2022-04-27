package com.example.ttapp.survey.viewmodel;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.ttapp.survey.model.answers.SmileyQuartet;
import com.example.ttapp.survey.model.answers.YesNoAnswer;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * ViewModel for {@link com.example.ttapp.survey.fragments.SmileyQuartetFragment}
 *
 * @author Emma Stålberg
 */
public class SmileyQuartetViewModel extends ViewModel {


    public MutableLiveData<String> answer = new MutableLiveData<>();

    //TODO handle exception
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void saveAnswer(int answerOption, String questionID) throws JsonProcessingException {
        //SmileyQuartet answerObject = new SmileyQuartet(answerOption, questionID);
        //answer.setValue(answerObject.getAnswerJson());
    }

}