main();

function main() {
    customerStatistics();
    cardTypeStatistics();
}

// Thống kê khách hàng
function customerStatistics() {
    const ctx = document.getElementById('traffic-chart');
    new Chart(ctx, {
        type: "bar",
        data: {
            labels: ["Tháng 1", "Tháng 2", "Tháng 3", "Tháng 4", "Tháng 5", "Tháng 6", "Tháng 8", "Tháng 9", "Tháng 10", "Tháng 11", "Tháng 12"],
            datasets: [
                {
                    label: "Số lượng khách hàng",
                    data: [12, 19, 3, 5, 2, 3, 5, 12, 1, 20, 5, 8],
                    borderWidth: 1,
                },
            ],
        },
        options: {
            scales: {
                y: {
                    beginAtZero: true,
                },
            },
        },
    });
}

// Thống kê loại tài thẻ
function cardTypeStatistics() {
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
