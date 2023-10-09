package com.example.app_cnpmnc_da_hethongatm.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.app_cnpmnc_da_hethongatm.Model.TheNganHang;



public class spinnerListCardAdapter extends ArrayAdapter<TheNganHang> {



    public spinnerListCardAdapter(@NonNull Context context) {
        super(context,android.R.layout.simple_spinner_item);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return createItemView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return createItemView(position, convertView, parent);
    }
    public View createItemView (int position, View convertView, ViewGroup parent){
        TheNganHang theNganHang = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_spinner_item, parent, false);
        }

        // Lookup view for data population
        TextView textView = convertView.findViewById(android.R.id.text1);

        // Populate the data into the template view
        if (theNganHang != null) {
            textView.setText(String.valueOf(theNganHang.getMaSoThe()));
        }

        // Set click listener for the item
        convertView.setOnClickListener(view -> {
            // Handle item click event here
            if (theNganHang != null) {
                String mathe = String.valueOf(theNganHang.getMaSoThe());
            }
        });
        return convertView;
    }


}
