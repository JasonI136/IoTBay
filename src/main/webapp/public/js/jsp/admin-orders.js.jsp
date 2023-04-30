<%--
  Created by IntelliJ IDEA.
  User: cmesina
  Date: 29/4/2023
  Time: 10:29 pm
  To change this template use File | Settings | File Templates.
--%>
    <%@ page contentType="text/javascript" %>

    var table = new Tabulator('#order-table', {
        ajaxURL: "${pageContext.request.contextPath}/admin/orders/get",
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
            {title: "User ID", field: "userId", sorter: "number", width: 100},
            {title: "Order Date", field: "orderDateISO", width: 200, formatter:"datetime", formatterParams:{
                    inputFormat:"iso",
                    outputFormat:"DD t",
                    timezone:"Australia/Sydney",
                    invalidPlaceholder:"(invalid date)"
                }},
            {title: "Order Status", field: "orderStatus", sorter: "string", width: 200, editor: "list", editorParams: {
                values: ["PENDING", "PROCESSING", "SHIPPED", "DELIVERED", "CANCELLED"]
                }}

        ]
    })

    table.on("cellEdited", function(cell) {
        console.log(cell);
        // check if the value changed was the order status
        if (cell.getColumn().getField() === "orderStatus") {
            var row = cell.getRow().getData();
            var orderStatus = cell.getValue();

            fetch("${pageContext.request.contextPath}/admin/orders/update/" + row.id, {
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
                        alert("Error updating order status")
                    }
                })

        }
    })