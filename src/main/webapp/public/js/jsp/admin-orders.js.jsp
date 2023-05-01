<%--
  Created by IntelliJ IDEA.
  User: cmesina
  Date: 29/4/2023
  Time: 10:29 pm
  To change this template use File | Settings | File Templates.
--%>
    <%@ page contentType="text/javascript" %>

    var customerNameMutator = function (value, data, type, params, component) {
        return data.user.firstName + " " + data.user.lastName;
    }

    var table = new Tabulator('#order-table', {
        ajaxURL: "${pageContext.request.contextPath}/admin/orders",
        ajaxConfig: {
            method: "GET",
            headers: {
                "Content-Type": "application/json",
            }
        },
        ajaxResponse: function (url, params, response) {
            return {
                "data": response.data.items,
                "last_page": response.data.totalPages
            }
        },
        dataSendParams: {
            "size": "limit"
        },
        layout: "fitColumns",
        paginationMode: "remote",
        paginationSize: 10,
        pagination: true,
        selectable: false,
        columns: [
            {title: "ID", field: "id", sorter: "number", width: 100},
            {title: "Customer ID", field: "user.id", sorter: "number", width: 100},
            {title: "Customer", field: "full_name", mutator:customerNameMutator, width: 150},
            {
                title: "Order Date", field: "orderDateISO", width: 200, formatter: "datetime", formatterParams: {
                    inputFormat: "iso",
                    outputFormat: "DD t",
                    timezone: "Australia/Sydney",
                    invalidPlaceholder: "(invalid date)"
                }
            },
            {
                title: "Order Status",
                field: "orderStatus",
                sorter: "string",
                width: 200,
                editor: "list",
                editorParams: {
                    values: ["PENDING", "PROCESSING", "SHIPPED", "DELIVERED", "CANCELLED"]
                }
            }

        ]
    })

    table.on("cellEdited", function (cell) {
        // check if the value changed was the order status
        if (cell.getColumn().getField() === "orderStatus") {
            var row = cell.getRow().getData();
            var orderStatus = cell.getValue();

            fetch("${pageContext.request.contextPath}/admin/orders/" + row.id, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({
                    orderStatus: orderStatus
                })
            })
                .then(res => res.json())
                .then(json => {
                    if (json.statusCode !== 200) {
                        // revert the change
                        cell.restoreOldValue();
                        Swal.fire({
                            title: "Error",
                            icon: 'error',
                            text: json.data,
                            showCancelButton: false,
                            confirmButtonColor: '#3085d6',
                            confirmButtonText: 'OK'
                        });
                    }
                }).catch((err) => {
                cell.restoreOldValue();
                Swal.fire({
                    title: "Error",
                    icon: 'error',
                    text: err,
                    showCancelButton: false,
                    confirmButtonColor: '#3085d6',
                    confirmButtonText: 'OK'
                });
            })

        }
    })