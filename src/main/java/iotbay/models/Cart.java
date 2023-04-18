/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package iotbay.models;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a user's shopping cart
 * @author cmesina
 */
@Data
public class Cart implements Serializable {

    /**
     * The list of cart items
     */
    private List<CartItem> cartItems;

    /**
     * Empty constructor that initializes the cart items list.
     */
    public Cart() {
        cartItems = new ArrayList<>();
    }

    /**
     * Adds a product to the cart. If the product is already in the cart, the quantity is updated.
     * @param product the product to add
     * @param quantity the quantity of the product to add
     */
    public void addCartItem(Product product, int quantity) {
        for (CartItem item : cartItems) {
            if (item.getProduct().getId() == product.getId()) {
                // The product is already in the cart, update the quantity
                item.setCartQuantity(item.getCartQuantity() + quantity);
                return;
            }
        }

        // The product is not yet in the cart, add a new item
        CartItem newItem = new CartItem();
        newItem.setProduct(product);
        newItem.setCartQuantity(quantity);
        cartItems.add(newItem);
    }

    /**
     * Updates the quantity of a product in the cart. If the quantity is 0, the product is removed from the cart.
     * @param product the product to update
     * @param quantity the new quantity of the product
     */
    public void updateCartItem(Product product, int quantity) {
        for (CartItem item : cartItems) {
            if (item.getProduct().getId() == product.getId()) {
                if (quantity == 0) {
                    cartItems.remove(item);
                } else {
                    item.setCartQuantity(quantity);
                }

                return;
            }
        }
        // Item not found in the cart, add a new item
        CartItem newItem = new CartItem();
        newItem.setProduct(product);
        newItem.setCartQuantity(quantity);
        cartItems.add(newItem);
    }

    /**
     * Gets the total price of the cart
     * @return the total price
     */
    public double getTotalPrice() {
        double totalPrice = 0.0;
        for (CartItem item : cartItems) {
            totalPrice += item.getTotalPrice();
        }
        return totalPrice;
    }

    /**
     * Gets the total quantity of the cart
     * @return the total quantity
     */
    public int getTotalQuantity() {
        int totalQuantity = 0;
        for (CartItem item : cartItems) {
            totalQuantity += item.getCartQuantity();
        }
        return totalQuantity;
    }

}
