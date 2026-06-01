package com.telemedclinic.auth.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.telemedclinic.auth.dto.ChangePasswordForm;
import com.telemedclinic.auth.service.AuthService;
import com.telemedclinic.user.entity.Role;

@Controller
@RequestMapping("/auth/change-password")
public class ChangePasswordController {

    private final AuthService authService;

    public ChangePasswordController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping
    public String showChangePasswordPage(
            HttpSession session,
            Model model
    ) {

        if (!Boolean.TRUE.equals(session.getAttribute("mustChangePassword"))) {
            return redirectToDashboard((Role) session.getAttribute("currentUserRole"));
        }

        model.addAttribute("changePasswordForm", new ChangePasswordForm());
        return "auth/change-password";
    }

    @PostMapping
    public String changePassword(
            @Valid @ModelAttribute("changePasswordForm") ChangePasswordForm changePasswordForm,
            BindingResult bindingResult,
            HttpSession session
    ) {

        if (!changePasswordForm.isPasswordMatching()) {
            bindingResult.rejectValue(
                    "confirmPassword",
                    "passwordMismatch",
                    "Konfirmasi password tidak sesuai."
            );
        }

        if (bindingResult.hasErrors()) {
            return "auth/change-password";
        }

        Long userId = (Long) session.getAttribute("currentUserId");

        if (userId == null) {
            return "redirect:/auth/login";
        }

        authService.changePassword(
                userId,
                changePasswordForm.getNewPassword()
        );

        session.setAttribute("mustChangePassword", false);

        return redirectToDashboard((Role) session.getAttribute("currentUserRole"));
    }

    private String redirectToDashboard(Role role) {
        if (role == null) {
            return "redirect:/auth/login";
        }

        if (role == Role.ROLE_ADMIN) {
            return "redirect:/admin/dashboard";
        }

        if (role == Role.ROLE_PHARMACIST) {
            return "redirect:/pharmacist/dashboard";
        }

        if (role == Role.ROLE_DOCTOR) {
            return "redirect:/doctor/dashboard";
        }

        return "redirect:/customer/dashboard";
    }
}
