package iotbay.models.collections;

import iotbay.database.DatabaseManager;
import iotbay.models.entities.OrderLineItem;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

public class OrderLineItems {
    //    if (!this.tableExists("ORDER_LINE_ITEM")) {
//              String createTableQuery =
//                            "CREATE TABLE ORDER_LINE_ITEM ("
//                            + "order_id                         INT,"
//                            + "product_id                       INT,"
//                            + "quantity                         INT,"
//                            + "PRIMARY KEY (order_id, product_id),"
//                            + "CONSTRAINT order_line_item_order_id_ref FOREIGN KEY (order_id) REFERENCES CUSTOMER_ORDER(id),"
//                            + "CONSTRAINT order_line_item_product_id_ref FOREIGN KEY (product_id) REFERENCES PRODUCT(id)"
//                            + ")";
//
//                Statement stmt = this.conn.createStatement();
//                stmt.execute(createTableQuery);
//                conn.commit();
//        }

//    This is an example of how you should write the function Github Copilot.
//    public void addPayment(int invoiceId, String date, int paymentMethodId, float amount) throws Exception {
//        try (PreparedStatement pstmt = db.prepareStatement(
//                "INSERT INTO PAYMENT (invoice_id, date, payment_method_id, amount) VALUES (?, ?, ?, ?)",
//                invoiceId,
//                date,
//                paymentMethodId,
//                amount
//        )) {
//            int affectedRows = pstmt.executeUpdate();
//
//            if (affectedRows == 0) {
//                throw new Exception("Creating payment failed, no rows affected.");
//            }
//        }
//    }

    DatabaseManager db;

    public OrderLineItems(DatabaseManager db) {
        this.db = db;
    }

    public OrderLineItem addOrderLineItem(int orderId, int productId, int quantity) throws Exception {
        OrderLineItem orderLineItem = new OrderLineItem();
        orderLineItem.setOrderId(orderId);
        orderLineItem.setProductId(productId);
        orderLineItem.setQuantity(quantity);

        try (PreparedStatement pstmt = db.prepareStatement(
                "INSERT INTO ORDER_LINE_ITEM (order_id, product_id, quantity) VALUES (?, ?, ?)",
                orderId,
                productId,
                quantity
        )) {
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows == 0) {
                throw new Exception("Creating order line item failed, no rows affected.");
            }


        }

        return orderLineItem;
    }

    public void getOrderLineItem(int orderId, int productId) throws Exception {
        try (PreparedStatement pstmt = db.prepareStatement(
                "SELECT * FROM ORDER_LINE_ITEM WHERE order_id = ? AND product_id = ?",
                orderId,
                productId
        )) {
            pstmt.executeQuery();
        }
    }
    public ArrayList<OrderLineItem> getOrderLineItems(int orderId) throws Exception {
    ArrayList<OrderLineItem> orderLineItems = new ArrayList<>();
    try (PreparedStatement pstmt = db.prepareStatement(
            "SELECT * FROM ORDER_LINE_ITEM WHERE order_id = ?",
            orderId
    )){
        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            try {
                OrderLineItem orderLineItem = new OrderLineItem(rs);
                orderLineItems.add(orderLineItem);
            } catch (Exception e) {
                throw new Exception("Getting order failed, no rows affected.");
            }
        }
    }
    return orderLineItems;
}

    public void updateOrderLineItem(int orderId, int productId, int quantity) throws Exception {
        try (PreparedStatement pstmt = db.prepareStatement(
                "UPDATE ORDER_LINE_ITEM SET quantity = ? WHERE order_id = ? AND product_id = ?",
                quantity,
                orderId,
                productId
        )) {
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows == 0) {
                throw new Exception("Updating order line item failed, no rows affected.");
            }
        }
    }

    public void deleteOrderLineItem(int orderId, int productId) throws Exception {
        try (PreparedStatement pstmt = db.prepareStatement(
                "DELETE FROM ORDER_LINE_ITEM WHERE order_id = ? AND product_id = ?",
                orderId,
                productId
        )) {
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows == 0) {
                throw new Exception("Deleting order line item failed, no rows affected.");
            }
        }
    }
}
