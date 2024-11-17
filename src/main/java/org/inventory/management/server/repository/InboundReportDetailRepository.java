package org.inventory.management.server.repository;

import org.inventory.management.server.entity.InboundReport;
import org.inventory.management.server.entity.InboundReportDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InboundReportDetailRepository extends JpaRepository<InboundReportDetail, Long> {
}
