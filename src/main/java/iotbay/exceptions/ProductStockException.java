package iotbay.exceptions;

import iotbay.models.Product;

import java.util.List;

public class ProductStockException extends Exception{

    public List<Product> outOfStockProducts;

    public ProductStockException(String message) {
        super(message);
    }

    public ProductStockException(String message, List<Product> outOfStockProducts) {
        super(message);
        this.outOfStockProducts = outOfStockProducts;
    }
}
