﻿$("#cccd").change(function () {
    const cccdValue = $(this).val() as string;

    // Gửi yêu cầu GET đến controller với dữ liệu cccdValue
    $.get("/Customer/ActionName", { cccd: cccdValue }, function (data) {
        // Xử lý dữ liệu trả về (nếu có)
        if (data == true) {
            alert("khách hàng tồn tại");
            $("#cccd").val("");
        }
        
    });
});
