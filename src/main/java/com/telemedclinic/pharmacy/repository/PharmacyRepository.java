package com.telemedclinic.pharmacy.repository;

import java.util.List;
import java.util.Optional;

import com.telemedclinic.pharmacy.entity.Pharmacy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PharmacyRepository extends JpaRepository<Pharmacy, Long> {

    boolean existsByLegalDocumentNumber(String legalDocumentNumber);

    Optional<Pharmacy> findByLegalDocumentNumber(String legalDocumentNumber);

    List<Pharmacy> findTop5ByOrderByPharmacyIdDesc();
}

