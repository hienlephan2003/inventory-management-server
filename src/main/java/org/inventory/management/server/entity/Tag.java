package org.inventory.management.server.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Boolean isDeleted;
    private String description;
    private Date createdDate;
    @ManyToMany()
    @JoinTable(
            name = "product_tag",
            joinColumns = @JoinColumn(name = "tag_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private Set<Product> products = new HashSet<>();
    @ManyToOne
    @JoinColumn(name = "area_id")
    private Area area;
}