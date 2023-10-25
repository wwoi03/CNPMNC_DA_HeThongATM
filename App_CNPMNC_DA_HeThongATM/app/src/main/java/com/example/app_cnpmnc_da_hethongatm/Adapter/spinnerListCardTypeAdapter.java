//package com.example.app_cnpmnc_da_hethongatm.Adapter;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//
//public class spinnerListCardTypeAdapter extends ArrayAdapter<LoaiTheNganHang> {
//    private String maloai;
//    public spinnerListCardTypeAdapter(@NonNull Context context){
//        super(context,android.R.layout.simple_spinner_item);
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        return createItemView(position, convertView, parent);
//    }
//
//    @Override
//    public View getDropDownView(int position, View convertView, ViewGroup parent) {
//        return createItemView(position, convertView, parent);
//    }
//    public View createItemView (int position, View convertView, ViewGroup parent){
//        LoaiTheNganHang loaiTheNganHang=getItem(position);
//
//        if (convertView == null) {
//            convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_spinner_item, parent, false);
//        }
//
//        TextView textView = convertView.findViewById(android.R.id.text1);
//
//
//        if (loaiTheNganHang != null) {
//            textView.setText(String.valueOf(loaiTheNganHang.getTenTNH()));
//        }
//
//
//        convertView.setOnClickListener(view -> {
//
//            if (loaiTheNganHang != null) {
//                maloai = loaiTheNganHang.getMaLoaiTNH();
//            }
//        });
//        return convertView;
//    }
//    public String getMaloai(){
//        return maloai;
//    }
//}
