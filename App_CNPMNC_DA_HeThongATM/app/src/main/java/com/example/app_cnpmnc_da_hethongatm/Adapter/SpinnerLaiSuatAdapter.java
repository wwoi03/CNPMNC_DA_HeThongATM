package com.example.app_cnpmnc_da_hethongatm.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.app_cnpmnc_da_hethongatm.Model.LaiSuat;
import com.example.app_cnpmnc_da_hethongatm.R;

import java.util.ArrayList;
import java.util.List;

public class SpinnerLaiSuatAdapter extends BaseAdapter {

    private List<LaiSuat> laiSuatArrayList;
    Context mContext;

    public SpinnerLaiSuatAdapter(List<LaiSuat> laiSuatArrayList, Context mContext) {
        this.laiSuatArrayList = laiSuatArrayList;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return laiSuatArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return laiSuatArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(R.layout.item_laisuat, parent, false);

        LaiSuat laiSuat = laiSuatArrayList.get(position);
        TextView txtkyhan = convertView.findViewById(R.id.txtKyHan);
        TextView txtlaisuat = convertView.findViewById(R.id.txtTiLe);

        txtkyhan.setText(laiSuat.getKyHan());
        txtlaisuat.setText(String.valueOf(laiSuat.getTiLe()));

        return  convertView;
    }
}
