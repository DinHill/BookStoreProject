package model;

import java.io.Serializable;

/**
 * Represents a book in the bookstore.
 */
public class Book implements Serializable {
    private int id;
    private String title;
    private String author;
    private int quantity;
    private double price;

    public Book(int id, String title, String author, int quantity, double price) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.quantity = quantity;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return String.format("%-10d %-30s %-20s %-10d $%-10.2f", id, title, author, quantity, price);
    }
}