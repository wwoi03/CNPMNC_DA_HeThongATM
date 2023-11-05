package com.example.app_cnpmnc_da_hethongatm.Extend;

public class ResultCode {
    // Loại tài khoản
    public static int PAYMENT_ACCOUNT_TYPE = 105; // tài khoản thanh toán
    public static int SAVING_ACCOUNT_TYPE = 106; // tài khoản tiết kiệm

    // Loại giao dịch
    public static String CHUYEN_TIEN = "CT"; // loại giao dịch: chuyển tiền
    public static String RUT_TIET_KIEM = "RTK"; // loại giao dịch: Rút tiết kiệm
    public static String GUI_TIET_KIEM = "GTK"; // loại giao dịch: Gửi tiết kiệm
    public static String NOP_TIET_KIEM = "NTK"; // loại giao dịch: Nộp tiết kiệm

    // Trạng thái nhắc chuyển tiền:
    public static int DA_CHUYEN_TIEN = 1; // đã chuyển tiền
    public static int QUA_HAN_CHUYEN_TIEN = 2; // quá hạn chuyển tiền
    public static int CHUA_DEN_HAN = 0; // // chưa đến hạn

    // Nhắc chuyển tiền
    public static int ADD_REMINDER_TRANSFER_MONEY = 101; // thêm nhắc chuyển tiền
    public static int EDIT_REMINDER_TRANSFER_MONEY = 102; // sửa nhắc chuyển tiền
}