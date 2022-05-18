package com.example.ttapp.fragments;

import static android.view.View.GONE;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.NetworkRequest;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
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
 * <p>
 * Used by: -
 * Uses: -
 * <p>
 * Created by
 *
 * @author Amanda Cyrén & Philip Winsnes
 */
public class HomeFragment extends Fragment {

    private SharedPreferences sharedPref;

    private ImageButton buttonUser, buttonCloseUserSection;
    private Button buttonStartSurvey;
    ConstraintLayout userContainer;

    private boolean userSectionIsOpen;

    private final int DELAY_ANIMATION = 500;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentHomeBinding binding = FragmentHomeBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();
        sharedPref = requireActivity().getPreferences(Context.MODE_PRIVATE);

        buttonStartSurvey = binding.buttonStartSurvey;
        Button buttonSignOut = binding.buttonSignOut;
        buttonUser = binding.buttonUser;
        buttonCloseUserSection = binding.buttonCloseUserSection;
        userContainer = binding.userContainer;
        TextView textViewIdentifier = binding.textViewIdentifierPreview;
        textViewIdentifier.setText(sharedPref.getString("identifier", "Error previewing the identifier"));

        buttonStartSurvey.setOnClickListener(view1 -> startSurvey(root));

        buttonSignOut.setOnClickListener(view -> signOut(root));

        buttonCloseUserSection.setOnClickListener(view -> onUserSectionButtonClick());

        buttonUser.setOnClickListener(view -> onUserSectionButtonClick());


        if (isNetworkAvailable()) {
            Log.e("internet", "Yey");
        } else {
            Log.e("internet", "Ney");
        }

        ConnectivityManager.NetworkCallback networkCallback = new ConnectivityManager.NetworkCallback() {
            @Override
            public void onAvailable(Network network) {
                // network available
                requireActivity().runOnUiThread(() -> {

                    // Stuff that updates the UI
                    Log.e("internet2", "Yey");
                    buttonStartSurvey.setEnabled(true);
                    buttonStartSurvey.setTextColor(Color.parseColor("#FFFFFF"));
                });
            }

            @Override
            public void onLost(Network network) {
                // network unavailable
                requireActivity().runOnUiThread(() -> {

                    // Stuff that updates the UI
                    Log.e("internet2", "Ney");
                    buttonStartSurvey.setEnabled(false);
                    buttonStartSurvey.setTextColor(Color.parseColor("#66FFFFFF"));
                });

            }
        };

        ConnectivityManager connectivityManager =
                (ConnectivityManager) requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            connectivityManager.registerDefaultNetworkCallback(networkCallback);
        } else {
            NetworkRequest request = new NetworkRequest.Builder()
                    .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET).build();
            connectivityManager.registerNetworkCallback(request, networkCallback);
        }

        return root;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void startSurvey(View view) {
        changeStatusBarColor(R.color.toto_background_gradient_blue);
        Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_surveyFragment);
    }

    private void signOut(View view) {
        forgetIdentifier();
        Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_registerFragment);
    }

    public void closeUserSection() {
        buttonUser.setEnabled(false);
        buttonCloseUserSection.setEnabled(false);
        TranslateAnimation animate = new TranslateAnimation(
                0,
                0,
                0,
                -userContainer.getHeight());
        animate.setDuration(DELAY_ANIMATION);
        animate.setFillAfter(true);
        userContainer.startAnimation(animate);
        userContainer.setVisibility(GONE);
        new CountDownTimer(DELAY_ANIMATION, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                buttonUser.setEnabled(true);
                buttonCloseUserSection.setEnabled(true);
                changeStatusBarColor(R.color.toto_background_gradient_blue);
            }
        }.start();
    }

    public void openUserSection() {
        buttonUser.setEnabled(false);
        buttonCloseUserSection.setEnabled(false);
        changeStatusBarColor(R.color.toto_dark_grey);
        userContainer.setVisibility(View.VISIBLE);
        TranslateAnimation animate = new TranslateAnimation(
                0,
                0,
                -userContainer.getHeight(),
                0);
        animate.setDuration(DELAY_ANIMATION);
        animate.setFillAfter(true);
        userContainer.startAnimation(animate);
        new CountDownTimer(DELAY_ANIMATION, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                buttonCloseUserSection.setEnabled(true);
                buttonCloseUserSection.setEnabled(true);
            }
        }.start();
    }

    public void onUserSectionButtonClick() {
        if (userSectionIsOpen) {
            closeUserSection();
        } else {
            openUserSection();
        }
        userSectionIsOpen = !userSectionIsOpen;
    }

    private void changeStatusBarColor(int color) {
        Window window = requireActivity().getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(getResources().getColor(color));
    }

    private void forgetIdentifier() {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("identifier", "");
        editor.apply();
    }

}