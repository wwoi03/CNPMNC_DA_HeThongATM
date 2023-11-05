package com.example.app_cnpmnc_da_hethongatm.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_cnpmnc_da_hethongatm.Model.TaiKhoanLienKet;
import com.example.app_cnpmnc_da_hethongatm.Model.HanMucChuyenTien;
import com.example.app_cnpmnc_da_hethongatm.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class DialogPlusTransferLimitAdapter extends FirebaseRecyclerAdapter<HanMucChuyenTien, DialogPlusTransferLimitAdapter.DialogPlusTransferLimitVH> {
    Listener listener;

    public DialogPlusTransferLimitAdapter(@NonNull FirebaseRecyclerOptions<HanMucChuyenTien> options, Listener listener) {
        super(options);
        this.listener = listener;
    }

    @Override
    protected void onBindViewHolder(@NonNull DialogPlusTransferLimitVH holder, int position, @NonNull HanMucChuyenTien model) {
        holder.tvTransferLimit.setText(model.getNumberFormat(model.getHanMuc()) + " VND");
        holder.tvContent.setText(model.getNoiDung());

        initListener(holder, position, model);
    }

    @NonNull
    @Override
    public DialogPlusTransferLimitVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_transfer_limit_dialog_plus, parent, false);
        return new DialogPlusTransferLimitVH(view);
    }

    // lắng nghe sự kiện
    private void initListener(@NonNull DialogPlusTransferLimitVH holder, int position, @NonNull HanMucChuyenTien model) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClickItemDialogPlusTransferLimitListener(model);
            }
        });
    }

    class DialogPlusTransferLimitVH extends RecyclerView.ViewHolder {
        TextView tvTransferLimit, tvContent;

        public DialogPlusTransferLimitVH(@NonNull View itemView) {
            super(itemView);

            tvTransferLimit = itemView.findViewById(R.id.tvTransferLimit);
            tvContent = itemView.findViewById(R.id.tvContent);
        }
    }

    public interface Listener {
        void onClickItemDialogPlusTransferLimitListener(HanMucChuyenTien hanMucChuyenTien);
    }
}
