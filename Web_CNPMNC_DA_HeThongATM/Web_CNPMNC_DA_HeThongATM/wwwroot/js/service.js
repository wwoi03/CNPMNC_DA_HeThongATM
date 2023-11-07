main();

function main() {
    createService();
    updateService();
}

// tạo dịch vụ
function createService() {
    var formAddBtn = document.querySelector('#form-add-btn');

    if (formAddBtn != null) {
        formAddBtn.addEventListener('click', function() {
          document.querySelector('#form-add').submit();
        });
    }
}

// sửa dịch vụ
function updateService() {
    var formEditBtn = document.querySelector('#form-edit-btn');

    if (formEditBtn != null) {
        formEditBtn.addEventListener('click', function() {
          document.querySelector('#form-edit').submit();
        });
    }
}