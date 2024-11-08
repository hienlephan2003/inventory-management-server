package org.inventory.management.server.entity;

import jakarta.persistence.*;

import lombok.*;
import org.inventory.management.server.model.enumeratiion.Role;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String username;
    @Enumerated(EnumType.STRING)
    private Role role;
    private String password;
    private String phone;
    private String address;
    @ManyToMany()
    @JoinTable(
            name = "calendar_PIC",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "calendar_id")
    )
    private Set<Calendar> calendars = new HashSet<>();

}