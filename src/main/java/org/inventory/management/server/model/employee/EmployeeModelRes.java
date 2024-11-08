package org.inventory.management.server.model.employee;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.inventory.management.server.model.enumeratiion.Role;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeModelRes {
    private String username;
    private Role role;
    private String accessToken;
}
