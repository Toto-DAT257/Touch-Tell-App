package com.example.ttapp.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.ttapp.R;
import com.example.ttapp.database.MongoDB;
import com.example.ttapp.databinding.FragmentRegisterBinding;
import com.example.ttapp.viewmodel.RegisterViewModel;

import org.bson.Document;

import io.realm.mongodb.App;
import io.realm.mongodb.RealmResultTask;
import io.realm.mongodb.User;

/**
 * Class for a fragment that presents the sign in screen
 *
 * @author Emelie Edberg
 */
public class RegisterFragment extends Fragment {

    private FragmentRegisterBinding binding;
    private RegisterViewModel registerViewModel;
    private String identifier;
    EditText codeEditText;
    Button confirmButton;
    TextView errorCodeIsEmpty;
    TextView errorIdentifierNotFound;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentRegisterBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();
        registerViewModel = new ViewModelProvider(requireActivity()).get(RegisterViewModel.class);
        registerViewModel.setDatabase(MongoDB.getDatabase(getContext()));

        codeEditText = binding.editTextIdCode;
        confirmButton = binding.buttonConfirmIdCode;
        errorCodeIsEmpty = binding.errorCodeIsEmpty;
        errorIdentifierNotFound = binding.errorIdentifierNotFound;

        confirmButton.setOnClickListener(view1 -> {
            identify();
            //login(root, codeEditText, errorCodeIsEmpty, errorIdentifierNotFound);
        });
        registerViewModel.getIdentified().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean identification) {
                if (identification) {
                    saveIdentifier(identifier);
                    Navigation.findNavController(root).navigate(R.id.action_registerFragment_to_firstQuestionFragment);
                }
                else {
                    errorIdentifierNotFound.setVisibility(View.VISIBLE);
                }
            }
        });
        return root;
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

    private void login(View root, EditText codeEditText, TextView errorCodeIsEmpty, TextView errorIdentifierNotFound) {
        String identifier = codeEditText.getText().toString();

        if (identifier.isEmpty()) {
            errorCodeIsEmpty.setVisibility(View.VISIBLE);
        } else {
            errorCodeIsEmpty.setVisibility(View.INVISIBLE);
            MongoDB db = MongoDB.getDatabase(getContext());
            RealmResultTask<Document> task = db.getDeviceIdTask(identifier);
            task.getAsync(result -> {
                if (result.isSuccess()) {
                    if (result.get() != null) {
                        saveIdentifier(identifier);
                        Navigation.findNavController(root).navigate(R.id.action_registerFragment_to_firstQuestionFragment);
                        Log.v("LOGIN", "Identifier " + result.get().toString());
                    }
                    else {
                        Log.v("LOGIN", "Identifier not registered");
                        errorIdentifierNotFound.setVisibility(View.VISIBLE);
                    }
                }
                else {
                    Log.v("LOGIN", "Database access failed." + result.getError().toString());
                    MongoDB.getMongoApp().currentUser().logOutAsync(result1 -> Log.v("LOGOUT:", String.valueOf(result1.isSuccess())));
                }
            });
        }
    }

    private void saveIdentifier(String identifier) {
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("identifier", identifier);
        editor.apply();
    }
}