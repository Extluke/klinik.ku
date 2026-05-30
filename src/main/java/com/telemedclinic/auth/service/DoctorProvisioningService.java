package com.telemedclinic.auth.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.telemedclinic.user.dto.CreateDoctorRequest;
import com.telemedclinic.user.entity.Doctor;
import com.telemedclinic.user.repository.DoctorRepository;
import com.telemedclinic.user.repository.UserRepository;

@Service
public class DoctorProvisioningService {

    private static final Logger logger = LoggerFactory.getLogger(DoctorProvisioningService.class);

    private final UserRepository userRepository;
    private final DoctorRepository doctorRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    public DoctorProvisioningService(
            UserRepository userRepository,
            DoctorRepository doctorRepository,
            PasswordEncoder passwordEncoder,
            EmailService emailService
    ) {

        this.userRepository = userRepository;
        this.doctorRepository = doctorRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    public DoctorProvisioningResult provisionDoctor(CreateDoctorRequest request) {
        String tempPassword = PasswordGenerator.generate();
        Doctor doctor = createAndSaveDoctor(request, tempPassword);

        EmailResult emailResult = emailService.sendDoctorCredentials(
                request.getEmail(),
                request.getName(),
                tempPassword
        );

        if (emailResult.isSuccess()) {
            doctor.markCredentialSent();
            doctorRepository.save(doctor);
            return DoctorProvisioningResult.success(doctor);
        }

        logger.warn(
                "Doctor account was created, but credential email failed for {}. Error: {}",
                request.getEmail(),
                emailResult.getErrorMessage()
        );

        return DoctorProvisioningResult.emailFailed(
                doctor,
                emailResult.getErrorMessage()
        );
    }

    public DoctorProvisioningResult resendCredentials(Long doctorId) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new IllegalArgumentException("Doctor not found."));

        String tempPassword = PasswordGenerator.generate();
        doctor.changePassword(passwordEncoder.encode(tempPassword));
        doctor.markMustChangePassword();
        doctorRepository.save(doctor);

        EmailResult emailResult = emailService.sendDoctorCredentials(
                doctor.getEmail(),
                doctor.getName(),
                tempPassword
        );

        if (emailResult.isSuccess()) {
            doctor.markCredentialSent();
            doctorRepository.save(doctor);
            return DoctorProvisioningResult.success(doctor);
        }

        logger.warn(
                "Resend credential failed for doctorId={}: {}",
                doctorId,
                emailResult.getErrorMessage()
        );

        return DoctorProvisioningResult.emailFailed(
                doctor,
                emailResult.getErrorMessage()
        );
    }

    @Transactional
    private Doctor createAndSaveDoctor(
            CreateDoctorRequest request,
            String tempPassword
    ) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email is already registered.");
        }

        String hashedPassword = passwordEncoder.encode(tempPassword);

        Doctor doctor = new Doctor(
                request.getName(),
                request.getEmail(),
                hashedPassword,
                request.getPhoneNumber(),
                request.getSpecialization(),
                request.getLicenseNumber()
        );

        return doctorRepository.save(doctor);
    }
}
