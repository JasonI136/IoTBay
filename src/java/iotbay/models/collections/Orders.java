package iotbay.models.collections;

import iotbay.database.DatabaseManager;
import iotbay.models.entities.Order;
import iotbay.models.enums.OrderStatus;

import java.io.Serializable;
import java.sql.*;

public class Orders {

//    if (!this.tableExists("CUSTOMER_ORDER")) {
//            String createTableQuery =
//                    "CREATE TABLE CUSTOMER_ORDER ("
//                    + "id                               INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
//                    + "user_id                          INT,"
//                    + "order_date                       DATE,"
//                    + "order_status                     VARCHAR(256),"
//                    + "PRIMARY KEY (id),"
//                    + "CONSTRAINT customer_order_user_id_ref FOREIGN KEY (user_id) REFERENCES USER_ACCOUNT(id)"
//                    + ")";
//
//            Statement stmt = this.conn.createStatement();
//            stmt.execute(createTableQuery);
//            conn.commit();
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

    private int id;

    private int userId;

    private Date orderDate;

    private String orderStatus;

    public Orders(DatabaseManager db) {
        this.db = db;
    }

    public Order addOrder(int userId, Timestamp orderDate, String orderStatus) throws Exception {
        Order order = new Order();
        order.setUserId(userId);
        order.setOrderDate(orderDate);
        order.setOrderStatus(OrderStatus.valueOf(orderStatus));

        try (PreparedStatement pstmt = db.getDbConnection().prepareStatement(
                "INSERT INTO CUSTOMER_ORDER (user_id, order_date, order_status) VALUES (?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, order.getUserId());
            pstmt.setTimestamp(2, order.getOrderDate());
            pstmt.setString(3, order.getOrderStatus().toString());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows == 0) {
                throw new Exception("Creating order failed, no rows affected.");
            }

            // Get the ID of the new order
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    order.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating order failed, no ID obtained.");
                }
            }
        }

        return order;
    }

    public void updateOrder(int id, int userId, Timestamp orderDate, String orderStatus) throws Exception {
        try (PreparedStatement pstmt = db.getDbConnection().prepareStatement(
                "UPDATE CUSTOMER_ORDER SET user_id = ?, order_date = ?, order_status = ? WHERE id = ?")) {

            pstmt.setInt(1, userId);
            pstmt.setTimestamp(2, orderDate);
            pstmt.setString(3, orderStatus);

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows == 0) {
                throw new Exception("Updating order failed, no rows affected.");
            }
        }
    }

    public Order getOrder(int id) throws Exception {
        try (PreparedStatement pstmt = db.prepareStatement(
                "SELECT * FROM CUSTOMER_ORDER WHERE id = ?",
                id
        )) {
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Order(rs);
            } else {
                throw new Exception("Getting order failed, no rows affected.");
            }
        }
    }

    public void deleteOrder(int id) throws Exception {
        try (PreparedStatement pstmt = db.prepareStatement(
                "DELETE FROM CUSTOMER_ORDER WHERE id = ?",
                id
        )) {
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows == 0) {
                throw new Exception("Deleting order failed, no rows affected.");
            }
        }
    }

}
