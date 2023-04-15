package iotbay.models.collections;

import iotbay.database.DatabaseManager;
import iotbay.exceptions.ProductNotFoundException;
import iotbay.models.entities.Product;

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


//        try (PreparedStatement stmt = this.db.prepareStatement(
//                query,
//                offset,
//                limit
//        )) {
//
//            try (ResultSet rs = stmt.executeQuery()) {
//                while (rs.next()) {
//                    Product product = new Product(rs);
//                    productList.add(product);
//                }
//            }
//        }
        return productList;
    }

    public int getProductCount() throws Exception {
        try (Connection conn = this.db.getDbConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement(
                    "SELECT COUNT(*) FROM PRODUCT"
            )) {
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        return rs.getInt(1);
                    } else {
                        throw new Exception("Could not retrieve product count");
                    }
                }
            }
        }
    }

    /**
     * Retrieves a product from the database.
     *
     * @param productId the id of the product to retrieve
     * @return the product as a Product object
     * @throws SQLException             if there is an error retrieving the product
     * @throws ProductNotFoundException if the product does not exist
     */
    public Product getProduct(int productId) throws SQLException, ProductNotFoundException {

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
                    if (!rs.next()) {
                        throw new ProductNotFoundException("The product with id " + productId + " does not exist.");
                    }

                    return new Product(rs);
                }
            }
        }

    }
}
