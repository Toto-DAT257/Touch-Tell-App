package com.example.ttapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class RegisterFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this emelie is boss
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        // Get views
        EditText codeEditText = view.findViewById(R.id.editTextIdCode);
        Button confirmButton = view.findViewById(R.id.buttonConfirmIdCode);
        TextView errorCodeIsEmpty = view.findViewById(R.id.error_code_is_empty);

        confirmButton.setOnClickListener(view1 -> {
            String code = codeEditText.getText().toString();

            if (code.isEmpty()) {
                // display error message
                errorCodeIsEmpty.setVisibility(View.VISIBLE);
            } else {
                // hide error message
                errorCodeIsEmpty.setVisibility(View.INVISIBLE);

                // Save code in activity preferences
                SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("code", code);
                editor.apply();

                // Go to survey fragment
                Navigation.findNavController(view).navigate(R.id.action_registerFragment_to_firstQuestionFragment);
            }


        });
        return view;
    }

}