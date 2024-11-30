package com.example.cs157aproject;

public class Book {
    private String title;
    private int quantity;

    // Constructor
    public Book(String title, int quantity) {
        this.title = title;
        this.quantity = quantity;
    }

    // Getters
    public String getTitle() {
        return title;
    }

    public int getQuantity() {
        return quantity;
    }
}

