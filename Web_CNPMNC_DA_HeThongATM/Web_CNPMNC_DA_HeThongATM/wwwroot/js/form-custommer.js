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
    //kiểm tra cccd
    $("#cccd").change(function () {
        var cccdValue = $(this).val();

        // Gửi yêu cầu GET đến controller với dữ liệu cccdValue
        $.get("/Customer/ActionName", { cccd: cccdValue }, function (data) {
            // Xử lý dữ liệu trả về (nếu có)
            if (data == true) {
                alert("khách hàng tồn tại");
                document.getElementById('cccd').value = "";
            }
            
        });
    });
    
    
    






});
document.addEventListener("DOMContentLoaded",function(){
    InsertDay();
    //nhận ngày tạo khách hàng vào form
    function InsertDay(){
        var today = new Date();
        var day = today.getDate();
        var month = today.getMonth() + 1;
        var year = today.getFullYear();
        var ngayThangNam =day+"/"+ month + "/" + year;
        console.log(ngayThangNam);
        $('#NgayTao').val(ngayThangNam);

    }
})
