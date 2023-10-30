document.addEventListener('DOMContentLoaded', function () {
    GetTenTK();

    function GetTenTK() {
        $(document).ready(function () {
           
            $("#SoTaiKhoan").change(function () {

                let stk = $(this).val();
                console.log(stk);

                $.ajax({
                    url: '/TransferMoney/sendAccount',
                    type: 'get',
                    data: { SoTaiKhoan: stk },
                    success: function (data)
                    {
                        console.log(data);
                        document.getElementById('tenTK').value = data;
                    }
                })
            });
        });
    }
})