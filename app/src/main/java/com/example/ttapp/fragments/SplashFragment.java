package com.example.ttapp.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.ttapp.R;

public class SplashFragment extends Fragment {

    private static final int SPLASH_TIME_OUT = 2000;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_splash, container, false);

        new Handler().postDelayed(this::getNextDestination, SPLASH_TIME_OUT);

        return view;
    }

    private void getNextDestination() {
        SharedPreferences settings = requireActivity().getPreferences(Context.MODE_PRIVATE);
        if (settings.getBoolean("my_first_time", true)) {
            Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).navigate(R.id.action_splashFragment_to_introductionFragment);
        } else if (!settings.getString("identifier", "").isEmpty()) {
            Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).navigate(R.id.action_splashFragment_to_homeFragment);
        } else {
            Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).navigate(R.id.action_splashFragment_to_registerFragment);
        }
    }

}