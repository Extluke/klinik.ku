package com.telemedclinic.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ChangePasswordForm {

    @NotBlank(message = "Password baru tidak boleh kosong.")
    @Size(min = 8, message = "Password baru minimal 8 karakter.")
    private String newPassword;

    @NotBlank(message = "Konfirmasi password tidak boleh kosong.")
    private String confirmPassword;

    public boolean isPasswordMatching() {
        return newPassword != null && newPassword.equals(confirmPassword);
    }
}
