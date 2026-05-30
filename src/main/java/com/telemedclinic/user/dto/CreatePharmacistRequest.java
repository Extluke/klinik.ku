package com.telemedclinic.user.dto;

public class CreatePharmacistRequest {

    private String name;
    private String email;
    private String phoneNumber;
    private String licenseNumber;
    private Long pharmacyId;

    public CreatePharmacistRequest() {
    }

    public CreatePharmacistRequest(
            String name,
            String email,
            String phoneNumber,
            String licenseNumber,
            Long pharmacyId
    ) {

        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.licenseNumber = licenseNumber;
        this.pharmacyId = pharmacyId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public Long getPharmacyId() {
        return pharmacyId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public void setPharmacyId(Long pharmacyId) {
        this.pharmacyId = pharmacyId;
    }
}
