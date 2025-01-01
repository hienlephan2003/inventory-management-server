package org.inventory.management.server.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.inventory.management.server.model.enumeratiion.ShipmentStatus;
import org.inventory.management.server.model.enumeratiion.ShipmentType;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Shipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private ShipmentType type;
    private Date date;
    private Date completedDate;
    private String fromPosition;
    private String toPosition;
    private String carrier;
    @Enumerated(EnumType.STRING)
    private ShipmentStatus status = ShipmentStatus.PENDING;
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee pic;
}
