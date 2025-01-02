package org.inventory.management.server.model.employee;

import lombok.Data;
import org.inventory.management.server.model.enumeratiion.Role;

import java.util.Date;

@Data
public class EmployeeRequestModel {
    private String position;
    private String address;
    private String phone;
    private String name;
    private String department;
    private String avatar;
    private Date dob;
    private String password;
}
