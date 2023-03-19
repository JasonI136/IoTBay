package iotbay.models;

import iotbay.database.DatabaseManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Categories {
    private final DatabaseManager db;

    public Categories(DatabaseManager db) {
        this.db = db;
    }

    /**
     * Retrieves all the categories from the database
     * @return an ArrayList of categories
     * @throws SQLException if there is an error retrieving the categories
     */
    public List<Category> getCategories() throws SQLException {
        List<Category> categoryList = new ArrayList<>();

        String query = "SELECT * FROM CATEGORY";

        try (PreparedStatement pstmt = this.db.getDbConnection().prepareStatement(query)) {

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Category category = new Category();
                    category.setCategoryId(rs.getInt("id"));
                    category.setName(rs.getString("name"));
                    categoryList.add(category);
                }
            }
        }
        return categoryList;


    }
}
