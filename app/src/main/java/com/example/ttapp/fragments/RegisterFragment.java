package com.example.ttapp.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.ttapp.R;
import com.example.ttapp.database.MongoDB;
import com.example.ttapp.databinding.FragmentRegisterBinding;
import com.example.ttapp.viewmodel.RegisterViewModel;

/**
 * Class for a fragment that presents the sign in screen
 *
 * @author Emelie Edberg
 */
public class RegisterFragment extends Fragment {

    private RegisterViewModel registerViewModel;
    private String identifier;
    EditText idEditText;
    Button confirmButton;
    TextView errorIdIsEmpty;
    TextView errorIdNotFound;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentRegisterBinding binding = FragmentRegisterBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();

        registerViewModel = new ViewModelProvider(requireActivity()).get(RegisterViewModel.class);
        registerViewModel.setDatabase(MongoDB.getDatabase(getContext()));

        idEditText = binding.editTextId;
        confirmButton = binding.buttonConfirmIdCode;
        errorIdIsEmpty = binding.errorIdIsEmpty;
        errorIdNotFound = binding.errorIdNotFound;

        confirmButton.setOnClickListener(view1 -> identify());

        observeIdentification(root);
        observeDatabaseAccess();
        return root;
    }

    private void observeIdentification(View root) {
        registerViewModel.getIdentified().observe(getViewLifecycleOwner(), identification -> {
            if (identification) {
                saveIdentifier(identifier);
                Navigation.findNavController(root).navigate(R.id.action_registerFragment_to_firstQuestionFragment);
            } else {
                errorIdNotFound.setVisibility(View.VISIBLE);
            }
        });
    }

    private void observeDatabaseAccess() {
        registerViewModel.getDatabaseAccess().observe(getViewLifecycleOwner(), databaseAccess -> {
            if (!databaseAccess) {
                Toast.makeText(getContext(), R.string.session_expired_toast, Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void identify() {
        // Reset error messages
        errorIdIsEmpty.setVisibility(View.INVISIBLE);
        errorIdNotFound.setVisibility(View.INVISIBLE);

        identifier = idEditText.getText().toString();
        if (identifier.isEmpty()) {
            // No identifier have been entered
            errorIdIsEmpty.setVisibility(View.VISIBLE);
        } else if (false) {
            // TODO: Check if the id is registered
            // The entered identifier is not registered
            errorIdNotFound.setVisibility(View.VISIBLE);
        } else {
            // The entered identifier was a success
            registerViewModel.identify(identifier);
        }
    }

    private void saveIdentifier(String identifier) {
        SharedPreferences sharedPref = requireActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("identifier", identifier);
        editor.apply();
    }
}