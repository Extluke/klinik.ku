package com.telemedclinic.prescription.repository;

import com.telemedclinic.prescription.model.PrescriptionItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrescriptionItemRepository extends JpaRepository<PrescriptionItem, Long> {
    
    // Kosongkan saja, bawaan Spring Boot sudah cukup untuk mengelola item obat.
    
}