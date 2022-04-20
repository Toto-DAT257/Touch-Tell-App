package com.example.ttapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.ttapp.R;


public class IntroductionFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_introduction, container, false);

        TextView introductionHeader = view.findViewById(R.id.introductonHeader);
        TextView introductionText = view.findViewById(R.id.introductionText);
        Button introductionButton = view.findViewById(R.id.introductionButton);
        introductionHeader.setText("Welcome to the Touch&Tell App");
        introductionText.setText("Lorem Ipsum is simply dummy text of the " +
                "printing and typesetting industry. " +
                "Lorem Ipsum has been the industry's standard dummy text ever " +
                "since the 1500s, when an unknown printer took a galley of type " +
                "and scrambled it to make a type specimen book. " +
                "It has survived not only five centuries, but also" +
                " the leap into electronic typesetting, remaining essentially" +
                " unchanged. It was popularised in the 1960s with" +
                " the release of Letraset sheets containing Lorem Ipsum" +
                " passages, and more recently with desktop publishing software" +
                " like Aldus PageMaker including versions of Lorem Ipsum.");
        introductionButton.setOnClickListener(view1 -> Navigation.findNavController(view).navigate(R.id.action_introductionFragment_to_registerFragment2));
        return view;
    }
}