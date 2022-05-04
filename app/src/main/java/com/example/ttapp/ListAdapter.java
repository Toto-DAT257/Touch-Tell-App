package com.example.ttapp;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class ListAdapter extends ArrayAdapter<String> {

    Context context;

    public ListAdapter(@NonNull Context context, @NonNull List<String> list) {
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
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_row,
                    parent, false);
        }

        // position i n list

        //TextView name = convertView.findViewById(R.id.category_name);
        //View circle = convertView.findViewById(R.id.category_circle);

        //name.setText(cat.getName());

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
