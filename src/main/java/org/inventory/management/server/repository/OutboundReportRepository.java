package org.inventory.management.server.repository;

import org.inventory.management.server.entity.InboundReport;
import org.inventory.management.server.entity.OutboundReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OutboundReportRepository extends JpaRepository<OutboundReport, Long> {
}
