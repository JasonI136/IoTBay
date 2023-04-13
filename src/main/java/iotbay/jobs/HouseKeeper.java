package iotbay.jobs;

import com.stripe.model.PaymentIntent;
import iotbay.database.DatabaseManager;
import iotbay.models.entities.Order;
import iotbay.models.enums.OrderStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class HouseKeeper implements Job {
    
    DatabaseManager db;

    private static final Logger logger = LogManager.getLogger(HouseKeeper.class);

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDataMap dataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        this.db = (DatabaseManager) dataMap.get("db");

        try {
            checkStripePayments();
        } catch (Exception e) {
            logger.error("Error checking stripe payments", e);
        }

        try {
            deleteOldPendingOrders();
        } catch (Exception e) {
            logger.error("Error deleting old pending orders", e);
        }
    }

    private void checkStripePayments() throws Exception {
        // get orders with OrderStatus enum of PENDING
        List<Order> pendingOrders = db.getOrders().getOrders(OrderStatus.PENDING);

        for (Order order : pendingOrders) {
            PaymentIntent paymentIntent = PaymentIntent.retrieve(order.getStripePaymentIntentId());

            // check if payment intent exists
            if (paymentIntent == null) {
                logger.error("Payment intent {} does not exist", order.getStripePaymentIntentId());
                order.setOrderStatus(OrderStatus.EXCEPTION);
                order.update();
                continue;
            }

            // check if payment intent is paid
            if (paymentIntent.getStatus().equals("succeeded")) {
                logger.info("Order {} has been paid for. Updating status to processing.", order.getId());
                db.getPayments().addPayment(
                        order.getId(),
                        new Timestamp(paymentIntent.getCreated()),
                        db.getPaymentMethods().getPaymentMethod(paymentIntent.getPaymentMethod()).getId(),
                        paymentIntent.getAmount());
                order.setOrderStatus(OrderStatus.PROCESSING);
                order.update();
            }
        }

    }

    /**
     * Delete pending orders that are older than 5 minutes.
     */
    private void deleteOldPendingOrders() throws Exception {

        // we need to delete the order line items and invoices first as there is a foreign key constraint.
        try (Connection conn = db.getOrders().getDb().getDbConnection()) {
            String sql = "SELECT * FROM CUSTOMER_ORDER WHERE order_status = 'PENDING' AND {fn TIMESTAMPDIFF(SQL_TSI_MINUTE, order_date, CURRENT_TIMESTAMP)} > 5";

            try (ResultSet rs = conn.createStatement().executeQuery(sql)) {
                List<Order> oldPendingOrders = new ArrayList<>();
                while (rs.next()) {
                    oldPendingOrders.add(db.getOrders().getOrder(rs.getInt("id")));
                }

                for (Order order : oldPendingOrders) {
                    try {
                        db.getOrderLineItems().deleteOrderLineItems(order.getId());
                    } catch (Exception e) {
                        logger.error("Error deleting order line items for order {}", order.getId(), e);
                    }
                    db.getInvoices().deleteInvoiceByOrderId(order.getId());
                }
            }
        }

        try (Connection conn = db.getOrders().getDb().getDbConnection()) {
            String sql = "DELETE FROM CUSTOMER_ORDER WHERE order_status = 'PENDING' AND {fn TIMESTAMPDIFF(SQL_TSI_MINUTE, order_date, CURRENT_TIMESTAMP)} > 5";

            int affectedRows = conn.createStatement().executeUpdate(sql);

            if (affectedRows > 0) {
                logger.info("Deleted {} old pending orders", affectedRows);
            }
        }


    }

}
