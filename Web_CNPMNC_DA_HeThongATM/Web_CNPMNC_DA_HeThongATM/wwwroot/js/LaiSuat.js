document.addEventListener('DOMContentLoaded', function () { 
    $(document).ready(function () {
        $("#InputTaiKhoanNguon").change(function () {
            var TaiKhoanNguon = $(this).val();
            $.ajax({
                url: "/SaveMoney/getInfomationCus",
                type: "GET",
                data: { sotaikhoannguon: TaiKhoanNguon },
                success: function (data) {                   
                    document.getElementById('tenkhachhang').innerHTML = data.tenTK;
                    document.getElementById('sotaikhoan').innerHTML = data.soTaiKhoan;
                    document.getElementById('sodu').innerHTML = data.soDu + "&nbsp;VND";
                    $("#datataikhoan").val(JSON.stringify(data));
                }
                
            });

        });
    });
   
    


});
