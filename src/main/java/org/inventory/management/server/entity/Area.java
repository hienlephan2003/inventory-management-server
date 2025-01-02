package org.inventory.management.server.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Area {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Boolean isDeleted;
    @OneToMany(mappedBy = "area", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Tag> tags;
}