import iotbay.database.DatabaseManager;
import iotbay.exceptions.UserExistsException;
import iotbay.exceptions.UserNotFoundException;
import iotbay.models.Category;
import iotbay.models.Product;
import iotbay.models.User;
import org.junit.Before;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DatabaseTests {

    private DatabaseManager db;

    public DatabaseTests() throws SQLException, ClassNotFoundException {
        // create a in-memory database
        this.db = new DatabaseManager(
                "jdbc:derby:memory:",
                "",
                "",
                "iotbaydb;create=true",
                true,
                true
        );
    }

    @Test
    @Order(1)
    public void testDatabaseManager() throws SQLException, ClassNotFoundException {
        assertNotNull(this.db);
    }

    @Test
    @Order(2)
    public void testCollectionsInstantiated() throws SQLException, ClassNotFoundException {
        assertNotNull(this.db.getUsers());
        assertNotNull(this.db.getProducts());
        assertNotNull(this.db.getCategories());
        assertNotNull(this.db.getOrders());
        assertNotNull(this.db.getOrderLineItems());
        assertNotNull(this.db.getPayments());
        assertNotNull(this.db.getInvoices());
        assertNotNull(this.db.getShipments());
        assertNotNull(this.db.getPaymentMethods());

    }

    @Test
    @Order(3)
    public void createUser() throws SQLException, UserExistsException, NoSuchAlgorithmException, InvalidKeySpecException {
        final String firstName = "John";
        final String lastName = "Doe";
        final String email = "email@example.com";
        final String password = "password";
        final String address = "123 Example Street";
        final String phoneNumber = "0400000000";
        final boolean staff = false;
        final Timestamp registrationDate = new Timestamp(System.currentTimeMillis());
        final String stripeCustomerId = "cus_123456789";
        final String username = "jdoe";


        User user = new User(this.db);

        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(password);
        user.setAddress(address);
        user.setPhoneNumber(phoneNumber);
        user.setStaff(staff);
        user.setRegistrationDate(registrationDate);
        user.setStripeCustomerId(stripeCustomerId);
        user.setUsername(username);

        this.db.getUsers().registerUserTest(user);

        User retrievedUser = this.db.getUsers().getUser("jdoe");

        assertNotNull(retrievedUser);
        assertTrue(retrievedUser.getId() > 0);
        assertEquals(firstName, retrievedUser.getFirstName());
        assertEquals(lastName, retrievedUser.getLastName());
        assertEquals(email, retrievedUser.getEmail());
        assertEquals(address, retrievedUser.getAddress());
        assertEquals(phoneNumber, retrievedUser.getPhoneNumber());
        assertEquals(staff, retrievedUser.isStaff());
        assertEquals(registrationDate, retrievedUser.getRegistrationDate());
        assertEquals(stripeCustomerId, retrievedUser.getStripeCustomerId());
        assertEquals(username, retrievedUser.getUsername());

    }

    @Test
    @Order(4)
    public void authenticateUser() throws SQLException, NoSuchAlgorithmException, InvalidKeySpecException, UserNotFoundException {
        User user = this.db.getUsers().authenticateUser("jdoe", "password");
        assertNotNull(user);
        assertEquals("jdoe", user.getUsername());
    }

    @Test
    public void getProducts() throws SQLException {
        List<Product> products = this.db.getProducts().getProducts(50, 0 , false);
        assertNotNull(products);
        assertTrue(products.size() > 0);
    }

    @Test
    public void getCategories() throws SQLException {
        List<Category> categories = this.db.getCategories().getCategories();
        assertNotNull(categories);
        assertTrue(categories.size() > 0);
    }



}
