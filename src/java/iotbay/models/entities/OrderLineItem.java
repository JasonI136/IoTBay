package iotbay.models.entities;

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

    private int orderId;
    private int productId;
    private int quantity;

    public OrderLineItem() {};

    public OrderLineItem(ResultSet rs) throws Exception {
        this.orderId = rs.getInt("order_id");
        this.productId = rs.getInt("product_id");
        this.quantity = rs.getInt("quantity");
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
