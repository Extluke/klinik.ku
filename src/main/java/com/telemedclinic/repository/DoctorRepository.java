package com.telemedclinic.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.telemedclinic.model.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
}
