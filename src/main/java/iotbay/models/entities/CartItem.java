/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package iotbay.models.entities;

import java.io.Serializable;

/**
 * Represents a cart item
 * @author cmesina
 */
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
     * Gets the product of the cart item
     * @return the product
     */
    public Product getProduct() {
        return product;
    }

    /**
     * Sets the product of the cart item
     * @param product the product
     */
    public void setProduct(Product product) {
        this.product = product;
    }

    /**
     * Gets the quantity of the product in the cart
     * @return the quantity
     */
    public int getCartQuantity() {
        return cartQuantity;
    }

    /**
     * Sets the quantity of the product in the cart
     * @param cartQuantity the quantity
     */
    public void setCartQuantity(int cartQuantity) {
        this.cartQuantity = cartQuantity;
    }

    /**
     * Gets the total price of the cart item
     * @return the total price
     */
    public double getTotalPrice() {
        return product.getPrice() * cartQuantity;
    }
}
