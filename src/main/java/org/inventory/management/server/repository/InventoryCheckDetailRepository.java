package org.inventory.management.server.repository;

import org.inventory.management.server.entity.InventoryCheckDetail;
import org.inventory.management.server.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface InventoryCheckDetailRepository extends JpaRepository<InventoryCheckDetail, Long> {
}

