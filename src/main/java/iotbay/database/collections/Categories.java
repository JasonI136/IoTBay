package iotbay.database.collections;

import iotbay.database.DatabaseManager;
import iotbay.models.Category;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a collection of categories
 */
public class Categories {

    /**
     * An instance of the database manager
     */
    private final DatabaseManager db;

    /**
     * Initializes the categories collection with the database manager
     * @param db an instance of the database manager
     */
    public Categories(DatabaseManager db) {
        this.db = db;
    }

    /**
     * Retrieves all the categories from the database.
     * @return a list of {@link iotbay.models.Category} objects
     * @throws SQLException if there is an error retrieving the categories
     */
    public List<Category> getCategories() throws SQLException {
        List<Category> categoryList = new ArrayList<>();

        String query = "SELECT * FROM CATEGORY";

        try (Connection conn = this.db.getDbConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        categoryList.add(new Category(rs));
                    }
                }
            }
        }

        return categoryList;


    }

    public void addCategory(Category category) throws SQLException {
        String query = "INSERT INTO CATEGORY (NAME) VALUES (?)";

        try (Connection conn = this.db.getDbConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, category.getName());
                int affectedRows =  stmt.executeUpdate();

                if (affectedRows == 0) {
                    throw new SQLException("Creating category failed, no rows affected.");
                }
            }
        }
    }

    public Category getCategory(String asString) throws SQLException {
        String query = "SELECT * FROM CATEGORY WHERE NAME = ?";

        try (Connection conn = this.db.getDbConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, asString);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        return new Category(rs);
                    }
                }
            }
        }

        return null;
    }

    public void deleteCategory(int categoryId) throws SQLException {
        String query = "DELETE FROM CATEGORY WHERE ID = ?";

        try (Connection conn = this.db.getDbConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, categoryId);
                int affectedRows =  stmt.executeUpdate();

                if (affectedRows == 0) {
                    throw new SQLException("Deleting category failed, no rows affected.");
                }
            }
        }
    }
}
