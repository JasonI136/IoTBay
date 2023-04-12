/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package iotbay.models.entities;

import iotbay.util.Misc;

import java.io.Serializable;
import java.sql.ResultSet;

/**
 * Represents a product
 *
 * @author cmesina
 */
public class Product implements Serializable {
    /**
     * The product ID
     * <br>
     * <br>
     * <b>TABLE:</b> PRODUCT.id
     */
    private int id;

    /**
     * The product name
     * <br>
     * <br>
     * <b>TABLE:</b> PRODUCT.name
     */
    private String name;

    /**
     * The product description
     * <br>
     * <br>
     * <b>TABLE:</b> PRODUCT.description
     */
    private String description;

    /**
     * The product image URL
     * <br>
     * <br>
     * <b>TABLE:</b> PRODUCT.image_url
     */
    private String imageURL;

    /**
     * The product price
     * <br>
     * <br>
     * <b>TABLE:</b> PRODUCT.price
     */
    private double price;

    /**
     * The product quantity
     * <br>
     * <br>
     * <b>TABLE:</b> PRODUCT.quantity
     */
    private int quantity;

    /**
     * The product category ID
     * <br>
     * <br>
     * <b>TABLE:</b> PRODUCT.category_id FK CATEGORY.id
     */
    private int categoryId;

    /**
     * The product category name
     * <br>
     * <br>
     * <b>TABLE:</b> CATEGORY.name
     */
    private String categoryName;

    /**
     * Empty constructor
     */
    public Product() {
        // Empty constructor
    }

    public Product(ResultSet rs) {
        try {
            this.id = rs.getInt("id");
            this.name = rs.getString("name");
            // check if description is null
            if (Misc.hasColumn(rs, "description")) {
                this.description = rs.getString("description");
            } else {
                this.description = "";
            }
            this.imageURL = rs.getString("image_url");
            this.price = rs.getDouble("price");
            this.quantity = rs.getInt("quantity");
            this.categoryId = rs.getInt("category_id");
            this.categoryName = rs.getString("category_name");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    /**
     * Get the product category name
     *
     * @return The product category name
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * Get the product category name without spaces
     *
     * @return The product category name without spaces
     */
    public String getCategoryNameNoSpace() {
        return this.getCategoryName().replaceAll("\\s", "").replaceAll("/", "");
    }

    /**
     * Set the product category name
     *
     * @param categoryName The product category name
     */
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    /**
     * Get the product image URL
     *
     * @return The product image URL
     */
    public String getImageURL() {
        return imageURL;
    }

    /**
     * Set the product image URL
     *
     * @param imageURL The product image URL
     */
    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    /**
     * Get the product category ID
     *
     * @return The product category ID
     */
    public int getCategoryId() {
        return categoryId;
    }

    /**
     * Set the product category ID
     *
     * @param categoryId The product category ID
     */
    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * Get the product ID
     *
     * @return The product ID
     */
    public int getId() {
        return id;
    }

    /**
     * Set the product ID
     *
     * @param id The product ID
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Get the product name
     *
     * @return The product name
     */
    public String getName() {
        return name;
    }

    /**
     * Set the product name
     *
     * @param name The product name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the product description
     *
     * @return The product description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set the product description
     *
     * @param description The product description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Get the product price
     *
     * @return The product price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Set the product price
     *
     * @param price The product price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Get the product quantity
     *
     * @return The product quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Set the product quantity
     *
     * @param quantity The product quantity
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}
