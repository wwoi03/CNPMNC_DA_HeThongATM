document.addEventListener('DOMContentLoaded', function () { 
    $(document).ready(function () {
        $("#InputTaiKhoanNguon").change(function () {
            var TaiKhoanNguon = $(this).val();
            $.ajax({
                url: "/SaveMoney/getInfomationCus",
                type: "GET",
                data: { sotaikhoannguon: TaiKhoanNguon },
                success: function (data) {
                    if (data == null) {
                        alert("Không tồn tại thông tin cho tài khoản nguồn này.");
                        document.getElementById('InputTaiKhoanNguon').value = "";
                    } else {
                        console.log(data);
                        document.getElementById('tenkhachhang').innerHTML = data.tenTK;
                        document.getElementById('sotaikhoan').innerHTML = data.soTaiKhoan;
                        document.getElementById('sodu').innerHTML = data.soDu + "&nbsp;VND";
                        $("#datataikhoan").val(JSON.stringify(data));
                    }
                    
                }
            });


        });
    });




  
});
