package org.inventory.management.server.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class InventoryCheckDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "product_id")
    Product product;
    @ManyToOne
    @JoinColumn(name = "inventory_check_id")
    private InventoryCheck inventoryCheck;
    private Integer stock; // Tồn kho
    private Integer actualQuantity; // Thực tế
    private Integer loss; // Hao hụt
    private BigDecimal unitPrice; // Đơn giá
    private BigDecimal lossValue; // Giá trị hao hụt
    private Date createdDate; // Ngày kiểm kê
}
