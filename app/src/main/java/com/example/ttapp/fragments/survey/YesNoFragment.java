package com.example.ttapp.fragments.survey;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ttapp.R;
import com.example.ttapp.viewmodel.YesNoViewModel;

public class YesNoFragment extends Fragment {

    private YesNoViewModel mViewModel;

    public static YesNoFragment newInstance() {
        return new YesNoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_yes_no, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(YesNoViewModel.class);
        // TODO: Use the ViewModel
    }

}