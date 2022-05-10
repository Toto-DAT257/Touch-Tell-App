package com.example.ttapp.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.ttapp.R;
import com.example.ttapp.database.MongoDB;
import com.example.ttapp.viewmodel.RegisterViewModel;

public class SplashFragment extends Fragment {

    private static final int SPLASH_TIME_OUT = 2000;

    private RegisterViewModel registerViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        registerViewModel = new ViewModelProvider(requireActivity()).get(RegisterViewModel.class);
        registerViewModel.setDatabase(MongoDB.getInstance());
        View view = inflater.inflate(R.layout.fragment_splash, container, false);
        observeIdentification(view);

        new Handler().postDelayed(() -> {
            getNextDestination(view);
        }, SPLASH_TIME_OUT);

        return view;
    }

    private void getNextDestination(View view) {
        SharedPreferences settings = requireActivity().getPreferences(Context.MODE_PRIVATE);
        if (settings.getBoolean("my_first_time", true)) {
            Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).navigate(R.id.action_splashFragment_to_introductionFragment);
        } else {
            checkIfIdentified(settings, view);
        }
    }

    private void checkIfIdentified(SharedPreferences settings, View view) {
        String identifier = settings.getString("identifier", "");
        if (identifier.isEmpty()) {
            Navigation.findNavController(view).navigate(R.id.action_splashFragment_to_registerFragment);
        } else {
            registerViewModel.identify(identifier);
        }
    }

    private void observeIdentification(View view) {
        registerViewModel.getIdentified().observe(getViewLifecycleOwner(), identification -> {
            if (identification) {
                Navigation.findNavController(view).navigate(R.id.action_splashFragment_to_homeFragment);
            } else {
                Navigation.findNavController(view).navigate(R.id.action_splashFragment_to_registerFragment);
            }
        });
    }

}