package com.example.ttapp.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
    EditText codeEditText;
    Button confirmButton;
    TextView errorCodeIsEmpty;
    TextView errorIdentifierNotFound;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentRegisterBinding binding = FragmentRegisterBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();

        registerViewModel = new ViewModelProvider(requireActivity()).get(RegisterViewModel.class);
        registerViewModel.setDatabase(MongoDB.getDatabase(getContext()));

        codeEditText = binding.editTextIdCode;
        confirmButton = binding.buttonConfirmIdCode;
        errorCodeIsEmpty = binding.errorCodeIsEmpty;
        errorIdentifierNotFound = binding.errorIdentifierNotFound;

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
                errorIdentifierNotFound.setVisibility(View.VISIBLE);
            }
        });
    }

    private void observeDatabaseAccess() {
        registerViewModel.getDatabaseAccess().observe(getViewLifecycleOwner(), databaseAccess -> {
            if (!databaseAccess) {
                Toast.makeText(getContext(), R.string.SessionExpiredToast, Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void identify() {
        identifier = codeEditText.getText().toString();
        if (identifier.isEmpty()) {
            errorCodeIsEmpty.setVisibility(View.VISIBLE);
        } else {
            registerViewModel.identify(identifier);
            errorCodeIsEmpty.setVisibility(View.INVISIBLE);
        }
    }

    private void saveIdentifier(String identifier) {
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("identifier", identifier);
        editor.apply();
    }
}