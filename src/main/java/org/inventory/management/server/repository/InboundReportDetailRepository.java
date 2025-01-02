package org.inventory.management.server.repository;

import org.inventory.management.server.entity.InboundReportDetail;
import org.inventory.management.server.entity.Product;
import org.inventory.management.server.entity.StockReportDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface InboundReportDetailRepository extends JpaRepository<InboundReportDetail, Long> {
    List<InboundReportDetail> findByProductAndExpirationDateAfterAndStockQuantityGreaterThanOrderByExpirationDateAsc(Product product, Date expirationDate, int stockQuantity);
    @Query("SELECT s FROM InboundReportDetail s WHERE s.product = :product AND s.createdDate BETWEEN :startTime AND :endTime")
    List<InboundReportDetail> findByProductAndCreatedDateBetween(
            @Param("product") Product product,
            @Param("startTime") Date startTime,
            @Param("endTime") Date endTime);
    @Query("SELECT s FROM InboundReportDetail s WHERE s.createdDate BETWEEN :startTime AND :endTime")
    List<InboundReportDetail> findByCreatedDateBetween(
            @Param("startTime") Date startTime,
            @Param("endTime") Date endTime);
}

