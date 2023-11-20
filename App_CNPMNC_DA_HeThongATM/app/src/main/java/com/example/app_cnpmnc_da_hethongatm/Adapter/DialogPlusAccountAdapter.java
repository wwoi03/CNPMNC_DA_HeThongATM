package com.example.app_cnpmnc_da_hethongatm.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_cnpmnc_da_hethongatm.Model.TaiKhoanLienKet;
import com.example.app_cnpmnc_da_hethongatm.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class DialogPlusAccountAdapter extends FirebaseRecyclerAdapter<TaiKhoanLienKet, DialogPlusAccountAdapter.DialogPlusAccountVH> {
    Listener listener;

    public DialogPlusAccountAdapter(@NonNull FirebaseRecyclerOptions<TaiKhoanLienKet> options, Listener listener) {
        super(options);
        this.listener = listener;
    }

    @Override
    protected void onBindViewHolder(@NonNull DialogPlusAccountVH holder, int position, @NonNull TaiKhoanLienKet model) {
        holder.tvAccountNumber.setText(String.valueOf(model.getSoTaiKhoan()));
        holder.tvSurplus.setText(model.getSoDuFormat() + " VND");

        initListener(holder, position, model);
    }

    @NonNull
    @Override
    public DialogPlusAccountVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_account_dialog_plus, parent, false);
        return new DialogPlusAccountVH(view);
    }

    // lắng nghe sự kiện
    private void initListener(@NonNull DialogPlusAccountVH holder, int position, @NonNull TaiKhoanLienKet model) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClickItemDialogPlusAccountListener(model);
            }
        });
    }

    class DialogPlusAccountVH extends RecyclerView.ViewHolder {
        TextView tvAccountNumber, tvSurplus;

        public DialogPlusAccountVH(@NonNull View itemView) {
            super(itemView);

            tvAccountNumber = itemView.findViewById(R.id.tvAccountNumber);
            tvSurplus = itemView.findViewById(R.id.tvSurplus);
        }
    }

    public interface Listener {
        void onClickItemDialogPlusAccountListener(TaiKhoanLienKet taiKhoanLienKet);
    }
}
