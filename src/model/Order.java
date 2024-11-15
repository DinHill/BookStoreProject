package model;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Represents an order in the bookstore.
 */
public class Order implements Serializable {
    private static int idCounter = 0;
    private int id;
    private Customer customer;
    private Book[] books;

    public Order(Customer customer, Book[] books) {
        this.id = ++idCounter;
        this.customer = customer;
        this.books = books;
    }

    public static void setIdCounter(int idCounter) {
        Order.idCounter = idCounter;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Book[] getBooks() {
        return books;
    }

    public double getTotalPrice() {
        double totalPrice = 0;
        for (Book book : books) {
            totalPrice += book.getPrice() * book.getQuantity();
        }
        return totalPrice;
    }

    @Override
    public String toString() {
        return String.format("Order{id=%d, customer=%s, books=%s}", id, customer, Arrays.toString(books));
    }
}