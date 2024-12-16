package org.inventory.management.server.repository;

import org.inventory.management.server.entity.OutboundLineItem;
import org.inventory.management.server.entity.OutboundReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OutboundLineItemRepository extends JpaRepository<OutboundLineItem, Long> {
}
