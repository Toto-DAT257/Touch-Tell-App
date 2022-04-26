package com.example.ttapp.survey;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ttapp.R;

public class SmileyQuartetFragment extends Fragment {

    private SmileyQuartetViewModel mViewModel;

    public static SmileyQuartetFragment newInstance() {
        return new SmileyQuartetFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_smiley_quartet, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(SmileyQuartetViewModel.class);
        // TODO: Use the ViewModel
    }

}