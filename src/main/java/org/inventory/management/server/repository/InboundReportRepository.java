package org.inventory.management.server.repository;

import org.inventory.management.server.entity.InboundReport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface InboundReportRepository extends JpaRepository<InboundReport, Long> {
    List<InboundReport> findAllByDateBetween(Date start, Date end);
}
