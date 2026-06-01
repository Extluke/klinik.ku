package com.telemedclinic.pharmacy.repository;

import com.telemedclinic.pharmacy.entity.Pharmacy;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PharmacyRepository extends JpaRepository<Pharmacy, Long> {

    boolean existsByLegalDocumentNumber(String legalDocumentNumber);

    Optional<Pharmacy> findByLegalDocumentNumber(String legalDocumentNumber);
}
