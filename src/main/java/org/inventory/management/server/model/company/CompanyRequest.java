package org.inventory.management.server.model.company;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;
@Data//toString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CompanyRequest {
    private String name;
    private String phone;
    private String address;
    private String email;
}
