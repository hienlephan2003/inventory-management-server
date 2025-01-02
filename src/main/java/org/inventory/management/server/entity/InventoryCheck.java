package org.inventory.management.server.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class InventoryCheck {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal totalPrice;
    private String name;
    @OneToMany(mappedBy = "inventoryCheck", cascade = CascadeType.ALL)
    private List<InventoryCheckDetail> items;
    private Date createdDate;
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;
}
