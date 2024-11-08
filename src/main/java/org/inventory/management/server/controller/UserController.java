package org.inventory.management.server.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.inventory.management.server.entity.Employee;
import org.inventory.management.server.model.employee.RegisterUserModel;
import org.inventory.management.server.model.employee.SignInEmployeeModel;
import org.inventory.management.server.model.employee.EmployeeModelRes;
import org.inventory.management.server.service.employee.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class UserController {
    private final EmployeeService userService;

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getUserByUserId(@PathVariable Long id){
        return ResponseEntity.ok(userService.findById(id));
    }
    @PostMapping("/sign-up")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<EmployeeModelRes> signUp(@RequestBody RegisterUserModel createUserRequest){
        return ResponseEntity.ok(userService.createNewUser(createUserRequest));
    }
    @PostMapping("/sign-in")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public  ResponseEntity<EmployeeModelRes> signIn(@RequestBody SignInEmployeeModel userModel){
        return ResponseEntity.ok(userService.signIn(userModel));
    }
}
