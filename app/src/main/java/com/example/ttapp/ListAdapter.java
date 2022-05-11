package com.example.ttapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.ttapp.survey.model.MultipleChoiceOption;

import java.util.List;

public class ListAdapter extends ArrayAdapter<MultipleChoiceOption> {

    Context context;

    public ListAdapter(@NonNull Context context, @NonNull List<MultipleChoiceOption> list) {
        super(context, R.layout.list_row, list);
        this.context = context;
    }

    /**
     * Method called every time a listView's row is being created, for lists using this adapter.
     * Gets the design and content of a row in the listView
     * @return the view of the list row
     * */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_row,
                    parent, false);
        }

        MultipleChoiceOption option = getItem(position);
        String text = option.getText();
        TextView textView = convertView.findViewById(R.id.textview_multibutton);
        textView.setText(text);
        ConstraintLayout multiConstraintLayout = convertView.findViewById(R.id.multiConstraintLayout);
        ImageView multiCheck = convertView.findViewById(R.id.check_multibutton);
        if (option.isSelected()) {
            multiCheck.setVisibility(View.VISIBLE);
            multiConstraintLayout.setBackgroundResource(R.drawable.background_multibutton_light);
        } else {
            multiCheck.setVisibility(View.INVISIBLE);
            multiConstraintLayout.setBackgroundResource(R.drawable.background_multibutton);
        }

        return convertView;
    }
    @Override
    public int getViewTypeCount() {
        if (getCount() == 0) {
            return 1;
        }
        return getCount();
    }

    @Override
    public int getItemViewType(int position) {

        return position;
    }

}
