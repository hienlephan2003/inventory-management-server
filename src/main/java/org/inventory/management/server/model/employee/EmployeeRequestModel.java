package org.inventory.management.server.model.employee;

import lombok.Data;
import org.inventory.management.server.model.enumeratiion.Role;

import java.util.Date;

@Data
public class EmployeeRequestModel {
    private String username;
    private String position;
    private Date createdTime = new Date();
    private String address;
    private String phone;
    private String name;
}
