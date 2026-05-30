package com.telemedclinic.doctor.controller;

import jakarta.servlet.http.HttpSession;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.telemedclinic.user.entity.Role;

@Controller
@RequestMapping("/doctor")
public class DoctorController {

    @GetMapping("/dashboard")
    public String showDashboard(
            HttpSession session,
            Model model
    ) {

        Object role = session.getAttribute("currentUserRole");

        if (role != Role.ROLE_DOCTOR) {
            return "redirect:/auth/login";
        }

        String doctorName = (String) session.getAttribute("currentUserName");
        String doctorEmail = (String) session.getAttribute("currentUserEmail");

        model.addAttribute("doctor", Map.of(
                "name", doctorName != null ? doctorName : "Dokter",
                "email", doctorEmail != null ? doctorEmail : "doctor@klinikku.id",
                "specialization", "Dokter Umum"
        ));

        model.addAttribute("stats", Map.of(
                "pendingConsultations", 4,
                "activeConsultations", 2,
                "completedToday", 8
        ));

        model.addAttribute("pendingConsultations", List.of(
                Map.of(
                        "patientName", "Budi Santoso",
                        "complaint", "Demam dan batuk sejak 3 hari",
                        "status", "PENDING",
                        "waitingTime", "12 menit"
                ),
                Map.of(
                        "patientName", "Siti Rahayu",
                        "complaint", "Konsultasi penggunaan obat",
                        "status", "IN_PROGRESS",
                        "waitingTime", "Sedang berlangsung"
                ),
                Map.of(
                        "patientName", "Ahmad Fauzi",
                        "complaint", "Sakit kepala berulang",
                        "status", "PENDING",
                        "waitingTime", "5 menit"
                )
        ));

        model.addAttribute("completedConsultations", List.of(
                Map.of(
                        "patientName", "Rina Marlina",
                        "diagnosis", "ISPA ringan",
                        "completedAt", "09:15"
                ),
                Map.of(
                        "patientName", "Hadi Susanto",
                        "diagnosis", "Konsultasi vitamin",
                        "completedAt", "10:42"
                ),
                Map.of(
                        "patientName", "Dewi Kusuma",
                        "diagnosis", "Resep obat lanjutan",
                        "completedAt", "11:58"
                )
        ));

        return "doctor/dashboard-doctor";
    }
}
