package com.telemedclinic.auth.service;

public class EmailResult {

    private final boolean success;
    private final String errorMessage;

    private EmailResult(boolean success, String errorMessage) {
        this.success = success;
        this.errorMessage = errorMessage;
    }

    public static EmailResult success() {
        return new EmailResult(true, null);
    }

    public static EmailResult failure(String errorMessage) {
        return new EmailResult(false, errorMessage);
    }

    public boolean isSuccess() {
        return success;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
