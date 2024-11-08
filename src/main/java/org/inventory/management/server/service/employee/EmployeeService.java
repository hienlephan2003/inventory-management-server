package org.inventory.management.server.service.employee;

import org.inventory.management.server.entity.Employee;
import org.inventory.management.server.exception.DataNotFoundException;
import org.inventory.management.server.exception.UserExistException;
import org.inventory.management.server.model.employee.RegisterUserModel;
import org.inventory.management.server.model.employee.SignInEmployeeModel;
import org.inventory.management.server.model.employee.EmployeeModelRes;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface EmployeeService {
    EmployeeModelRes createNewUser(RegisterUserModel createUserRequest) throws UserExistException;
    EmployeeModelRes signIn(SignInEmployeeModel signInRequest) throws UsernameNotFoundException;
    Employee getUserByUsername(String username) throws DataNotFoundException;
    Employee findById(Long userId);
}

