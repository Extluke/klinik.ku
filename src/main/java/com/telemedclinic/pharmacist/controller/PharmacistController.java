package com.telemedclinic.pharmacist.controller;

import jakarta.servlet.http.HttpSession;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.telemedclinic.user.entity.Pharmacist;
import com.telemedclinic.user.entity.Role;
import com.telemedclinic.user.entity.User;
import com.telemedclinic.user.repository.UserRepository;

@Controller
@RequestMapping("/pharmacist")
public class PharmacistController {

    private final UserRepository userRepository;

    public PharmacistController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private Optional<Pharmacist> findAuthenticatedPharmacist(HttpSession session) {
        String roleValue = session.getAttribute("currentUserRole") != null
                ? session.getAttribute("currentUserRole").toString()
                : null;

        if (!Role.ROLE_PHARMACIST.name().equals(roleValue)) {
            return Optional.empty();
        }

        String email = (String) session.getAttribute("currentUserEmail");
        if (email == null) {
            return Optional.empty();
        }

        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty() || !(optionalUser.get() instanceof Pharmacist pharmacist)) {
            return Optional.empty();
        }

        return Optional.of(pharmacist);
    }

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        Optional<Pharmacist> optionalPharmacist = findAuthenticatedPharmacist(session);
        if (optionalPharmacist.isEmpty()) {
            return "redirect:/auth/login";
        }

        Pharmacist pharmacist = optionalPharmacist.get();
        model.addAttribute("pharmacist", pharmacist);
        model.addAttribute("pharmacy", pharmacist.getPharmacy());
        return "pharmacist/pharmacy-dashboard";
    }

    @GetMapping("/profile")
    public String profile(HttpSession session, Model model) {
        Optional<Pharmacist> optionalPharmacist = findAuthenticatedPharmacist(session);
        if (optionalPharmacist.isEmpty()) {
            return "redirect:/auth/login";
        }

        Pharmacist pharmacist = optionalPharmacist.get();
        model.addAttribute("pharmacist", pharmacist);
        model.addAttribute("pharmacy", pharmacist.getPharmacy());
        return "pharmacist/pharmacy-profile-v2";
    }

    @GetMapping("/validasi")
    public String validasi(HttpSession session, Model model) {
        Optional<Pharmacist> optionalPharmacist = findAuthenticatedPharmacist(session);
        if (optionalPharmacist.isEmpty()) {
            return "redirect:/auth/login";
        }

        model.addAttribute("pharmacist", optionalPharmacist.get());
        return "pharmacist/pharmacy-validasi-resep";
    }

    @GetMapping("/tracking")
    public String tracking(HttpSession session, Model model) {
        Optional<Pharmacist> optionalPharmacist = findAuthenticatedPharmacist(session);
        if (optionalPharmacist.isEmpty()) {
            return "redirect:/auth/login";
        }

        model.addAttribute("pharmacist", optionalPharmacist.get());
        return "pharmacist/pharmacy-tracking-pesanan";
    }

    @GetMapping("/katalog")
    public String katalog(HttpSession session, Model model) {
        Optional<Pharmacist> optionalPharmacist = findAuthenticatedPharmacist(session);
        if (optionalPharmacist.isEmpty()) {
            return "redirect:/auth/login";
        }

        model.addAttribute("pharmacist", optionalPharmacist.get());
        return "pharmacist/pharmacy-katalog-obat";
    }

    @GetMapping("/keuangan")
    public String keuangan(HttpSession session, Model model) {
        Optional<Pharmacist> optionalPharmacist = findAuthenticatedPharmacist(session);
        if (optionalPharmacist.isEmpty()) {
            return "redirect:/auth/login";
        }

        model.addAttribute("pharmacist", optionalPharmacist.get());
        return "pharmacist/pharmacy-pendapatan";
    }
}
