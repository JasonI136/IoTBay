package iotbay.util;

import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import iotbay.database.DatabaseManager;
import iotbay.database.collections.Orders;
import iotbay.enums.OrderStatus;
import iotbay.exceptions.ProductStockException;
import iotbay.models.*;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CheckoutSession {

    private Invoice invoice;
    private List<OrderLineItem> orderLineItems;
    private Order order;
    private final Cart shoppingCart;
    private final Map<String, Object> stripeParams = new HashMap<>();
    private final Map<String, String> stripeMetaData = new HashMap<>();
    private final Timestamp currentTime = new Timestamp(System.currentTimeMillis());
    private final User user;
    private final DatabaseManager db;

    public CheckoutSession(Cart shoppingCart, User user, DatabaseManager db) {
        this.shoppingCart = shoppingCart;
        this.user = user;
        this.db = db;
    }

    /**
     * Initiates the checkout process.
     *
     * @return The stripe payment intent object.
     */
    public PaymentIntent initiateCheckout() throws SQLException, ProductStockException, StripeException, IOException {
        try {
            makeOrder();
            makeOrderLineItems();
            makeInvoice();
        } catch (SQLException | ProductStockException e) {
            rollback();
            throw e;
        }

        PaymentIntent paymentIntent;
        try {
            this.stripeParams.put("amount", (int) this.shoppingCart.getTotalPrice() * 100);
            this.stripeParams.put("currency", "aud");
            this.stripeParams.put("customer", user.getStripeCustomerId());
            this.stripeMetaData.put("order_id", Integer.toString(this.order.getId()));
            this.stripeMetaData.put("invoice_id", Integer.toString(invoice.getId()));
            this.stripeParams.put("metadata", this.stripeMetaData);

            paymentIntent = PaymentIntent.create(this.stripeParams);
        } catch (StripeException e) {
            rollback();
            throw e;
        }

        try {
            this.order.setStripePaymentIntentId(paymentIntent.getId());
            this.order.update();
        } catch (SQLException e) {
            rollback();
            throw e;
        }

        return paymentIntent;

    }

    /**
     * Undo the checkout process. Removes created orders, invoices and line items from the database.
     */
    public void rollback() throws SQLException {
        // The following code must be executed in reverse order to the checkout process. There are foreign key constraints.

        if (this.invoice != null) {
            this.db.getInvoices().deleteInvoice(this.invoice.getId());
        }

        if (this.orderLineItems != null) {
            if (this.orderLineItems.size() > 0) {
                this.db.getOrderLineItems().deleteOrderLineItems(this.order.getId());
            }
        }

        if (this.order != null) {
            this.db.getOrders().deleteOrder(this.order.getId());
        }

    }

    private void makeOrder() throws SQLException {
        this.order = this.db.getOrders().addOrder(
                user.getId(),
                this.currentTime,
                OrderStatus.PENDING
        );

        this.stripeMetaData.put("order_id", Integer.toString(this.order.getId()));
    }

    private void makeOrderLineItems() throws SQLException, ProductStockException {
        List<CartItem> cartItemsNotInStock = new ArrayList<>();
        for (CartItem cartItem : this.shoppingCart.getCartItems()) {
            try {
                this.db.getOrderLineItems().addOrderLineItem(this.order.getId(), cartItem.getProduct().getId(), cartItem.getCartQuantity(), cartItem.getTotalPrice());
            } catch (ProductStockException e) {
                cartItemsNotInStock.add(cartItem);
            }
        }

        if (cartItemsNotInStock.size() > 0) {
            throw new ProductStockException("Some products are out of stock.", cartItemsNotInStock.stream().map(CartItem::getProduct).toList());
        }
    }

    private void makeInvoice() throws SQLException {
        this.invoice = this.db.getInvoices().addInvoice(this.order.getId(), this.currentTime, (float) this.shoppingCart.getTotalPrice() * 100);
    }


}
