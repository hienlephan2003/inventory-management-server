package org.inventory.management.server.repository;

import org.inventory.management.server.entity.InboundReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InboundReportRepository extends JpaRepository<InboundReport, Long> {
}
