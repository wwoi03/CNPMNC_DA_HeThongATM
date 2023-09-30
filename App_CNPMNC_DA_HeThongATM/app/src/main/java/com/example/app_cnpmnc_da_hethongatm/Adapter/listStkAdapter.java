package com.example.app_cnpmnc_da_hethongatm.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_cnpmnc_da_hethongatm.Activities.BillActivity;
import com.example.app_cnpmnc_da_hethongatm.Activities.TransferMoneyActivity;
import com.example.app_cnpmnc_da_hethongatm.Model.TransferMoney;
import com.example.app_cnpmnc_da_hethongatm.R;

import java.util.List;

public class listStkAdapter extends RecyclerView.Adapter<listStkAdapter.listStkViewHolder> {

    public List<TransferMoney> transferMonies;

    public listStkAdapter(List<TransferMoney> transferMonies) {
        this.transferMonies = transferMonies;
    }

    @NonNull
    @Override
    public listStkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_rowstk,parent,false);
        return new listStkViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull listStkViewHolder holder, int position) {
        TransferMoney transferMoney = transferMonies.get(position);
        if(transferMoney ==null){
            return;
        }
        holder.stk.setText("So Tai Khoan: "+transferMoney.getSoTaiKhoan());
        holder.soDu.setText("So Du: "+transferMoney.getSoDu());
        if(transferMoney.getTinhTrangTK() != 1){
            holder.cv_STK.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    builder.setTitle("Có Lỗi");
                    builder.setMessage("STK này đã bị khóa liên hệ cskh để biết thêm");
                    builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            });
            holder.btn_history.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    builder.setTitle("Có Lỗi");
                    builder.setMessage("STK này đã bị khóa liên hệ cskh để biết thêm");
                    builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            });
        }
        else {
            holder.cv_STK.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, TransferMoneyActivity.class);
                    intent.putExtra("transferMoney",transferMoney);
                    context.startActivity(intent);
                }
            });
            holder.btn_history.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), BillActivity.class);
                    intent.putExtra("transferMoney",transferMoney);
                    v.getContext().startActivity(intent);
                }
            });
        }


    }

    @Override
    public int getItemCount() {
        if(transferMonies!=null){
            return transferMonies.size();
        }
        return 0;
    }

    public class listStkViewHolder extends RecyclerView.ViewHolder{

        private TextView stk,soDu;
        private CardView cv_STK;
        private Button btn_history;

        public listStkViewHolder(@NonNull View itemView) {
            super(itemView);
            stk = itemView.findViewById(R.id.stk);
            soDu = itemView.findViewById(R.id.soDu);
            cv_STK =itemView.findViewById(R.id.cv_itembill);
            btn_history = itemView.findViewById(R.id.btn_history);
        }
    }
}
