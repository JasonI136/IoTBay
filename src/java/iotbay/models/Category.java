/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package iotbay.models;

import java.io.Serializable;

/**
 *
 * @author cmesina
 */
public class Category implements Serializable {

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }
    
    public String getNameNoSpace() {
        return this.getName().replaceAll("\\s", "").replaceAll("/", "");
    }

    public void setName(String name) {
        this.name = name;
    }
    private int categoryId;
    private String name;
    
    public Category() {}
    
}
