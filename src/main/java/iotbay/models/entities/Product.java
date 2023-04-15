/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package iotbay.models.entities;

import iotbay.util.Misc;
import lombok.*;

import java.io.Serializable;
import java.sql.ResultSet;

/**
 * Represents a product
 *
 * @author cmesina
 */
@Data
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
     * Get the product category name without spaces
     *
     * @return The product category name without spaces
     */
    public String getCategoryNameNoSpace() {
        return this.getCategoryName().replaceAll("\\s", "").replaceAll("/", "");
    }

}
