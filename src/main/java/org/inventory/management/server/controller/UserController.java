package org.inventory.management.server.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.inventory.management.server.entity.Employee;
import org.inventory.management.server.model.employee.*;
import org.inventory.management.server.service.employee.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class UserController {
    private final EmployeeService userService;

    @GetMapping("")
    public ResponseEntity<Employee> getUserByUserId(){
        Long userId = ((UserPrinciple)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId();
        return ResponseEntity.ok(userService.findById(userId));
    }
    @PostMapping("profile")
    public ResponseEntity<EmployeeModelRes> updateUserProfile(@RequestBody EmployeeRequestModel profile){
        Long userId = ((UserPrinciple)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId();

        return ResponseEntity.ok(userService.updateProfile(userId, profile));
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
