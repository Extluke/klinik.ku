package com.telemedclinic.model;

import java.util.List;
import java.util.ArrayList;

public class Pharmacy {

    // Attributes
    private final String pharmacyId;

    private String name;
    private String address;
    private String phoneNumber;

    private double latitude;
    private double longitude;

    private boolean isActive;

    private List<InventoryItem> inventoryItems;


    // Constructor
    public Pharmacy(
            String pharmacyId,
            String name,
            String address,
            String phoneNumber,
            double latitude,
            double longitude
    ) {
        this.pharmacyId = pharmacyId;
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.latitude = latitude;
        this.longitude = longitude;

        this.isActive = true;

        this.inventoryItems = new ArrayList<>();
    }


    // Getter
    public String getPharmacyId() {
        return pharmacyId;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public boolean isActive() {
        return isActive;
    }

    public List<InventoryItem> getInventoryItems() {
        return inventoryItems;
    }


    // Setter
    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setActive(boolean active) {
        isActive = active;
    }


    // Behavior methods
    public void addInventoryItem(InventoryItem inventoryItem) {
        inventoryItems.add(inventoryItem);
    }

    public void removeInventoryItem(InventoryItem inventoryItem) {
        inventoryItems.remove(inventoryItem);
    }

    public boolean hasMedicine(Medicine medicine) {

        for (InventoryItem item : inventoryItems) {

            if (item.getMedicine().equals(medicine)) {
                return true;
            }

        }

        return false;
    }

    public InventoryItem findInventoryItem(Medicine medicine) {

        for (InventoryItem item : inventoryItems) {

            if (item.getMedicine().equals(medicine)) {
                return item;
            }

        }

        return null;
    }

    public void activate() {
        this.isActive = true;
    }

    public void deactivate() {
        this.isActive = false;
    }
}
