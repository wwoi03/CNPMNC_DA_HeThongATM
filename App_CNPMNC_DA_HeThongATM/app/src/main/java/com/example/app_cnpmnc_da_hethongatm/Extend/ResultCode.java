package com.example.app_cnpmnc_da_hethongatm.Extend;

public class ResultCode {
    public static int PAYMENT_ACCOUNT_TYPE = 105; // tài khoản thanh toán
    public static int SAVING_ACCOUNT_TYPE = 106; // tài khoản tiết kiệm

    public static String CHUYEN_TIEN = "CT"; // loại giao dịch: chuyển tiền
    public static String RUT_TIET_KIEM = "RTK"; // loại giao dịch: Rút tiết kiệm
    public static String GUI_TIET_KIEM = "GTK"; // loại giao dịch: Gửi tiết kiệm
    public static String NOP_TIET_KIEM = "NTK"; // loại giao dịch: Nộp tiết kiệm
}
/*
    C1: cái key của record mh sẽ lưu là CT,RT, RTK, .... kiểu vậy, khi ông chuyển tiền, chỉ cần gọi lại mấy biến này là đc
    C2: key vẫn dữ riêng, tạo thêm một cột MaLoaiGD như là CT, RT, RTK,.... Khi ông thực hiện chuyển tiền ông cx sẽ gọi đến các biến này,
       từ đó truy suất đế bảng LoaiGiaoDich rồi lấy key gắn vào bảng GiaoDich (khả quan nhất)
    C3: so sánh chuỗi có chữ chuyển tiền theo Tên Loại Giao Dịch trong bảng LoaijGiaoDich rồi lấy Key
*/
