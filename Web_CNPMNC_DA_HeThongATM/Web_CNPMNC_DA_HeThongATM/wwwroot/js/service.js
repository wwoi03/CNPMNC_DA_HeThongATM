main();

function main() {
    createService();
}

// tạo dịch vụ
function createService() {
    document.querySelector('#form-add-btn').addEventListener('click', function() {
      document.querySelector('#form-add').submit();
    });
}