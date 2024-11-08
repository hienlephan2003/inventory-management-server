package org.inventory.management.server.repository;

import org.inventory.management.server.entity.Company;
import org.inventory.management.server.entity.InboundReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {
}
