package org.inventory.management.server.repository;

import org.inventory.management.server.entity.InboundReport;
import org.inventory.management.server.entity.OutboundReport;
import org.inventory.management.server.entity.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface OutboundReportRepository extends JpaRepository<OutboundReport, Long> {
    List<OutboundReport> findAllByDateBetween(Date start, Date end);
    Optional<OutboundReport> findOutboundReportByShipment(Shipment shipment);
}
