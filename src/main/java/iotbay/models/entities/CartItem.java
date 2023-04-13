/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package iotbay.models.entities;

import lombok.*;

import java.io.Serializable;

/**
 * Represents a cart item
 * @author cmesina
 */
@Data
public class CartItem implements Serializable {

    /**
     * The product
     */
    private Product product;

    /**
     * The quantity of the product in the cart
     */
    private int cartQuantity;

    /**
     * Empty constructor
     */
    public CartItem() {
    }

    /**
     * Gets the total price of the cart item
     * @return the total price
     */
    public double getTotalPrice() {
        return product.getPrice() * cartQuantity;
    }
}
