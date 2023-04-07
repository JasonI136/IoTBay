package iotbay.models.entities;

import iotbay.models.enums.OrderStatus;

import java.io.Serializable;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Timestamp;

/**
 * The order entity
 */
public class Order implements Serializable {

    /**
     * The order id
     */
    private int id;

    /**
     * The user id
     */
    private int userId;

    /**
     * The order date
     */
    private Timestamp orderDate;

    /**
     * The order status
     */
    private OrderStatus orderStatus;

    public Order() {};

    public Order(ResultSet rs) throws Exception {
        this.id = rs.getInt("id");
        this.userId = rs.getInt("user_id");
        this.orderDate = rs.getTimestamp("order_date");
        this.orderStatus = OrderStatus.valueOf(rs.getString("order_status"));
    }

    /**
     * Get the order id
     * @return the order id
     */
    public int getId() {
        return id;
    }

    /**
     * Set the order id
     * @param id the order id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Get the user id
     * @return the user id
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Set the user id
     * @param userId the user id
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Get the order date
     * @return the order date
     */
    public Timestamp getOrderDate() {
        return orderDate;
    }

    /**
     * Set the order date
     * @param orderDate the order date
     */
    public void setOrderDate(Timestamp orderDate) {
        this.orderDate = orderDate;
    }

    /**
     * Get the order status
     * @return the order status
     */
    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    /**
     * Set the order status
     * @param orderStatus the order status
     */
    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }
}
