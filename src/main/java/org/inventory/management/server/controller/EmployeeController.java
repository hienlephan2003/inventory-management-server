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

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;
    @GetMapping("")
    public ResponseEntity<List<EmployeeModelRes>> getEmployees(){
        return ResponseEntity.ok(employeeService.getEmployees());
    }
    @PostMapping("")
    public ResponseEntity<EmployeeModelRes> addEmployee(@RequestBody CreateEmployeeRequestModel model){
        return ResponseEntity.ok(employeeService.createNewEmployee(model));
    }
    @PutMapping("/{id}")
    public ResponseEntity<EmployeeModelRes> updateEmployee(@PathVariable Long id,@RequestBody EmployeeRequestModel model){
        return ResponseEntity.ok(employeeService.updateProfile(id, model));
    }
    @PutMapping("/password/{id}")
    public ResponseEntity<EmployeeModelRes> updateEmployeeWithPassword(@PathVariable Long id,@RequestBody EmployeeRequestModel model){
        return ResponseEntity.ok(employeeService.updateProfileAndPassword(id, model));
    }
}
