package org.inventory.management.server.repository;

import org.inventory.management.server.entity.InboundReportDetail;
import org.inventory.management.server.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface InboundReportDetailRepository extends JpaRepository<InboundReportDetail, Long> {
    List<InboundReportDetail> findByProductAndExpirationDateAfterAndStockQuantityGreaterThanOrderByExpirationDateAsc(Product product, Date expirationDate, int stockQuantity);
}
