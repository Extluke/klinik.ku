package com.telemedclinic.prescription.exception;

// Ubah dari Exception menjadi RuntimeException
public class PrescriptionNotFoundException extends RuntimeException {
    public PrescriptionNotFoundException(String message) {
        super(message);
    }
}