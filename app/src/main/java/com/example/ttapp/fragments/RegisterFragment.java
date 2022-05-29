package com.example.ttapp.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.ttapp.R;
import com.example.ttapp.database.Database;
import com.example.ttapp.databinding.FragmentRegisterBinding;
import com.example.ttapp.network.NetworkCallbackObservable;
import com.example.ttapp.viewmodel.RegisterViewModel;

/**
 * Class for a fragment that presents the sign in screen
 */
public class RegisterFragment extends Fragment {

    private SharedPreferences sharedPref;
    private RegisterViewModel registerViewModel;
    private String identifier;
    private EditText idEditText;
    private Button confirmButton;
    private TextView errorMessage;
    private ProgressBar loading;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentRegisterBinding binding = FragmentRegisterBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();

        changeStatusBarColor(R.color.toto_background_gradient_blue);

        sharedPref = requireActivity().getPreferences(Context.MODE_PRIVATE);
        registerViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);
        registerViewModel.setDatabase(Database.getInstance());
        observeIdentification(root);
        observeDatabaseAccess();

        idEditText = binding.textField;
        confirmButton = binding.buttonConfirmIdCode;
        errorMessage = binding.errorMessage;
        loading = binding.loadingProgressBar;

        confirmButton.setOnClickListener(view1 -> {
            if (NetworkCallbackObservable.getInstance().isNetworkAvailable(requireContext())) {
                identify();
            } else {
                errorMessage.setText(R.string.error_network);
            }
        });

        idEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    closeKeyboard();
                    if (NetworkCallbackObservable.getInstance().isNetworkAvailable(requireContext())) {
                        identify();
                        handled = true;
                    } else {
                        errorMessage.setText(R.string.error_network);
                    }
                }
                return handled;
            }
        });

        return root;
    }

    private void closeKeyboard() {
        View view = requireActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager manager = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void changeStatusBarColor(int color) {
        Window window = requireActivity().getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(getResources().getColor(color));
    }

    private void observeIdentification(View root) {
        registerViewModel.getIdentified().observe(getViewLifecycleOwner(), identification -> {
            if (identification) {
                saveIdentifier(identifier);
                Navigation.findNavController(root).navigate(R.id.action_registerFragment_to_homeFragment);
            } else {
                stopLoading();
                errorMessage.setText(R.string.error_id_not_found);
            }
        });
    }

    private void observeDatabaseAccess() {
        registerViewModel.getDatabaseAccess().observe(getViewLifecycleOwner(), databaseAccess -> {
            if (!databaseAccess) {
                //Toast.makeText(getContext(), R.string.session_expired_toast, Toast.LENGTH_SHORT).show();
                errorMessage.setText(R.string.error_database);
                stopLoading();
            }
        });
    }

    private void identify() {
        // Reset error messages
        errorMessage.setText("");

        identifier = idEditText.getText().toString();
        if (identifier.isEmpty()) {
            // No identifier have been entered
            errorMessage.setText(R.string.error_id_is_empty);
        } else {
            // Identification is done by RegisterViewModel
            registerViewModel.identify(identifier);
            startLoading();
        }
    }

    private void startLoading() {
        loading.setVisibility(View.VISIBLE);
        confirmButton.setVisibility(View.INVISIBLE);
    }

    private void stopLoading() {
        loading.setVisibility(View.INVISIBLE);
        confirmButton.setVisibility(View.VISIBLE);
    }

    private void saveIdentifier(String identifier) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("identifier", identifier);
        editor.apply();
    }

}