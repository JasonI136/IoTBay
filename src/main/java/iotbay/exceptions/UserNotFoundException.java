/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Exception.java to edit this template
 */
package iotbay.exceptions;

/**
 *
 * @author cmesina
 */
public class UserNotFoundException extends Exception {

    /**
     * Creates a new instance of <code>UserNotFoundException</code> without
     * detail message.
     */
    public UserNotFoundException() {
    }

    /**
     * Constructs an instance of <code>UserNotFoundException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public UserNotFoundException(String msg) {
        super(msg);
    }
    
}
