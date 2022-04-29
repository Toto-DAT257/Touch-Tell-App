package com.example.ttapp.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.ttapp.R;
import com.example.ttapp.databinding.FragmentHomeBinding;

/**
 * Class for a fragment that presents the home page to the application
 *
 * Used by: -
 * Uses: -
 *
 * Created by
 * @author Amanda Cyrén & Philip Winsnes
 */
public class HomeFragment extends Fragment {

    private Button buttonStartSurvey;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentHomeBinding binding = FragmentHomeBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();

        buttonStartSurvey = binding.buttonStartSurvey;

        buttonStartSurvey.setOnClickListener(view1 -> Navigation.findNavController(view1).navigate(R.id.action_homeFragment_to_surveyFragment));

        return root;
    }

}