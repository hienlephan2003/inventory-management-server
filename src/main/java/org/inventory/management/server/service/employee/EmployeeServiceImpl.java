package org.inventory.management.server.service.employee;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.inventory.management.server.entity.Employee;
import org.inventory.management.server.entity.Product;
import org.inventory.management.server.exception.DataNotFoundException;
import org.inventory.management.server.exception.UserExistException;
import org.inventory.management.server.model.employee.*;
import org.inventory.management.server.model.enumeratiion.Role;
import org.inventory.management.server.model.product.ProductModelRes;
import org.inventory.management.server.repository.EmployeeRepository;
import org.inventory.management.server.service.auth.AuthService;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final AuthService authService;
    private final AuthenticationManager authenticationManager;

    @Override
    public List<EmployeeModelRes> getEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        List<Employee> filteredEmployees = employees.stream()
                .filter(product -> product.getRole() == Role.EMPLOYEE_ROLE)
                .toList();
        return filteredEmployees.stream().map(item -> modelMapper.map(item, EmployeeModelRes.class)).toList();
    }

    @Override
    public EmployeeModelRes createNewEmployee(CreateEmployeeRequestModel createUserRequest) throws UserExistException {
        if (!employeeRepository.existsUserByUsername(createUserRequest.getUsername())) {
            Employee newEmployee = Employee.builder()
                    .name(createUserRequest.getName())
                    .username(createUserRequest.getUsername())
                    .password(passwordEncoder.encode(createUserRequest.getPassword()))
                    .address(createUserRequest.getAddress())
                    .role(Role.EMPLOYEE_ROLE)
                    .dob(createUserRequest.getDob())
                    .createdTime(new Date())
                    .avatar(createUserRequest.getAvatar())
                    .department(createUserRequest.getDepartment())
                    .position(createUserRequest.getPosition())
                    .build();
            Employee employee = employeeRepository.save(newEmployee);
            return modelMapper.map(employee, EmployeeModelRes.class);
        } else{
            throw new UserExistException("User exists!");
        }
    }

    @Override
    public EmployeeModelRes createNewUser(RegisterUserModel createUserRequest) throws UserExistException {
        if (!employeeRepository.existsUserByUsername(createUserRequest.getUsername())) {
            Employee newEmployee = Employee.builder()
                    .name(createUserRequest.getName())
                    .username(createUserRequest.getUsername())
                    .password(passwordEncoder.encode(createUserRequest.getPassword()))
                    .address(createUserRequest.getAddress())
                    .role(Role.EMPLOYEE_ROLE)
                    .build();
            Employee employee = employeeRepository.save(newEmployee);
            return modelMapper.map(employee, EmployeeModelRes.class);
        } else{
            throw new UserExistException("User exists!");
        }
    }
    
    @Override
    public EmployeeModelRes signIn(SignInEmployeeModel userModel) throws UsernameNotFoundException {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userModel.getUsername(), userModel.getPassword()));
        if(!authentication.isAuthenticated()) {
            throw new UsernameNotFoundException("Authentication failed");
        }
        Employee employee = employeeRepository.findUserByUsername(userModel.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User is not exists"));
        String token = authService.createToken(userModel.getUsername(), employee.getId());
        EmployeeModelRes userModelRes = modelMapper.map(employee, EmployeeModelRes.class);
        userModelRes.setAccessToken(token);
        return userModelRes;
    }

    @Override
    public Employee getUserByUsername(String username) {
        return (employeeRepository.findUserByUsername(username)).orElseThrow(()-> new DataNotFoundException("User is not exists"));
    }

    @Override
    public Employee findById(Long userId) {
        return employeeRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("Not found user"));
    }

    @Override
    public EmployeeModelRes updateProfile(Long userId, EmployeeRequestModel profile) {
        Employee employee  = findById(userId);
        String password = employee.getPassword();
        modelMapper.map(profile, employee);
        employee.setPassword(password);
        return modelMapper.map(employeeRepository.save(employee), EmployeeModelRes.class);
    }
    @Override
    public EmployeeModelRes updateProfileAndPassword(Long userId, EmployeeRequestModel profile) {
        Employee employee  = findById(userId);
        modelMapper.map(profile, employee);
        employee.setPassword(passwordEncoder.encode(profile.getPassword()));
        return modelMapper.map(employeeRepository.save(employee), EmployeeModelRes.class);
    }
}

