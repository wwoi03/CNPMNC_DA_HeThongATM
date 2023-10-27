
document.addEventListener('DOMContentLoaded', function () {


    // Bắt sự kiện khi người dùng thực hiện tìm kiếm
    $('#search-button').on('click', function () {
        document.getElementById('Card-list').style.display = 'none';
        let values = document.getElementById('search-input').value;
        console.log(values);
        if (values =="") {
            location.reload();
        } else {
            $.ajax({
                url: '/CreditCard/SearchCard',
                type: 'GET',
                data: { searchValue: values },
                success: function (data) {
                    console.log(data);
                    $('#CardListContainer').html(data);
                }
            });
        }
    });
});

