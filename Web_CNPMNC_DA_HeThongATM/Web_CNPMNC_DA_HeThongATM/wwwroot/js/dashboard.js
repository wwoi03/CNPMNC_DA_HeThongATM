main();

function main() {
    customerStatistics();
    cardTypeStatistics();
}

// Thống kê khách hàng
function customerStatistics() {
    // Lấy ngày hiện tại
    var today = new Date();
    var date = today.getFullYear();

    // init data
    const data = {
        labels: ["Tháng 1", "Tháng 2", "Tháng 3", "Tháng 4", "Tháng 5", "Tháng 6", "Tháng 8", "Tháng 9", "Tháng 10", "Tháng 11", "Tháng 12"],
        datasets: [
            {
                label: "Số lượng khách hàng năm " + date,
                data: [12, 19, 3, 5, 2, 3, 5, 12, 1, 20, 5, 8],
            },
        ],
    }

    // render
    const ctx = document.getElementById('traffic-chart');
    new Chart(ctx, {
        type: "bar",
        data,
        options: {
            scales: {
                y: {
                    beginAtZero: true,
                },
            },
        },
    });
}

// Thống kê loại thẻ
function cardTypeStatistics() {
    // init data
    const data = {
        labels: ['Thẻ ATM', 'Thẻ VISA'],
        datasets: [
            {
                label: 'Số lượng thẻ',
                data: [14, 34],
                fill: true,
                backgroundColor: ['#00C294', '#FF957D'],
            }
        ]
    };

    // render
    const doughnutCardType = document.getElementById('card-type');
    new Chart(doughnutCardType, {
        type: 'doughnut',
        data,
        options: {
            responsive: true,
            plugins: {
                legend: {
                    position: 'top',
                },
                title: {
                    display: true,
                    text: 'Loại thẻ'
                }
            }
        }
    });
}
