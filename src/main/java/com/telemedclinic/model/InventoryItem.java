package com.telemedclinic.model;

import java.util.Objects;

public class InventoryItem {

    // Attributes
    private final String inventoryItemId;

    private final Medicine medicine;
    private final Pharmacy pharmacy;

    private int stock;
    private double price;


    // Constructor
    public InventoryItem(
            String inventoryItemId,
            Medicine medicine,
            Pharmacy pharmacy,
            int stock,
            double price
    ) {

        if (inventoryItemId == null || inventoryItemId.isBlank()) {
            throw new IllegalArgumentException(
                    "Inventory item ID cannot be empty."
            );
        }

        this.inventoryItemId = inventoryItemId;

        this.medicine = Objects.requireNonNull(
                medicine,
                "Medicine cannot be null."
        );

        this.pharmacy = Objects.requireNonNull(
                pharmacy,
                "Pharmacy cannot be null."
        );

        setStock(stock);
        setPrice(price);
    }


    // Getter
    public String getInventoryItemId() {
        return inventoryItemId;
    }

    public Medicine getMedicine() {
        return medicine;
    }

    public Pharmacy getPharmacy() {
        return pharmacy;
    }

    public int getStock() {
        return stock;
    }

    public double getPrice() {
        return price;
    }


    // Setter
    public void setStock(int stock) {

        if (stock < 0) {
            throw new IllegalArgumentException(
                    "Stock cannot be negative."
            );
        }

        this.stock = stock;
    }

    public void setPrice(double price) {

        if (price < 0) {
            throw new IllegalArgumentException(
                    "Price cannot be negative."
            );
        }

        this.price = price;
    }


    // Behavior methods
    public boolean isAvailable(int quantity) {
        return stock >= quantity;
    }

    public boolean isOutOfStock() {
        return stock <= 0;
    }

    public void reduceStock(int quantity) {

        if (quantity <= 0) {
            throw new IllegalArgumentException(
                    "Quantity must be greater than zero."
            );
        }

        if (!isAvailable(quantity)) {
            throw new IllegalArgumentException(
                    "Insufficient stock."
            );
        }

        stock -= quantity;
    }

    public void increaseStock(int quantity) {

        if (quantity <= 0) {
            throw new IllegalArgumentException(
                    "Quantity must be greater than zero."
            );
        }

        stock += quantity;
    }
}
