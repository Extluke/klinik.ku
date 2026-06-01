package com.telemedclinic.inventory.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.telemedclinic.inventory.entity.InventoryItem;

@Repository
public interface InventoryItemRepository extends JpaRepository<InventoryItem, Long> {
    List<InventoryItem> findByMedicine_NameContainingIgnoreCase(String name);
    List<InventoryItem> findByMedicine_CategoryIgnoreCase(String category);
    List<InventoryItem> findByMedicine_NameContainingIgnoreCaseAndMedicine_CategoryIgnoreCase(String name, String category);
}
