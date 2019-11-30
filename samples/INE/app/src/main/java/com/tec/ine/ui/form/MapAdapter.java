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
import java.util.Map;

public class MapAdapter extends ArrayAdapter<String> {
    private Context mContext;
    private List<String> propertyList;

    public MapAdapter(@NonNull Context context, List propertyList) {
        super(context, 0, propertyList);
        this.mContext = context;
        this.propertyList = propertyList;
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(android.R.layout.simple_list_item_1, parent, false);

        String string = propertyList.get(position);
        TextView name = (TextView) listItem.findViewById(android.R.id.text1);
        name.setText(string);
        return listItem;
    }

    @Override
    public int getCount() {
        return propertyList.size();
    }

    @Nullable
    @Override
    public String getItem(int position) {
        return propertyList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }


}
