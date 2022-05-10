package com.example.ttapp.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.ttapp.R;

/**
 * Class for a fragment that presents the introduction page to the application
 *
 * Used by: -
 * Uses: -
 *
 * Created by
 * @author Alva Jansson, Fanny Söderling & Simon Holst
 */
public class IntroductionFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_introduction, container, false);

        // Creating shared preferences in order to keep track if the application has been run before
        SharedPreferences settings = getActivity().getPreferences(Context.MODE_PRIVATE);

        TextView introductionHeader = view.findViewById(R.id.introductionHeader);
        TextView introductionText = view.findViewById(R.id.introductionText);
        Button introductionButton = view.findViewById(R.id.introductionButton);
        introductionHeader.setText(getString(R.string.introduction_header));
        introductionText.setText(getString(R.string.introduction_text));
        introductionButton.setText(R.string.introduction_button);

        introductionButton.setOnClickListener(view1 -> {

            settings.edit().putBoolean("my_first_time", false).apply();
            Navigation.findNavController(view).navigate(R.id.action_introductionFragment_to_registerFragment2);
        });

        return view;
    }
}