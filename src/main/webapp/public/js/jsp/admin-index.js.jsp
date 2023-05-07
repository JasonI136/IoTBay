<%--
  Created by IntelliJ IDEA.
  User: cmesina
  Date: 7/5/2023
  Time: 2:46 pm
  To change this template use File | Settings | File Templates.
--%>
    <%@ page contentType="text/javascript"%>
    fetch("${pageContext.request.contextPath}/admin/users/metrics/regPerMonth")
        .then(response => response.json())
        .then(json => {
            var labels = [];
            var data = [];

// Calculate the range of months to include
            var today = new Date();
            var startMonth = new Date(today.setMonth(today.getMonth() - 5)).getMonth();
            var startYear = new Date(today.setMonth(startMonth)).getFullYear();
            var endMonth = new Date().getMonth();
            var endYear = new Date().getFullYear();

            for (const [key, value] of Object.entries(json.data)) {
                var split = key.split("-");
                var year = parseInt(split[0]);
                var month = parseInt(split[1]) - 1; // Subtract 1 to convert to 0-based index

                // Check if the month is within the range of months to include
                if (year > startYear || (year === startYear && month >= startMonth)) {
                    if (year < endYear || (year === endYear && month <= endMonth)) {
                        labels.push(split[1] + "/" + split[0]);
                        data.push(value);
                    }
                }
            }

            // Create chart for customer growth
            var ctx1 = document.getElementById('customerGrowthChart').getContext('2d');
            var customerGrowthChart = new Chart(ctx1, {
                type: 'line',
                data: {
                    labels: labels,
                    datasets: [{
                        label: 'Registrations',
                        data: data,
                        backgroundColor: 'rgba(255, 159, 64, 0.2)',
                        borderColor: 'rgba(255, 159, 64, 1)',
                        borderWidth: 3,
                        lineTension: 0.3,
                        shadowOffsetX: 0,
                        shadowOffsetY: 3,
                        shadowBlur: 5,
                        shadowColor: 'rgba(0, 0, 0, 0.3)'
                    }]
                },
                options: {
                    scales: {
                        y: {
                            ticks: {
                                beginAtZero: true,
                                precision: 0
                            }
                        }
                    }
                }
            });
        })
        .catch(error => console.error(error));

    fetch("${pageContext.request.contextPath}/admin/orders/metrics/ordersPerMonth")
        .then(response => response.json())
        .then(json => {
            var labels = [];
            var data = [];

// Calculate the range of months to include
            var today = new Date();
            var startMonth = new Date(today.setMonth(today.getMonth() - 5)).getMonth();
            var startYear = new Date(today.setMonth(startMonth)).getFullYear();
            var endMonth = new Date().getMonth();
            var endYear = new Date().getFullYear();

            for (const [key, value] of Object.entries(json.data)) {
                var split = key.split("-");
                var year = parseInt(split[0]);
                var month = parseInt(split[1]) - 1; // Subtract 1 to convert to 0-based index

                // Check if the month is within the range of months to include
                if (year > startYear || (year === startYear && month >= startMonth)) {
                    if (year < endYear || (year === endYear && month <= endMonth)) {
                        labels.push(split[1] + "/" + split[0]);
                        data.push(value);
                    }
                }
            }

            // Total Order Chart
            var ctx = document.getElementById('orderChart').getContext('2d');
            var orderChart = new Chart(ctx, {
                type: 'line',
                data: {
                    labels: labels,
                    datasets: [{
                        label: 'Sales',
                        data: data,
                        backgroundColor: 'rgba(108, 122, 224, 0.2)',
                        borderColor: 'rgba(108, 122, 224, 1)',
                        borderWidth: 3, // Set line width to 3
                        lineTension: 0.3, // Set line tension to make lines curved
                        shadowOffsetX: 0, // Set shadow offset
                        shadowOffsetY: 3,
                        shadowBlur: 5,
                        shadowColor: 'rgba(0, 0, 0, 0.3)' // Set shadow color
                    }]
                },
                options: {
                    scales: {
                        y: {
                            ticks: {
                                beginAtZero: true,
                                precision: 0
                            }
                        }
                    }
                }
            });
        })
        .catch(error => console.error(error));
