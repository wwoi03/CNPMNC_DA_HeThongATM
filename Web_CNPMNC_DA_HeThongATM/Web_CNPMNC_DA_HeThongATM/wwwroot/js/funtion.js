$(document).ready(function () {
    // Lắng nghe sự thay đổi của các trường nhập liệu
    $("#sonha, #phuongxa, #quanhuyen, #tinhthanh").change(function () {
        var sonha = $("#sonha").val();
        var phuongxa = $("#phuongxa").val();
        var quanhuyen = $("#quanhuyen").val();
        var tinhthanh = $("#tinhthanh").val();

        // Tổng hợp thành địa chỉ
        var diachi = sonha + ", " + phuongxa + ", " + quanhuyen + ", " + tinhthanh;
        console.log(diachi);
        // Đặt giá trị của asp-for="Diachi" thông qua thẻ input ẩn
        $("#Diachi").val(diachi);
    });
    $("#cccd").change(function () {
        var cccdValue = $(this).val();

        // Gửi yêu cầu GET đến controller với dữ liệu cccdValue
        $.get("/CreateCard/ActionName", { cccd: cccdValue }, function (data) {
            // Xử lý dữ liệu trả về (nếu có)
            if (data == true) {
                alert("khách hàng tồn tại");
                document.getElementById('cccd').value = "";
            }
            
        });
    });
});

