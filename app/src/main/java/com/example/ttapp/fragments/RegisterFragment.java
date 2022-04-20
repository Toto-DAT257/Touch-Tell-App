package com.example.ttapp.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
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

import org.bson.Document;

import io.realm.mongodb.RealmResultTask;

/**
 * Class for a fragment that presents the sign in screen
 *
 * @author Emelie Edberg
 */
public class RegisterFragment extends Fragment {

    FragmentRegisterBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentRegisterBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();


        EditText codeEditText = binding.editTextIdCode;
        Button confirmButton = binding.buttonConfirmIdCode;
        TextView errorCodeIsEmpty = binding.errorCodeIsEmpty;
        TextView errorIdentifierNotFound = binding.errorIdentifierNotFound;

        confirmButton.setOnClickListener(view1 -> login(root, codeEditText, errorCodeIsEmpty, errorIdentifierNotFound));
        return root;
    }

    private void login(View root, EditText codeEditText, TextView errorCodeIsEmpty, TextView errorIdentifierNotFound) {
        String identifier = codeEditText.getText().toString();

        if (identifier.isEmpty()) {
            // display error message
            errorCodeIsEmpty.setVisibility(View.VISIBLE);
        } else {
            // hide error message
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