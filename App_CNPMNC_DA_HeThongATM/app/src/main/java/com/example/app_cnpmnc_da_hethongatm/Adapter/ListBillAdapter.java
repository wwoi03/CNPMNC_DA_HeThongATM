package com.example.app_cnpmnc_da_hethongatm.Adapter;

import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_cnpmnc_da_hethongatm.Activities.TransferMoneyActivity;
import com.example.app_cnpmnc_da_hethongatm.Extend.DbHelper;
import com.example.app_cnpmnc_da_hethongatm.Model.MauChuyenTien;
import com.example.app_cnpmnc_da_hethongatm.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class ListBillAdapter extends RecyclerView.Adapter<ListBillAdapter.ListBillHolder> {

    private List<MauChuyenTien> mauChuyenTiens;

    public ListBillAdapter(List<MauChuyenTien> mauChuyenTiens) {
        this.mauChuyenTiens = mauChuyenTiens;
    }

    @NonNull
    @Override
    public ListBillHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_savebill,parent,false);
        return new ListBillHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListBillHolder holder, int position) {
        MauChuyenTien mauChuyenTien = mauChuyenTiens.get(position);
        if(mauChuyenTien == null){
            return;
        }
        holder.item_TenNguoiNhan.setText(mauChuyenTien.getTenNguoiNhan());
        holder.item_TienGD.setText(String.valueOf(mauChuyenTien.getSoTien()));
        holder.item_NoiDungLuu.setText(mauChuyenTien.getNoiDung());
        holder.item_stkLuu.setText(String.valueOf(mauChuyenTien.getTaiKhoanNhan()));
        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Thông báo");
                builder.setMessage("Bạn có chắc chắn muốn xóa không?");
                builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("MauChuyenTien/" + mauChuyenTien.getKey());
                        databaseReference.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                DbHelper.BuilderXinXo(v.getContext(),"Xóa thành công");
                            }

                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                DbHelper.BuilderXinXo(v.getContext(),"Xóa Khong thành công");
                            }
                        });
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        holder.cv_itembill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent12 = new Intent(v.getContext(), TransferMoneyActivity.class);
                intent12.putExtra("flag1",1000);
                intent12.putExtra("STK123",mauChuyenTien.getTaiKhoanNhan());
                Log.d(String.valueOf(mauChuyenTien.getTaiKhoanNhan()), "TestTrcIntent: ");
                intent12.putExtra("NoiDung123",mauChuyenTien.getNoiDung());
                Log.d(String.valueOf(mauChuyenTien.getNoiDung()), "TestTrcIntent2: ");
                intent12.putExtra("SoTien123",mauChuyenTien.getSoTien());
                v.getContext().startActivity(intent12);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(mauChuyenTiens != null){
            return mauChuyenTiens.size();
        }
        return 0;
    }

    public class ListBillHolder extends RecyclerView.ViewHolder{

        TextView item_TenNguoiNhan,item_TienGD,item_NoiDungLuu,item_stkLuu;
        ImageView btn_delete;
        CardView cv_itembill;
        public ListBillHolder(@NonNull View itemView) {
            super(itemView);
            item_TenNguoiNhan = itemView.findViewById(R.id.item_TenNguoiNhan);
            item_TienGD = itemView.findViewById(R.id.item_TienGD);
            item_NoiDungLuu = itemView.findViewById(R.id.item_NoiDungLuu);
            item_stkLuu = itemView.findViewById(R.id.item_stkLuu);
            btn_delete = itemView.findViewById(R.id.btn_delete);
            cv_itembill = itemView.findViewById(R.id.cv_itembill);
        }
    }
}
