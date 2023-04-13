import iotbay.database.DatabaseManager;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.ejb.DependsOn;
import javax.xml.crypto.Data;
import java.io.IOException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DatabaseTests {

    @Test
    public void testDatabaseManager() throws SQLException, ClassNotFoundException {
        DatabaseManager db = new DatabaseManager(
                "jdbc:derby://localhost:1527/",
                "iotbay",
                "iotbay",
                "iotbaydb"
        );
        assertNotNull(db);
    }

    @Test
    public void testCollectionsInstantiated() throws SQLException, ClassNotFoundException {
        DatabaseManager db = new DatabaseManager(
                "jdbc:derby://localhost:1527/",
                "iotbay",
                "iotbay",
                "iotbaydb"
        );
        assertNotNull(db.getUsers());
        assertNotNull(db.getProducts());
        assertNotNull(db.getCategories());
        assertNotNull(db.getOrders());
        assertNotNull(db.getOrderLineItems());
        assertNotNull(db.getPayments());
        assertNotNull(db.getInvoices());
        assertNotNull(db.getShipments());
        assertNotNull(db.getPaymentMethods());

    }

}
