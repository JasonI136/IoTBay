/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package iotbay.models.cart;

import iotbay.models.Product;

import java.io.Serializable;

/**
 *
 * @author cmesina
 */
public class CartItem implements Serializable {

    private Product product;
    private int cartQuantity;

    public CartItem() {
    }

    ;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getCartQuantity() {
        return cartQuantity;
    }

    public void setCartQuantity(int cartQuantity) {
        this.cartQuantity = cartQuantity;
    }

    public double getTotalPrice() {
        return product.getPrice() * cartQuantity;
    }
}
