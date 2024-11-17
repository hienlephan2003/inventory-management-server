package org.inventory.management.server.repository;

import org.inventory.management.server.entity.InboundReport;
import org.inventory.management.server.entity.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShipmentRepository extends JpaRepository<Shipment, Long> {
}
