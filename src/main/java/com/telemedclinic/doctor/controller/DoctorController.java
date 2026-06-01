package com.telemedclinic.doctor.controller;

import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.telemedclinic.user.entity.Doctor;
import com.telemedclinic.user.entity.Role;
import com.telemedclinic.user.entity.User;
import com.telemedclinic.user.repository.DoctorRepository;
import com.telemedclinic.user.repository.UserRepository;

@Controller
@RequestMapping("/doctor")
public class DoctorController {

    private final UserRepository userRepository;
    private final DoctorRepository doctorRepository;

    public DoctorController(UserRepository userRepository, DoctorRepository doctorRepository) {
        this.userRepository = userRepository;
        this.doctorRepository = doctorRepository;
    }

    private Optional<Doctor> findAuthenticatedDoctor(HttpSession session) {
        String roleValue = session.getAttribute("currentUserRole") != null
                ? session.getAttribute("currentUserRole").toString()
                : null;

        if (!Role.ROLE_DOCTOR.name().equals(roleValue)) {
            return Optional.empty();
        }

        String email = (String) session.getAttribute("currentUserEmail");
        if (email == null) {
            return Optional.empty();
        }

        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty() || !(optionalUser.get() instanceof Doctor doctor)) {
            return Optional.empty();
        }

        return Optional.of(doctor);
    }

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        Optional<Doctor> optionalDoctor = findAuthenticatedDoctor(session);
        if (optionalDoctor.isEmpty()) {
            return "redirect:/auth/login";
        }

        Doctor doctor = optionalDoctor.get();
        model.addAttribute("doctor", doctor);
        return "doctor/doctor-dashboard";
    }

    @GetMapping("/profile")
    public String profile(HttpSession session, Model model) {
        Optional<Doctor> optionalDoctor = findAuthenticatedDoctor(session);
        if (optionalDoctor.isEmpty()) {
            return "redirect:/auth/login";
        }

        Doctor doctor = optionalDoctor.get();
        model.addAttribute("doctor", doctor);
        return "doctor/doctor-profile";
    }

    @GetMapping("/antrean")
    public String antrean(HttpSession session, Model model) {
        Optional<Doctor> optionalDoctor = findAuthenticatedDoctor(session);
        if (optionalDoctor.isEmpty()) {
            return "redirect:/auth/login";
        }

        model.addAttribute("doctor", optionalDoctor.get());
        return "doctor/doctor-antrean-masuk";
    }

    @GetMapping("/live-konsultasi")
    public String live(HttpSession session, Model model) {
        Optional<Doctor> optionalDoctor = findAuthenticatedDoctor(session);
        if (optionalDoctor.isEmpty()) {
            return "redirect:/auth/login";
        }

        model.addAttribute("doctor", optionalDoctor.get());
        return "doctor/doctor-live-konsultasi";
    }

    @GetMapping("/earnings")
    public String earnings(HttpSession session, Model model) {
        Optional<Doctor> optionalDoctor = findAuthenticatedDoctor(session);
        if (optionalDoctor.isEmpty()) {
            return "redirect:/auth/login";
        }

        model.addAttribute("doctor", optionalDoctor.get());
        return "doctor/doctor-earnings";
    }
}
