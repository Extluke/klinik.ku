package com.telemedclinic.admin.service;

import java.util.List;
import java.util.Locale;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.telemedclinic.admin.dto.AdminDashboardStats;
import com.telemedclinic.auth.dto.AuthResponse;
import com.telemedclinic.auth.service.AuthService;
import com.telemedclinic.auth.service.DoctorProvisioningResult;
import com.telemedclinic.auth.service.DoctorProvisioningService;
import com.telemedclinic.auth.service.PharmacistProvisioningResult;
import com.telemedclinic.auth.service.PharmacistProvisioningService;
import com.telemedclinic.user.dto.CreateDoctorForm;
import com.telemedclinic.user.dto.CreateDoctorRequest;
import com.telemedclinic.user.dto.CreatePharmacistForm;
import com.telemedclinic.user.dto.CreatePharmacistRequest;
import com.telemedclinic.pharmacy.dto.CreatePharmacyForm;
import com.telemedclinic.pharmacy.dto.PharmacyRegisterRequest;
import com.telemedclinic.pharmacy.entity.Pharmacy;
import com.telemedclinic.pharmacy.service.PharmacyService;
import com.telemedclinic.user.entity.Role;
import com.telemedclinic.user.entity.User;
import com.telemedclinic.pharmacy.repository.PharmacyRepository;
import com.telemedclinic.user.repository.UserRepository;

@Service
public class AdminService {

    private final AuthService authService;
    private final DoctorProvisioningService doctorProvisioningService;
    private final PharmacistProvisioningService pharmacistProvisioningService;
    private final PharmacyService pharmacyService;
    private final PharmacyRepository pharmacyRepository;
    private final UserRepository userRepository;

    public AdminService(
            AuthService authService,
            DoctorProvisioningService doctorProvisioningService,
            PharmacistProvisioningService pharmacistProvisioningService,
            PharmacyService pharmacyService,
            PharmacyRepository pharmacyRepository,
            UserRepository userRepository
    ) {

        this.authService = authService;
        this.doctorProvisioningService = doctorProvisioningService;
        this.pharmacistProvisioningService = pharmacistProvisioningService;
        this.pharmacyService = pharmacyService;
        this.pharmacyRepository = pharmacyRepository;
        this.userRepository = userRepository;
    }

    // Membuat pharmacy baru dari input admin dengan status langsung approved dan aktif.
    @Transactional
    public Pharmacy createPharmacy(CreatePharmacyForm form) {
        Pharmacy pharmacy = pharmacyService.registerPharmacy(
                new PharmacyRegisterRequest(
                        form.getName(),
                        form.getAddress(),
                        form.getPhoneNumber(),
                        form.getLegalDocumentNumber(),
                        form.getLatitude(),
                        form.getLongitude()
                )
        );

        pharmacy.approveApplication();
        return pharmacyRepository.save(pharmacy);
    }

    // Mengambil semua pharmacy yang terdaftar untuk kebutuhan halaman admin.
    public List<Pharmacy> findAllPharmacies() {
        return pharmacyRepository.findAll();
    }

    public List<Pharmacy> findPharmacies(String search) {
        List<Pharmacy> pharmacies = findAllPharmacies();

        if (search == null || search.isBlank()) {
            return pharmacies;
        }

        String keyword = search.toLowerCase(Locale.ROOT);

        return pharmacies.stream()
                .filter(pharmacy -> containsIgnoreCase(pharmacy.getName(), keyword)
                        || containsIgnoreCase(pharmacy.getAddress(), keyword)
                        || containsIgnoreCase(pharmacy.getPhoneNumber(), keyword)
                        || containsIgnoreCase(pharmacy.getLegalDocumentNumber(), keyword))
                .toList();
    }

    // Membuat akun doctor baru dari input admin dengan status langsung approved.
    public DoctorProvisioningResult createDoctor(CreateDoctorForm form) {
        return doctorProvisioningService.provisionDoctor(
                new CreateDoctorRequest(
                        form.getName(),
                        form.getEmail(),
                        form.getPhoneNumber(),
                        form.getSpecialization(),
                        form.getLicenseNumber()
                )
        );
    }

    public DoctorProvisioningResult resendDoctorCredentials(Long doctorId) {
        return doctorProvisioningService.resendCredentials(doctorId);
    }

    // Membuat akun pharmacist baru untuk pharmacy yang sudah dipilih admin.
    public PharmacistProvisioningResult createPharmacist(CreatePharmacistForm form) {
        return pharmacistProvisioningService.provisionPharmacist(
                new CreatePharmacistRequest(
                        form.getName(),
                        form.getEmail(),
                        form.getPhoneNumber(),
                        form.getLicenseNumber(),
                        form.getPharmacyId()
                )
        );
    }

    // Mengambil semua user dari seluruh role untuk kebutuhan manajemen akun admin.
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    // Mengambil akun doctor dan pharmacist terbaru untuk ringkasan dashboard admin.
    public List<User> findRecentMedicalStaffUsers() {
        return userRepository.findTop5ByRoleInOrderByCreatedAtDesc(List.of(
                Role.ROLE_DOCTOR,
                Role.ROLE_PHARMACIST
        ));
    }

    // Mengambil pharmacy terbaru untuk ringkasan dashboard admin.
    public List<Pharmacy> findRecentPharmacies() {
        return pharmacyRepository.findTop5ByOrderByPharmacyIdDesc();
    }

    // Mengaktifkan atau menonaktifkan user berdasarkan userId.
    @Transactional
    public User toggleUserStatus(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found."));

        user.toggleActiveStatus();
        return userRepository.save(user);
    }

    // Menghitung agregasi data utama untuk dashboard admin.
    public AdminDashboardStats getDashboardStats() {
        return AdminDashboardStats.builder()
                .totalDoctor(userRepository.countByRole(Role.ROLE_DOCTOR))
                .totalPharmacist(userRepository.countByRole(Role.ROLE_PHARMACIST))
                .totalCustomer(userRepository.countByRole(Role.ROLE_CUSTOMER))
                .totalPharmacy(pharmacyRepository.count())
                .totalInactiveUser(userRepository.countByActiveFalse())
                .build();
    }

    private boolean containsIgnoreCase(String value, String keyword) {
        return value != null && value.toLowerCase(Locale.ROOT).contains(keyword);
    }
}
