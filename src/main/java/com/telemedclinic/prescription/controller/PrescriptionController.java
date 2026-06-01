package com.telemedclinic.prescription.controller;

import com.telemedclinic.prescription.model.Prescription;
import com.telemedclinic.prescription.service.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/prescription")
public class PrescriptionController {

    private final PrescriptionService prescriptionService;

    @Autowired
    public PrescriptionController(PrescriptionService prescriptionService) {
        this.prescriptionService = prescriptionService;
    }

    // --- JALUR UNTUK DOKTER: Menyimpan resep setelah konsultasi ---
    @PostMapping("/create")
    public String createPrescription(@ModelAttribute Prescription prescription, RedirectAttributes redirectAttributes) {
        try {
            prescriptionService.createPrescription(prescription);
            // Kalau sukses, kirim pesan notifikasi hijau ke layar
            redirectAttributes.addFlashAttribute("successMessage", "Mantap! Resep berhasil dibuat dan dikirim ke pasien.");
        } catch (Exception e) {
            // Kalau gagal, kirim pesan error merah ke layar
            redirectAttributes.addFlashAttribute("errorMessage", "Gagal membuat resep: " + e.getMessage());
        }
        // Setelah klik tombol, arahkan kembali ke halaman dashboard dokter
        return "redirect:/doctor/dashboard"; 
    }

    // --- JALUR UNTUK PASIEN / APOTEKER: Menebus resep ---
    @PostMapping("/redeem")
    public String redeemPrescription(@RequestParam("prescriptionId") String prescriptionId, RedirectAttributes redirectAttributes) {
        try {
            prescriptionService.redeemPrescription(prescriptionId);
            redirectAttributes.addFlashAttribute("successMessage", "Berhasil! Resep sudah ditebus dan sedang diproses.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Waduh, gagal menebus resep: " + e.getMessage());
        }
        // Setelah nebus, balik ke halaman dashboard customer
        return "redirect:/customer/dashboard"; 
    }
}