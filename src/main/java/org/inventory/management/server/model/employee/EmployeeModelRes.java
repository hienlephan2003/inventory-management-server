package org.inventory.management.server.model.employee;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.inventory.management.server.model.enumeratiion.Role;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeModelRes {
    private int id;
    private String username;
    private Role role;
    private String accessToken;
    private String position;
    private Date createdTime = new Date();
    private String address;
    private String phone;
    private String name;
    private String department;
    private String avatar;
    private Date dob;
}
