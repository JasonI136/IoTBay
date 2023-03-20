package iotbay.models.collections;

import iotbay.database.DatabaseManager;
import iotbay.models.entities.Category;

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
     * Retrieves all the categories from the database
     * @return an ArrayList of categories
     * @throws SQLException if there is an error retrieving the categories
     */
    public List<Category> getCategories() throws Exception {
        List<Category> categoryList = new ArrayList<>();

        String query = "SELECT * FROM CATEGORY";

        try (PreparedStatement pstmt = this.db.getDbConnection().prepareStatement(query)) {

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Category category = new Category(rs);
                    categoryList.add(category);
                }
            }
        }
        return categoryList;


    }
}
