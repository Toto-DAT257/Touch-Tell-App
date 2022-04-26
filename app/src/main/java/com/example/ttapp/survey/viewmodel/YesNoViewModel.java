package com.example.ttapp.survey.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.ttapp.survey.model.answers.IAnswer;
import com.example.ttapp.survey.model.answers.YesNoAnswer;

/**
 * ViewModel for {@link com.example.ttapp.survey.fragments.YesNoFragment}
 *
 * @author Emma Stålberg
 */
public class YesNoViewModel extends ViewModel {

    public MutableLiveData<IAnswer> answer = new MutableLiveData<>();

    public void SaveAnswer(int answerOption, String questionID) {
        YesNoAnswer answerObject = new YesNoAnswer(answerOption, questionID);
        answer.setValue(answerObject);
    }


}