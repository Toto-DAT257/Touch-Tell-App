package com.example.ttapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public abstract class FirstQuestionFragment extends Fragment {

    View view;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        setView(inflater, container);

        return view;

    }

    protected abstract void setView(LayoutInflater inflater, ViewGroup container);

}
