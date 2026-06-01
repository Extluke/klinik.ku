package com.telemedclinic.auth.service;

import com.telemedclinic.user.entity.Doctor;

public class DoctorProvisioningResult {

    private final Doctor doctor;
    private final boolean emailSent;
    private final String emailError;

    private DoctorProvisioningResult(
            Doctor doctor,
            boolean emailSent,
            String emailError
    ) {

        this.doctor = doctor;
        this.emailSent = emailSent;
        this.emailError = emailError;
    }

    public static DoctorProvisioningResult success(Doctor doctor) {
        return new DoctorProvisioningResult(doctor, true, null);
    }

    public static DoctorProvisioningResult emailFailed(
            Doctor doctor,
            String errorMessage
    ) {

        return new DoctorProvisioningResult(doctor, false, errorMessage);
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public boolean isEmailSent() {
        return emailSent;
    }

    public String getEmailError() {
        return emailError;
    }
}
