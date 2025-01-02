package org.inventory.management.server.repository;

import org.inventory.management.server.entity.InboundReportDetail;
import org.inventory.management.server.entity.OutboundReportDetail;
import org.inventory.management.server.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface OutboundReportDetailRepository extends JpaRepository<OutboundReportDetail, Long> {
    @Query("SELECT s FROM OutboundReportDetail s WHERE s.product = :product AND s.createdDate BETWEEN :startTime AND :endTime")
    List<OutboundReportDetail> findByProductAndCreatedDateBetween(
            @Param("product") Product product,
            @Param("startTime") Date startTime,
            @Param("endTime") Date endTime);
    @Query("SELECT s FROM OutboundReportDetail s WHERE s.createdDate BETWEEN :startTime AND :endTime")
    List<OutboundReportDetail> findByCreatedDateBetween(
            @Param("startTime") Date startTime,
            @Param("endTime") Date endTime);
}
