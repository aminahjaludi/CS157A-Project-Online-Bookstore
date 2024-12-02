package com.example.cs157aproject;

public class Book {
    private String title;
    private int quantity;
    private double price; // New attribute

    // Updated Constructor
    public Book(String title, int quantity, double price) {
        this.title = title;
        this.quantity = quantity;
        this.price = price; // Initialize price
    }

    // Getters
    public String getTitle() {
        return title;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price; // Getter for price
    }
}


