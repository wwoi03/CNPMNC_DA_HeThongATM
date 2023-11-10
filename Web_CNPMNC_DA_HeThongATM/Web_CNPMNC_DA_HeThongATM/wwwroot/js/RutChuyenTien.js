document.addEventListener('DOMContentLoaded', function () {

    // form edit lãi suất
    // JavaScript to handle the "Edit" button click and populate the form
    $(".edit-button").on("click", function () {
        var row = $(this).closest("tr");
        var kyHan = row.find("td:eq(1)").text();
        var tiLe = row.find("td:eq(2)").text();

        $("#editKyHan").val(kyHan);
        $("#editTiLe").val(tiLe);
    });

    // JavaScript to handle the "Save" button click
    $("#saveButton").on("click", function () {
        var newKyHan = $("#editKyHan").val();
        var newTiLe = $("#editTiLe").val();

        // Create a JSON object with the updated data
        var updatedData = {
            KyHan: newKyHan,
            TiLe: newTiLe
        };

        // Send the JSON data to the server for updating

        // Close the modal
        $("#editModal").modal("hide");
    });

    //form xóa lãi suất
    // JavaScript to handle the "Delete" button click
    var interestRateToDelete = null; // Store the selected interest rate to delete

    $(".delete-button").on("click", function () {
        var row = $(this).closest("tr");
        var kyHan = row.find("td:eq(1)").text();
        var tiLe = row.find("td:eq(2)").text();

        interestRateToDelete = {
            KyHan: kyHan,
            TiLe: tiLe
        };
    });

    // JavaScript to handle the "Delete" button in the modal
    $("#confirmDeleteButton").on("click", function () {
        // Send a request to the server to delete the interest rate

        // After a successful deletion, remove the corresponding row from the table
        interestRateToDelete = null; // Clear the selected interest rate
        $("#deleteModal").modal("hide");
    });





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

    GettrangthaiTK();

    function GettrangthaiTK() {
        $(document).ready(function () {

            $("#TinhTrangTaiKhoan").change(function () {

                let stk = $(this).val();
                console.log(stk);

                $.ajax({
                    url: '/AccountStatus/TrangThaiTk',
                    type: 'get',
                    data: { TinhTrangTaiKhoan: tttk },
                    success: function (data) {
                        console.log(data);
                        document.getElementById('tentinhtrangTK').value = data;
                    }
                })
            });
        });
    }
})