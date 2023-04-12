package iotbay.models.entities;

import iotbay.database.DatabaseManager;
import iotbay.models.enums.OrderStatus;

import javax.xml.crypto.Data;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.Statement;

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

    private final transient DatabaseManager db;

    private Order order;
    private Product product;
    private int quantity;

    private double price;

    public OrderLineItem(DatabaseManager db) {
        this.db = db;
    }

    public OrderLineItem(ResultSet rs, DatabaseManager db) throws Exception {
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


    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }


    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
