package model;

import java.io.Serializable;

/**
 * Represents a customer in the bookstore.
 */
public class Customer implements Serializable {
    private String name;
    private String address;

    public Customer(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return String.format("Customer{name='%s', address='%s'}", name, address);
    }
}