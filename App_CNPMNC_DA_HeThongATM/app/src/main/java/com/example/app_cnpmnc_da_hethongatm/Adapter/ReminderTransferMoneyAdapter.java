package com.example.app_cnpmnc_da_hethongatm.Adapter;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_cnpmnc_da_hethongatm.Extend.ResultCode;
import com.example.app_cnpmnc_da_hethongatm.Model.NhacChuyenTien;
import com.example.app_cnpmnc_da_hethongatm.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class ReminderTransferMoneyAdapter extends FirebaseRecyclerAdapter<NhacChuyenTien, ReminderTransferMoneyAdapter.ReminderTransferMoneyVH> {
    Listener listener;
    public ReminderTransferMoneyAdapter(@NonNull FirebaseRecyclerOptions<NhacChuyenTien> options, Listener listener) {
        super(options);
        this.listener = listener;
    }

    @Override
    protected void onBindViewHolder(@NonNull ReminderTransferMoneyVH holder, int position, @NonNull NhacChuyenTien model) {
        // kiểm tra hiển thị icon theo trạng thái nhắc chuyển tiền
        if (model.checkLate() && model.getTrangThai() == ResultCode.CHUA_DEN_HAN) { // quá hạn
            holder.ivReminderIcon.setImageResource(R.drawable.circle_exclamation_solid);
            holder.ivReminderIcon.setColorFilter(R.color.warning_color);
        } else if (model.getTrangThai() == ResultCode.CHUA_DEN_HAN) { // chưa đến hạn
            holder.ivReminderIcon.setImageResource(R.drawable.clock_solid);
            holder.ivReminderIcon.setColorFilter(R.color.in_phase_color);
        } else if (model.getTrangThai() == ResultCode.DA_CHUYEN_TIEN) { // đã chuyển tiền
            holder.ivReminderIcon.setImageResource(R.drawable.circle_check_solid);
            holder.ivReminderIcon.setColorFilter(R.color.done_color);
        }

        holder.tvContent.setText(model.getNoiDungNhac());

        holder.tvReminderDay.setText(model.getNgayHetHan());

        holder.tvAmountMoney.setText(model.getSoTienNhacChuyenFormat());
    }

    @NonNull
    @Override
    public ReminderTransferMoneyVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reminder_transfer_money, parent, false);
        return new ReminderTransferMoneyVH(view);
    }

    @Override
    public void onDataChanged() {
        super.onDataChanged();
    }

    class ReminderTransferMoneyVH extends RecyclerView.ViewHolder {
        TextView tvContent, tvReminderDay, tvAmountMoney;
        ImageView ivReminderIcon;


        public ReminderTransferMoneyVH(@NonNull View itemView) {
            super(itemView);

            tvContent = itemView.findViewById(R.id.tvContent);
            tvReminderDay = itemView.findViewById(R.id.tvReminderDay);
            tvAmountMoney = itemView.findViewById(R.id.tvAmountMoney);
            ivReminderIcon = itemView.findViewById(R.id.ivReminderIcon);
        }
    }

    public interface Listener {
    }
}
