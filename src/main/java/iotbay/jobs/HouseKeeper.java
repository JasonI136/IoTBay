package iotbay.jobs;

import com.stripe.model.PaymentIntent;
import com.stripe.model.PaymentIntentSearchResult;
import com.stripe.param.PaymentIntentSearchParams;
import iotbay.models.collections.*;
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

    private static final Logger logger = LogManager.getLogger(HouseKeeper.class);

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDataMap dataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        Orders orders = (Orders) dataMap.get("orders");
        Payments payments = (Payments) dataMap.get("payments");
        PaymentMethods paymentMethods = (PaymentMethods) dataMap.get("paymentMethods");
        OrderLineItems orderLineItems = (OrderLineItems) dataMap.get("orderLineItems");
        Invoices invoices = (Invoices) dataMap.get("invoices");

        try {
            checkStripePayments(orders, payments, paymentMethods);
        } catch (Exception e) {
            logger.error("Error checking stripe payments", e);
        }

        try {
            deleteOldPendingOrders(orders, orderLineItems, invoices);
        } catch (Exception e) {
            logger.error("Error deleting old pending orders", e);
        }
    }

    private void checkStripePayments(Orders orders, Payments payments, PaymentMethods paymentMethods) throws Exception {
        // get orders with OrderStatus enum of PENDING
        List<Order> pendingOrders = orders.getOrders(OrderStatus.PENDING);

        for (Order order : pendingOrders) {
            PaymentIntentSearchParams params = PaymentIntentSearchParams
                    .builder()
                    .setQuery(String.format("status:'succeeded' AND metadata['order_id']:'%s'", order.getId()))
                    .build();

            PaymentIntentSearchResult result = PaymentIntent.search(params);

            if (result.getData().size() == 1) {
                // get the paymentintent metadata
                PaymentIntent paymentIntent = result.getData().get(0);
                logger.info("Order {} has been paid for. Updating status to processing.", order.getId());
                payments.addPayment(
                        Integer.parseInt(paymentIntent.getMetadata().get("invoice_id")),
                        new Timestamp(paymentIntent.getCreated()),
                        paymentMethods.getPaymentMethod(paymentIntent.getPaymentMethod()).getId(),
                        paymentIntent.getAmount());
                order.setOrderStatus(OrderStatus.PROCESSING);
                order.update();
            }
        }

    }

    /**
     * Delete pending orders that are older than 30 minutes.
     */
    private void deleteOldPendingOrders(Orders orders, OrderLineItems orderLineItems, Invoices invoices) throws Exception {

        // we need to delete the order line items and invoices first as there is a foreign key constraint.
        try (Connection conn = orders.getDb().getDbConnection()) {
            String sql = "SELECT * FROM CUSTOMER_ORDER WHERE order_status = 'PENDING' AND {fn TIMESTAMPDIFF(SQL_TSI_MINUTE, order_date, CURRENT_TIMESTAMP)} > 5";

            try (ResultSet rs = conn.createStatement().executeQuery(sql)) {
                List<Order> oldPendingOrders = new ArrayList<>();
                while (rs.next()) {
                    oldPendingOrders.add(orders.getOrder(rs.getInt("id")));
                }

                for (Order order : oldPendingOrders) {
                    try {
                        orderLineItems.deleteOrderLineItems(order.getId());
                    } catch (Exception e) {
                        logger.error("Error deleting order line items for order {}", order.getId(), e);
                    }
                    invoices.deleteInvoiceByOrderId(order.getId());
                }
            }
        }

        try (Connection conn = orders.getDb().getDbConnection()) {
            String sql = "DELETE FROM CUSTOMER_ORDER WHERE order_status = 'PENDING' AND {fn TIMESTAMPDIFF(SQL_TSI_MINUTE, order_date, CURRENT_TIMESTAMP)} > 5";

            int affectedRows = conn.createStatement().executeUpdate(sql);

            if (affectedRows > 0) {
                logger.info("Deleted {} old pending orders", affectedRows);
            }
        }


    }

}
