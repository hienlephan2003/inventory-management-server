package org.inventory.management.server.model.company;

import lombok.*;

@Data//toString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CompanyRes {
    private Long id;
    private String name;
    private String phone;
    private String address;
    private String email;
}
