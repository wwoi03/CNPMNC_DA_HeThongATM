document.addEventListener('DOMContentLoaded', function () {
  






    $('#UndoStaff').on('click', function () {
        $.ajax({
            url: '/Staff/Undo',
            type: 'post',
            success: function (data) {
                console.log(data);
                // Xử lý dữ liệu trả về từ phương thức Undo ở đây nếu cần
            },
            error: function (xhr, status, error) {
                console.log(xhr.responseText);
                // Xử lý lỗi nếu có
            }
        });
    });



})