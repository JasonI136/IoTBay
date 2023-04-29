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
            {title: "Order Date", field: "orderDate", sorter: "string", width: 200},
            {title: "Order Status", field: "orderStatus", sorter: "string", width: 200},

        ]
    })