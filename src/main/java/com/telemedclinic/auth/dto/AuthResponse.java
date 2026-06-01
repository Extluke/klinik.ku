package com.telemedclinic.auth.dto;

import com.telemedclinic.user.entity.Role;

public class AuthResponse {

    private Long userId;
    private String name;
    private String email;
    private Role role;
    private boolean mustChangePassword;

    public AuthResponse(Long userId, String name, String email, Role role) {
        this(userId, name, email, role, false);
    }

    public AuthResponse(Long userId, String name, String email, Role role, boolean mustChangePassword) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.role = role;
        this.mustChangePassword = mustChangePassword;
    }

    public Long getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Role getRole() {
        return role;
    }

    public boolean isMustChangePassword() {
        return mustChangePassword;
    }
}
