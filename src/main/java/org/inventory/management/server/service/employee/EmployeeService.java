package org.inventory.management.server.service.employee;

import org.inventory.management.server.entity.Employee;
import org.inventory.management.server.exception.DataNotFoundException;
import org.inventory.management.server.exception.UserExistException;
import org.inventory.management.server.model.employee.*;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface EmployeeService {
    List<EmployeeModelRes> getEmployees();
    EmployeeModelRes createNewEmployee(CreateEmployeeRequestModel model) throws UserExistException;
    EmployeeModelRes createNewUser(RegisterUserModel createUserRequest) throws UserExistException;
    EmployeeModelRes signIn(SignInEmployeeModel signInRequest) throws UsernameNotFoundException;
    Employee getUserByUsername(String username) throws DataNotFoundException;
    Employee findById(Long userId);
    EmployeeModelRes updateProfile(Long userId, EmployeeRequestModel profile);
    EmployeeModelRes updateProfileAndPassword(Long userId, EmployeeRequestModel profile);
}

