package org.inventory.management.server.repository;

import org.inventory.management.server.entity.Area;
import org.inventory.management.server.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AreaRepository extends JpaRepository<Area, Long> {
}
