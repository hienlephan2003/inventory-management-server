package org.inventory.management.server.repository;

import org.inventory.management.server.entity.InventoryCheck;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface InventoryCheckRepository extends JpaRepository<InventoryCheck, Long> {
}
