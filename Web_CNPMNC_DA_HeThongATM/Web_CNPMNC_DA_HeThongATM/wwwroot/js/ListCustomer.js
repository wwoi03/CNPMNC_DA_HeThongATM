
document.addEventListener('DOMContentLoaded', function () {


    // Bắt sự kiện khi người dùng thực hiện tìm kiếm
    $('#search-button').on('click', function () {
        document.getElementById('CustomerList').style.display = 'none';
        let values = document.getElementById('search-input').value;
        console.log(values);
        if (values == "") {
            location.reload();
        } else {
            $.ajax({
                url: '/Customer/SearchCustomer',
                type: 'GET',
                data: { searchCustomer: values },
                success: function (data) {
                    console.log(data);
                    $('#SearchCustomerID').html(data);
                }
            });
        }
    });
});

