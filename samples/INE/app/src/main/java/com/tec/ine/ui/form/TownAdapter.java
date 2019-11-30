package com.tec.ine.ui.form;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.tec.ine.models.Town;

import java.util.ArrayList;
import java.util.List;

public class TownAdapter extends ArrayAdapter<Town> {
    private Context mContext;
    private List<Town> towns;

    public TownAdapter(@NonNull Context context, List<Town> towns) {
        super(context, 0, towns);
        this.mContext = context;
        this.towns = towns;
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(android.R.layout.simple_list_item_1, parent, false);

        Town town = towns.get(position);
        TextView name = (TextView) listItem.findViewById(android.R.id.text1);
        name.setText(String.valueOf(town.getId()) + " | " + town.getName());
        return listItem;
    }

    @Override
    public int getCount() {
        return towns.size();
    }

    @Nullable
    @Override
    public Town getItem(int position) {
        return towns.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public List<Town> getTowns() {
        return towns;
    }
}
