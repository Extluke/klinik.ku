package com.telemedclinic.controller;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.telemedclinic.model.Role;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    // Menampilkan dashboard sederhana untuk customer yang sudah login.
    @GetMapping("/dashboard")
    public String showDashboard(
            HttpSession session,
            Model model
    ) {

        Object role = session.getAttribute("currentUserRole");

        if (role != Role.ROLE_CUSTOMER) {
            return "redirect:/auth/login";
        }

        model.addAttribute("customerName", session.getAttribute("currentUserName"));
        model.addAttribute("customerEmail", session.getAttribute("currentUserEmail"));

        return "customer/dashboard";
    }
}
