package org.inventory.management.server.service.employee;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.inventory.management.server.entity.Employee;
import org.inventory.management.server.exception.DataNotFoundException;
import org.inventory.management.server.exception.UserExistException;
import org.inventory.management.server.model.employee.RegisterUserModel;
import org.inventory.management.server.model.enumeratiion.Role;
import org.inventory.management.server.model.employee.SignInEmployeeModel;
import org.inventory.management.server.model.employee.EmployeeModelRes;
import org.inventory.management.server.repository.UserRepository;
import org.inventory.management.server.service.auth.AuthService;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final AuthService authService;
    private final AuthenticationManager authenticationManager;

    @Override
    public EmployeeModelRes createNewUser(RegisterUserModel createUserRequest) throws UserExistException {
        if (!userRepository.existsUserByUsername(createUserRequest.getUsername())) {
            Employee newEmployee = Employee.builder()
                    .name(createUserRequest.getName())
                    .username(createUserRequest.getUsername())
                    .password(passwordEncoder.encode(createUserRequest.getPassword()))
                    .address(createUserRequest.getAddress())
                    .role(Role.EMPLOYEE_ROLE)
                    .build();
            Employee employee = userRepository.save(newEmployee);
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
        Employee employee = userRepository.findUserByUsername(userModel.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User is not exists"));
        String token = authService.createToken(userModel.getUsername(), employee.getId());
        EmployeeModelRes userModelRes = modelMapper.map(employee, EmployeeModelRes.class);
        userModelRes.setAccessToken(token);
        return userModelRes;
    }

    @Override
    public Employee getUserByUsername(String username) {
        return (userRepository.findUserByUsername(username)).orElseThrow(()-> new DataNotFoundException("User is not exists"));
    }

    @Override
    public Employee findById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("Not found user"));
    }
}

