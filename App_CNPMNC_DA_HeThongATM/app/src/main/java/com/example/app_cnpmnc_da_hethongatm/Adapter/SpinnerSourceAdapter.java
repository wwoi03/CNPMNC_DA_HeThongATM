package com.example.app_cnpmnc_da_hethongatm.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.app_cnpmnc_da_hethongatm.Model.GuiTietKiem;
import com.example.app_cnpmnc_da_hethongatm.Model.TaiKhoanLienKet;
import com.example.app_cnpmnc_da_hethongatm.R;

import java.util.List;

public class SpinnerSourceAdapter extends BaseAdapter {
    private List<TaiKhoanLienKet> lienKetList;
    Context mContext;

    public SpinnerSourceAdapter( List<TaiKhoanLienKet> lienKetList, Context mContext) {
        this.lienKetList = lienKetList;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return lienKetList.size();
    }

    @Override
    public Object getItem(int position) {
        return lienKetList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(R.layout.item_deposit, parent, false);

        TaiKhoanLienKet taiKhoanLienKet = lienKetList.get(position);

        TextView txtSource = convertView.findViewById(R.id.txtGuiTietKiem);

        txtSource.setText(String.valueOf(taiKhoanLienKet.getSoTaiKhoan()));

        return  convertView;
    }
}
