package org.inventory.management.server.repository;

import org.inventory.management.server.entity.StockReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface StockReportRepository extends JpaRepository<StockReport, Long> {
    Optional<StockReport> findFirstByOrderByDateDesc();


}
