
document.addEventListener('DOMContentLoaded', function () {
    //run code funtion
    inputtimes();
    inputanimate();
    getTodayPlusFiveYears();
    getNameCus();
    NgayHetHan();
    //list funtion
    function inputtimes() {
        var inputNgayThangNam = document.getElementById("ngaymothe");
        var today = new Date();
        var day = today.getDate();
        var month = today.getMonth() + 1;
        var year = today.getFullYear();
        var ngayThangNam = day+"/"+ month + "/" + year;
        inputNgayThangNam.value = ngayThangNam;
    }

    //
    function inputanimate() {
        document.querySelector('.card-number-input').oninput = () => {
            document.querySelector('.card-number-box').innerText = document.querySelector('.card-number-input').value;
        }

        document.querySelector('.card-holder-input').oninput = () => {
            document.querySelector('.card-holder-name').innerText = document.querySelector('.card-holder-input').value;
        }

        document.querySelector('.month-input').oninput = () => {
            document.querySelector('.exp-month').innerText = document.querySelector('.month-input').value;
        }

        document.querySelector('.year-input').oninput = () => {
            document.querySelector('.exp-year').innerText = document.querySelector('.year-input').value;
        }

        document.querySelector('.year-input').onmouseenter = () => {
            document.querySelector('.front').style.transform = 'perspective(1000px) rotateY(-180deg)';
            document.querySelector('.back').style.transform = 'perspective(1000px) rotateY(0deg)';
        }

        document.querySelector('.year-input').onmouseleave = () => {
            document.querySelector('.front').style.transform = 'perspective(1000px) rotateY(0deg)';
            document.querySelector('.back').style.transform = 'perspective(1000px) rotateY(180deg)';
        }

        //document.querySelector('.cvv-input').oninput = () => {
        //    document.querySelector('.cvv-box').innerText = document.querySelector('.cvv-input').value;
        }
    

    //
    function getTodayPlusFiveYears() {
        // Lấy ngày hiện tại
        var today = new Date();

        // Thêm 5 năm vào ngày hiện tại
        today.setFullYear(today.getFullYear() + 5);
        // Lấy ngày, tháng và năm sau khi thêm 5 năm
        var month = today.getMonth() + 1; // Lưu ý: Tháng bắt đầu từ 0
        var year = today.getFullYear();
        var day = today.getDate();
        // Tạo chuỗi ngày tháng năm
        var ngayThangNam =  month + "/" + year;

        document.querySelector('.exp-month').innerText = 
          document.querySelector('.month-input').value = month;
        document.querySelector('.exp-year').innerText =
        document.querySelector('.year-input').value = year;
        
    }
    function getNameCus() {
        $(document).ready(function () {
            $("#makhachhang").change(function () {
                var idcusValue = $(this).val();

               
                $.get("/CreditCard/GetNameCus", { cccd: idcusValue }, function (data) {
                    console.log(data);

                    if (data.tenkh == null) {
                        alert(" khách hàng không tồn tại");
                        document.querySelector('.card-holder-name').innerText =
                            document.querySelector('.tenkhachhang').value = "";
                    } else {
                        
                        document.querySelector('.tenkhachhang').value = document.querySelector('.card-name-customer').innerText = data.tenkh;                      
                        document.querySelector('.card-number-input').value = document.querySelector('.card-number-box').innerText = data.stk;
                        document.querySelector('.card-holder-input').value = document.querySelector('.card-holder-name').innerText = data.sotaikhoan;
                        $('#MaPin').val(data.pin);
                    }
                });
            });
        });
    }
    function NgayHetHan() {
        // Lấy ngày hiện tại
        var today = new Date();
        // Thêm 5 năm vào ngày hiện tại
        today.setFullYear(today.getFullYear() + 5)
        var day = today.getDate();
        // Tạo chuỗi ngày tháng năm
        var NgayHetHan = day +"/" + $('#thanghethan').val() + "/" + $('#namhethan').val();
        $('#ngayhethan').val(NgayHetHan);
    }

   

})