package com.telemedclinic.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Entity;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class User {

    // Attributes
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String name;
    private String email;
    private String password;
    private String phoneNumber;


    // No-args constructor for JPA
    public User() {
    }


    // Constructor
    public User(
            String name,
            String email,
            String password,
            String phoneNumber
    ) {

        setName(name);
        setEmail(email);
        setPassword(password);
        setPhoneNumber(phoneNumber);
    }


    // Getter
    public Long getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    protected String getPassword() {
        return password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }


    // Setter
    public void setName(String name) {

        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException(
                    "Name cannot be empty."
            );
        }

        this.name = name;
    }

    public void setEmail(String email) {

        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException(
                    "Email cannot be empty."
            );
        }

        this.email = email;
    }

    public void setPassword(String password) {

        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException(
                    "Password cannot be empty."
            );
        }

        this.password = password;
    }

    public void setPhoneNumber(String phoneNumber) {

        if (phoneNumber == null || phoneNumber.isBlank()) {
            throw new IllegalArgumentException(
                    "Phone number cannot be empty."
            );
        }

        this.phoneNumber = phoneNumber;
    }


    // Behavior methods
    public void updateProfile(
            String name,
            String phoneNumber
    ) {

        setName(name);
        setPhoneNumber(phoneNumber);
    }

    public void changePassword(String newPassword) {
        setPassword(newPassword);
    }
}
