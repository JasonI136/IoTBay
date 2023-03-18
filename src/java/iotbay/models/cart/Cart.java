/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package iotbay.models.cart;

import iotbay.models.Product;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author cmesina
 */
public class Cart implements Serializable {

    private List<CartItem> cartItems;

    public Cart() {
        cartItems = new ArrayList<>();
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public void addCartItem(Product product, int quantity) {
        for (CartItem item : cartItems) {
            if (item.getProduct().getProductId() == product.getProductId()) {
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

    public void updateCartItem(Product product, int quantity) {
        for (CartItem item : cartItems) {
            if (item.getProduct().getProductId() == product.getProductId()) {
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

    public double getTotalPrice() {
        double totalPrice = 0.0;
        for (CartItem item : cartItems) {
            totalPrice += item.getTotalPrice();
        }
        return totalPrice;
    }

    public int getTotalQuantity() {
        int totalQuantity = 0;
        for (CartItem item : cartItems) {
            totalQuantity += item.getCartQuantity();
        }
        return totalQuantity;
    }

}
