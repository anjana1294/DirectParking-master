package com.directparking.app.ui.common;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.directparking.app.R;
import com.directparking.app.ui.signup.model.IdNameItem;

import java.util.List;

public class CustomSpinnerAdapter extends ArrayAdapter<IdNameItem> {

    private List<IdNameItem> items;
    private Spinner spinner;

    public CustomSpinnerAdapter(Context context, int resource, Spinner spinner, List<IdNameItem> items) {
        super(context, resource, items);
        this.spinner = spinner;
        this.items = items;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        View row = convertView;
        SpinnerItemHolder holder;

        if (row == null) {
            row = LayoutInflater.from(parent.getContext()).inflate(R.layout.spinner_hint_layout, parent, false);
            holder = new SpinnerItemHolder();

            holder.tilTitle = row.findViewById(R.id.til_title);
            holder.etTitle = row.findViewById(R.id.et_title);
            row.setTag(holder);
        } else {
            holder = (SpinnerItemHolder) row.getTag();
        }

        if (position == 0) {
            if (items.get(position).getName().contains("|")) {
                holder.etTitle.setHint(items.get(position).getName().split("\\|")[0]);
            } else {
                holder.etTitle.setHint(items.get(position).getName());
            }
        } else {
            holder.tilTitle.setHint(items.get(0).getName());

            if (items.get(position).getName().contains("|")) {
                holder.etTitle.setText(items.get(position).getName().split("\\|")[0]);
            } else {
                holder.etTitle.setText(items.get(position).getName());
            }
        }

        holder.etTitle.setOnClickListener(view -> spinner.performClick());

        return row;
    }

    @Override
    public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {

        View view;

        if (position == 0) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.spinner_dropdown_item_layout, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_spinner_dropdown_item, parent, false);
            TextView textview = view.findViewById(android.R.id.text1);
            if (items.get(position).getName().contains("|")) {
                textview.setText(items.get(position).getName().split("\\|")[0]);
            } else {
                textview.setText(items.get(position).getName());
            }
        }

        return view;
    }

    static class SpinnerItemHolder {
        TextInputLayout tilTitle;
        TextInputEditText etTitle;
    }
}