package org.inventory.management.server.repository;

import org.inventory.management.server.entity.Product;
import org.inventory.management.server.entity.StockReportDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface StockReportDetailRepository extends JpaRepository<StockReportDetail, Long> {
    Optional<StockReportDetail> findFirstByProductOrderByCreatedDate(Product product);

}
