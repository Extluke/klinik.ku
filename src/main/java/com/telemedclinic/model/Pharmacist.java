package com.telemedclinic.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

@Entity
public class Pharmacist extends User {

    // Attributes
    private String licenseNumber;

    @ManyToOne
    @JoinColumn(name = "pharmacy_id")
    private Pharmacy pharmacy;


    // No-args constructor for JPA
    public Pharmacist() {
    }


    // Constructor
    public Pharmacist(
            String name,
            String email,
            String password,
            String phoneNumber,
            String licenseNumber,
            Pharmacy pharmacy
    ) {

        super(
                name,
                email,
                password,
                phoneNumber
        );

        setLicenseNumber(licenseNumber);
        setPharmacy(pharmacy);
    }


    // Getter
    public String getLicenseNumber() {
        return licenseNumber;
    }

    public Pharmacy getPharmacy() {
        return pharmacy;
    }


    // Setter
    public void setLicenseNumber(String licenseNumber) {

        if (licenseNumber == null || licenseNumber.isBlank()) {
            throw new IllegalArgumentException(
                    "License number cannot be empty."
            );
        }

        this.licenseNumber = licenseNumber;
    }

    public void setPharmacy(Pharmacy pharmacy) {

        if (pharmacy == null) {
            throw new IllegalArgumentException(
                    "Pharmacy cannot be null."
            );
        }

        this.pharmacy = pharmacy;
    }


    // Behavior methods
    public boolean isAssignedTo(Pharmacy pharmacy) {

        return this.pharmacy.equals(pharmacy);
    }
}
