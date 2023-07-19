
         /* Mảng các màu sắc cho từng phần tử */

            /* Tạo biểu đồ tròn */
            var ctx = document.getElementById('pieChart').getContext('2d');
            var pieChart = new Chart(ctx, {
                type: 'pie',
                data: {
                    labels: labels, // Tên của từng category
                    datasets: [{
                        data: data, // Tổng expense của từng category
                        backgroundColor: ['#FF6384', '#36A2EB', '#FFCE56', '#4BC0C0', '#9966FF', '#FF9F40', '#00CC99', '#FF66CC'] // Màu sắc cho từng phần tử
                    }]
                },
                options: {
                    responsive: false
                }
            });

            /* Hàm tạo mảng màu sắc ngẫu nhiên và không trùng lặp */

     // Lấy thẻ canvas và context
     var canvas = document.getElementById('expenseChart');
     var ctx = canvas.getContext('2d');

     // Tạo biểu đồ tròn
     var chart = new Chart(ctx, {
         type: 'pie',
         data: {
             labels: ['Expense', 'Remaining'],
             datasets: [{
                 data: [expensePercentage, remainingPercentage],
                 backgroundColor: ['#4BC0C0', '#9966FF']
             }]
         },
         options: {
             title: {
                 display: true,
                 text: 'Expense vs Budget'
             },
             responsive: false, // Đặt kích thước biểu đồ phù hợp với kích thước phần tử chứa
         }
     });


/* Tạo biểu đồ tròn */
var ctx = document.getElementById('pieChartBudget').getContext('2d');
var pieChart = new Chart(ctx, {
    type: 'pie',
    data: {
        labels: labels, // Tên của từng category
        datasets: [{
            data: datas, // Tổng expense của từng category
            backgroundColor: ['#FF6384', '#36A2EB', '#FFCE56', '#4BC0C0', '#9966FF', '#FF9F40', '#00CC99', '#FF66CC']// Màu sắc cho từng phần tử
        }]
    },
    options: {
        responsive: false
    }
});

/* Hàm tạo mảng màu sắc ngẫu nhiên và không trùng lặp */