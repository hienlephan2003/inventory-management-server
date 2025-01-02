package org.inventory.management.server.repository;

import org.inventory.management.server.entity.StockReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface StockReportRepository extends JpaRepository<StockReport, Long> {
    Optional<StockReport> findFirstByOrderByDateDesc();
    @Query("SELECT sr FROM StockReport sr WHERE " +
            "EXTRACT(YEAR FROM CAST(sr.startDate AS DATE)) = EXTRACT(YEAR FROM CAST(:startDate AS DATE)) AND " +
            "EXTRACT(MONTH FROM CAST(sr.startDate AS DATE)) = EXTRACT(MONTH FROM CAST(:startDate AS DATE)) AND " +
            "EXTRACT(YEAR FROM CAST(sr.endDate AS DATE)) = EXTRACT(YEAR FROM CAST(:endDate AS DATE)) AND " +
            "EXTRACT(MONTH FROM CAST(sr.endDate AS DATE)) = EXTRACT(MONTH FROM CAST(:endDate AS DATE))")
    StockReport findFirstByYearAndMonth(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

}
