/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package iotbay.models;

import lombok.Data;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Represents a category
 * @author cmesina
 */
@Data
public class Category implements Serializable {

    /**
     * The category ID
     * <br>
     * <br>
     * <b>TABLE:</b> CATEGORY.id
     */
    private int id;

    /**
     * The category name
     * <br>
     * <br>
     * <b>TABLE:</b> CATEGORY.name
     */
    private String categoryName;

    /**
     * Default constructor
     */
    public Category() {}

    /**
     * Constructor for creating a category from a ResultSet
     * @param rs The ResultSet to create the category from
     * @throws Exception If the ResultSet is invalid
     */
    public Category(ResultSet rs) throws SQLException {
        this.id = rs.getInt("id");
        this.categoryName = rs.getString("name");
    }

    /**
     * Get the category name
     * @return The category name
     */
    public String getName() {
        return categoryName;
    }

    /**
     * Get the category name without spaces
     * @return The category name without spaces
     */
    public String getNameNoSpace() {
        return this.getName().replaceAll("\\s", "").replaceAll("/", "");
    }

    /**
     * Set the category name
     * @param name The category name
     */
    public void setName(String name) {
        this.categoryName = name;
    }

}
