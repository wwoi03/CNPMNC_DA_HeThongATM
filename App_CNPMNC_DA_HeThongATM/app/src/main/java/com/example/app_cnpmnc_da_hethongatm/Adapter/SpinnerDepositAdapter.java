package com.example.app_cnpmnc_da_hethongatm.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.app_cnpmnc_da_hethongatm.Model.GuiTietKiem;
import com.example.app_cnpmnc_da_hethongatm.Model.LaiSuat;
import com.example.app_cnpmnc_da_hethongatm.R;

import java.util.List;

public class SpinnerDepositAdapter extends BaseAdapter {

    private List<GuiTietKiem> guiTietKiemList;
    Context mContext;

    public SpinnerDepositAdapter( List<GuiTietKiem> guiTietKiemList, Context mContext) {
        this.guiTietKiemList = guiTietKiemList;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return guiTietKiemList.size();
    }

    @Override
    public Object getItem(int position) {
        return guiTietKiemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(R.layout.item_deposit, parent, false);

        GuiTietKiem guiTietKiem = guiTietKiemList.get(position);
        TextView txtGTK = convertView.findViewById(R.id.txtGuiTietKiem);

        txtGTK.setText(String.valueOf(guiTietKiem.getTaiKhoanTietKiem()));

        return  convertView;
    }
}

