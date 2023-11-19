package com.example.app_cnpmnc_da_hethongatm.Extend;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.app_cnpmnc_da_hethongatm.R;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class UtilityClass {

    // Hiển thị thông báo lỗi
    public static void showDialogError(Context context, String textTitle, String textContent) {
        new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE).setTitleText(textTitle).setContentText(textContent).show();
    }

    // setupToolbar
    public static void setupToolbar(AppCompatActivity activity, Toolbar tbToolbar, String title) {
        tbToolbar.setTitle("");
        activity.setSupportActionBar(tbToolbar);

        TextView textView = new TextView(activity);
        textView.setText(title);
        textView.setTextSize(20);
        textView.setTextColor(Color.WHITE);
        textView.setLayoutParams(new androidx.appcompat.widget.Toolbar.LayoutParams(androidx.appcompat.widget.Toolbar.LayoutParams.WRAP_CONTENT, androidx.appcompat.widget.Toolbar.LayoutParams.WRAP_CONTENT, Gravity.CENTER_HORIZONTAL));
        tbToolbar.addView(textView);

        // kích hoạt nút quay lại trên ActionBar
        if (activity.getSupportActionBar() != null) {
            // Đặt màu trắng cho nút quay lại
            final Drawable upArrow = activity.getResources().getDrawable(R.drawable.baseline_chevron_left_24);
            upArrow.setColorFilter(activity.getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_ATOP);
            activity.getSupportActionBar().setHomeAsUpIndicator(upArrow);

            // Hiển thị nút quay lại
            activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }
}
