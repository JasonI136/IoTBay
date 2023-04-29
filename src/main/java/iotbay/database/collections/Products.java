package iotbay.database.collections;

import iotbay.database.DatabaseManager;
import iotbay.exceptions.ProductInOrderException;
import iotbay.models.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a collection of products
 *
 * @author cmesina
 */
public class Products {

    /**
     * An instance of the database manager
     */
    private final DatabaseManager db;

    /**
     * Initializes the products collection with the database manager
     *
     * @param db
     */
    public Products(DatabaseManager db) {
        this.db = db;
    }

    /**
     * Retrieves an ArrayList of products from the database depending on the limit and offset.
     *
     * @param limit       the number of products to retrieve
     * @param offset      the offset to start retrieving products from
     * @param description whether to include the product description in the results
     * @return an ArrayList of products
     * @throws SQLException if there is an error retrieving the products
     */
    public List<Product> getProducts(int limit, int offset, boolean description) throws SQLException {
        List<Product> productList = new ArrayList<>();

        String query;

        if (description) {
            query = "SELECT PRODUCT.id, PRODUCT.name, PRODUCT.description, "
                    + "PRODUCT.image_url, PRODUCT.price, PRODUCT.quantity, PRODUCT.category_id, "
                    + "category.name AS category_name "
                    + "FROM PRODUCT "
                    + "INNER JOIN CATEGORY "
                    + "ON PRODUCT.category_id = CATEGORY.id "
                    + "ORDER BY PRODUCT.id "
                    + "OFFSET ? ROWS "
                    + "FETCH NEXT ? ROWS ONLY";
        } else {
            query = "SELECT PRODUCT.id, PRODUCT.name, PRODUCT.image_url, PRODUCT.price, PRODUCT.quantity, PRODUCT.category_id, "
                    + "category.name AS category_name "
                    + "FROM PRODUCT "
                    + "INNER JOIN CATEGORY "
                    + "ON PRODUCT.category_id = CATEGORY.id "
                    + "ORDER BY PRODUCT.id "
                    + "OFFSET ? ROWS "
                    + "FETCH NEXT ? ROWS ONLY";
        }

        try (Connection conn = this.db.getDbConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, offset);
                stmt.setInt(2, limit);
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        productList.add(new Product(rs));
                    }
                }
            }
        }

        return productList;
    }

    /**
     * Gets the number of products in the database.
     * @return the number of products in the database
     * @throws SQLException if there is an error retrieving the number of products
     */
    public int getProductCount() throws SQLException {
        try (Connection conn = this.db.getDbConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement(
                    "SELECT COUNT(*) FROM PRODUCT"
            )) {
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        return rs.getInt(1);
                    }
                }
            }
        }

        return 0;
    }

    /**
     * Gets the number of products in the database that match the given product name..
     * @param productName the name of the product to search for
     * @return the number of products in the database that match the given product name
     * @throws SQLException if there is an error retrieving the number of products
     */
    public int getProductCount(String productName) throws SQLException {
        try (Connection conn = this.db.getDbConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement(
                    "SELECT COUNT(*) FROM PRODUCT WHERE LOWER(name) LIKE LOWER(?)"
            )) {
                stmt.setString(1, "%" + productName + "%");
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        return rs.getInt(1);
                    }
                }
            }
        }

        return 0;
    }

    /**
     * Retrieves a product from the database.
     *
     * @param productId the id of the product to retrieve
     * @return the product as a Product object
     * @throws SQLException             if there is an error retrieving the product
     */
    public Product getProduct(int productId) throws SQLException {

        try (Connection conn = this.db.getDbConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement(
                    "SELECT PRODUCT.id, PRODUCT.name, PRODUCT.description, "
                            + "PRODUCT.image_url, PRODUCT.price, PRODUCT.quantity, PRODUCT.category_id, "
                            + "category.name AS category_name "
                            + "FROM PRODUCT "
                            + "INNER JOIN CATEGORY "
                            + "ON PRODUCT.category_id = CATEGORY.id "
                            + "WHERE PRODUCT.id = ?"
                            + "ORDER BY PRODUCT.id "

            )) {
                stmt.setInt(1, productId);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        return new Product(rs);
                    }
                }
            }
        }

        return null;

    }

    /**
     * Retrieves a list of products from the database that match the given product name.
     * @param productName the name of the product to search for
     * @param limit the number of products to retrieve
     * @param offset the offset to start retrieving products from
     * @return a list of {@link iotbay.models.Product} objects.
     * @throws SQLException if there is an error retrieving the products
     */
    public List<Product> searchProduct(String productName, int limit, int offset) throws SQLException {
        List<Product> productList = new ArrayList<>();

        String query = "SELECT PRODUCT.id, PRODUCT.name, PRODUCT.description, "
                + "PRODUCT.image_url, PRODUCT.price, PRODUCT.quantity, PRODUCT.category_id, "
                + "category.name AS category_name "
                + "FROM PRODUCT "
                + "INNER JOIN CATEGORY "
                + "ON PRODUCT.category_id = CATEGORY.id "
                + "WHERE LOWER(PRODUCT.name) LIKE LOWER(?) "
                + "ORDER BY PRODUCT.id "
                + "OFFSET ? ROWS "
                + "FETCH NEXT ? ROWS ONLY";

        try (Connection conn = this.db.getDbConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, "%" + productName + "%");
                stmt.setInt(2, offset);
                stmt.setInt(3, limit);
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        productList.add(new Product(rs));
                    }
                }
            }
        }

        return productList;
    }

    /**
     * Updates a product in the database.
     * @param product the product to update
     * @throws SQLException if there is an error updating the product
     */
    public void updateProduct(Product product) throws SQLException {
        try (Connection conn = this.db.getDbConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement(
                    "UPDATE PRODUCT SET name = ?, description = ?, image_url = ?, price = ?, quantity = ?, category_id = ? WHERE id = ?"
            )) {
                stmt.setString(1, product.getName());
                stmt.setString(2, product.getDescription());
                stmt.setString(3, product.getImageURL());
                stmt.setDouble(4, product.getPrice());
                stmt.setInt(5, product.getQuantity());
                stmt.setInt(6, product.getCategoryId());
                stmt.setInt(7, product.getId());
                stmt.executeUpdate();
            }
        }
    }

    /**
     * Deletes a product from the database.
     * @param product the product to delete
     */
    public void deleteProduct(Product product) throws SQLException, ProductInOrderException {
        // check if product is in any orders
        try (Connection conn = this.db.getDbConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement(
                    "SELECT * FROM ORDER_LINE_ITEM WHERE product_id = ?"
            )) {
                stmt.setInt(1, product.getId());
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        throw new ProductInOrderException("Unable to delete product, product is in an order.");
                    }
                }
            }
        }


        try (Connection conn = this.db.getDbConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement(
                    "DELETE FROM PRODUCT WHERE id = ?"
            )) {
                stmt.setInt(1, product.getId());
                int affectedRows = stmt.executeUpdate();

                if (affectedRows == 0) {
                    throw new SQLException("Deleting product failed, no rows affected.");
                }
            }
        }
    }

}
