package iotbay.models;

import iotbay.database.DatabaseManager;
import iotbay.exceptions.ProductNotFoundException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Products {
    private final DatabaseManager db;

    public Products(DatabaseManager db) {
        this.db = db;
    }

    /**
     * Retrieves an ArrayList of products from the database depending on the limit and offset.
     * @param limit the number of products to retrieve
     * @param offset the offset to start retrieving products from
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


        try (PreparedStatement pstmt = this.db.getDbConnection().prepareStatement(query)) {
            pstmt.setInt(1, offset);
            pstmt.setInt(2, limit);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Product product = new Product();
                    product.setProductId(rs.getInt("id"));
                    product.setName(rs.getString("name"));
                    if (description) {
                        product.setDescription(rs.getString("description"));
                    }

                    product.setPrice(rs.getInt("price"));
                    product.setQuantity(rs.getInt("quantity"));
                    product.setCategoryId(rs.getInt("category_id"));
                    product.setCategoryName(rs.getString("category_name"));
                    product.setImageURL(rs.getString("image_url"));
                    productList.add(product);
                }
            }
        }
        return productList;
    }

    /**
     * Retrieves a product from the database.
     * @param productId the id of the product to retrieve
     * @return the product as a Product object
     * @throws SQLException if there is an error retrieving the product
     * @throws ProductNotFoundException if the product does not exist
     */
    public Product getProduct(int productId) throws SQLException, ProductNotFoundException {
        PreparedStatement productQuery = this.db.getDbConnection().prepareStatement("SELECT * FROM PRODUCT WHERE id = ?");
        productQuery.setInt(1, productId);

        ResultSet rs = productQuery.executeQuery();

        if (!rs.next()) {
            throw new ProductNotFoundException("The product with id " + productId + " does not exist.");
        }

        Product product = new Product();
        product.setProductId(rs.getInt("id"));
        product.setName(rs.getString("name"));
        product.setDescription(rs.getString("description"));
        product.setPrice(rs.getInt("price"));
        product.setQuantity(rs.getInt("quantity"));
        product.setCategoryId(rs.getInt("category_id"));
        product.setImageURL(rs.getString("image_url"));

        return product;
    }
}
