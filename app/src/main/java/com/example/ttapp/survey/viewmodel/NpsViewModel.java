package com.example.ttapp.survey.viewmodel;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.ttapp.survey.model.answers.NPSAnswer;
import com.example.ttapp.survey.model.answers.YesNoAnswer;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * ViewModel for {@link com.example.ttapp.survey.fragments.NpsFragment}
 *
 * @author Emma Stålberg
 */
public class NpsViewModel extends ViewModel {

    public MutableLiveData<String> answer = new MutableLiveData<>();

    // TODO handle exception
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void SaveAnswer(int answerOption, String questionID) throws JsonProcessingException {
        NPSAnswer answerObject = new NPSAnswer(answerOption, questionID);
        answer.setValue(answerObject.getAnswerJson());
    }

}