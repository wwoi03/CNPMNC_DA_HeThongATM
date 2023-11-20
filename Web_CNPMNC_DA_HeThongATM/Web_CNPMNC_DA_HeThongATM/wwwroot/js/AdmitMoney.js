
document.addEventListener('DOMContentLoaded', function () {
    $(document).ready(function () {
        $("#InputTaiKhoanNguon").change(function () {
            var TaiKhoanNguon = $(this).val() ; // Assuming it's a string
            $.ajax({
                url: "/SaveMoney/GetTKNorTKTK",
                type: "GET",
                data: { stk: TaiKhoanNguon },
                success: function (data) {
                    if (data === null || data === undefined) {
                        alert('tài khoản không tồn tại');
                        $('#InputTaiKhoanNguon').val(""); // Use jQuery to set value
                    } else {
                      
                        document.getElementById('tenkhachhang').innerHTML = data.tenTK;
                        document.getElementById('sotaikhoan').innerHTML = data.guiTietKiem.taiKhoanTietKiem;
                        document.getElementById('sodu').innerHTML = data.guiTietKiem.tienGui + " VND";
                        document.getElementById('NgayGui').innerHTML = data.guiTietKiem.ngayGui;
                        document.getElementById('kyhan').innerHTML = data.laiSuat.kyHan;
                        document.getElementById('tile').innerHTML = data.laiSuat.tiLe;
                        $("#datataikhoan").val(JSON.stringify(data));
                    }
                }, Error: function () {
                    document.getElementById('quavip').style.display = "block";
                    $('#InputTaiKhoanNguon').val(""); // Use jQuery to set value
                }

            });
        });
    });
});
