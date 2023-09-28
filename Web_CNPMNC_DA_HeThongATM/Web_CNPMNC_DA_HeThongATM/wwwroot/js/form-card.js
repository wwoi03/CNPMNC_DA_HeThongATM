
document.addEventListener('DOMContentLoaded', function () {
    //run code funtion
    inputtimes();
    inputanimate();
    getTodayPlusFiveYears();
    getNameCus();
    //list funtion
    function inputtimes() {
        var inputNgayThangNam = document.getElementById("ngaymothe");
        var today = new Date();
        var month = today.getMonth() + 1;
        var year = today.getFullYear();
        var ngayThangNam = month + "/" + year;
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

        document.querySelector('.cvv-input').onmouseenter = () => {
            document.querySelector('.front').style.transform = 'perspective(1000px) rotateY(-180deg)';
            document.querySelector('.back').style.transform = 'perspective(1000px) rotateY(0deg)';
        }

        document.querySelector('.cvv-input').onmouseleave = () => {
            document.querySelector('.front').style.transform = 'perspective(1000px) rotateY(0deg)';
            document.querySelector('.back').style.transform = 'perspective(1000px) rotateY(180deg)';
        }

        document.querySelector('.cvv-input').oninput = () => {
            document.querySelector('.cvv-box').innerText = document.querySelector('.cvv-input').value;
        }
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

               
                $.get("/ReditCard/GetNameCus", { codeToFind: idcusValue }, function (data) {
                    console.log(data);
                    if (data == null) {
                        alert("mã khách hàng không tồn tại");
                        document.querySelector('.card-holder-name').innerText =
                            document.querySelector('.tenkhachhang').value = "";
                    } else {          
                        document.querySelector('.card-holder-name').innerText =
                            document.querySelector('.tenkhachhang').value = data.tenkh;
                        document.querySelector('.sdt').value = data.sdt;
                        document.querySelector('.card-number-input').value = document.querySelector('.card-number-box').innerText = "2134567234567";
                        document.querySelector('.card-holder-input').value = document.querySelector('.card-holder-name').innerText = "2134567234567";
                    }
                });
            });
        });
    }

   

})