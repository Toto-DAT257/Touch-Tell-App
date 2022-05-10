package com.example.ttapp.fragments;

import static android.view.View.GONE;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

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

    private SharedPreferences sharedPref;

    private Button buttonStartSurvey, buttonSignOut;
    private ImageButton buttonUser, buttonCloseUserSection;
    private TextView textViewIdentifier, textViewHomeHeader;
    ConstraintLayout userContainer;

    private boolean userSectionIsOpen;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentHomeBinding binding = FragmentHomeBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();

        buttonStartSurvey = binding.buttonStartSurvey;
        buttonSignOut = binding.buttonSignOut;
        buttonUser = binding.buttonUser;
        buttonCloseUserSection = binding.buttonCloseUserSection;
        userContainer = binding.userContainer;
        textViewHomeHeader = binding.textViewHomeHeader;

        buttonStartSurvey.setOnClickListener(view1 -> Navigation.findNavController(root).navigate(R.id.action_homeFragment_to_registerFragment));

        buttonSignOut.setOnClickListener(view -> signOut(root));

        buttonCloseUserSection.setOnClickListener(view -> onUserSectionButtonClick());

        buttonUser.setOnClickListener(view -> onUserSectionButtonClick());
            //FragmentTransaction ft = getChildFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left).hide().commit();

        return root;
    }

    private void signOut(View view) {
        //forgetIdentifier();
        Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_splashFragment);
    }

    public void closeUserSection(View view) {
        buttonUser.setEnabled(true);
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                0,  // fromYDelta
                -view.getHeight());                // toYDelta
        animate.setDuration(500);
        animate.setFillAfter(true);
        view.startAnimation(animate);
        view.setVisibility(GONE);
    }

    public void openUserSection(View view) {
        view.setVisibility(View.VISIBLE);
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                -view.getHeight(),                 // fromYDelta
                0); // toYDelta
        animate.setDuration(500);
        animate.setFillAfter(true);
        view.startAnimation(animate);
        buttonUser.setEnabled(false);
    }

    public void onUserSectionButtonClick() {
        if (userSectionIsOpen) {
            closeUserSection(userContainer);
        } else {
            openUserSection(userContainer);
        }
        userSectionIsOpen = !userSectionIsOpen;
    }



    private void forgetIdentifier() {
        sharedPref = requireActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("identifier", "");
        editor.apply();
    }

}