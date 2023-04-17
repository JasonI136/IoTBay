package iotbay.models;

import iotbay.database.DatabaseManager;
import iotbay.enums.OrderStatus;
import lombok.Data;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

@Data
public class OrderLineItem implements Serializable {
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
//                
//        }

    /**
     * The database manager
     */
    private final transient DatabaseManager db;

    /**
     * An instance of the order,
     */
    private Order order;

    /**
     * An instance of the product
     */
    private Product product;

    /**
     * The quantity of the product
     */
    private int quantity;

    /**
     * The price of the product
     */
    private double price;

    /**
     * The constructor for the OrderLineItem class.
     *
     * @param db The database manager.
     */
    public OrderLineItem(DatabaseManager db) {
        this.db = db;
    }

    /**
     * The constructor for the OrderLineItem class.
     * @param rs The result set.
     * @param db The database manager.
     * @throws SQLException If there is an error with the SQL query.
     */
    public OrderLineItem(ResultSet rs, DatabaseManager db) throws SQLException {
        this.db = db;
        Order order = new Order(db);
        order.setId(rs.getInt("order_id"));
        order.setOrderDate(rs.getTimestamp("order_date"));
        order.setOrderStatus(OrderStatus.valueOf(rs.getString("order_status")));
        order.setUserId(rs.getInt("user_id"));

        Product product = new Product();
        product.setId(rs.getInt("product_id"));
        product.setName(rs.getString("product_name"));
        product.setImageURL(rs.getString("product_image"));
        product.setPrice(rs.getDouble("product_price"));

        this.order = order;
        this.product = product;
        this.price = rs.getDouble("price");
        this.quantity = rs.getInt("quantity");
    }


}
