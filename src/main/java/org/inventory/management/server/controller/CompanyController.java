package org.inventory.management.server.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.inventory.management.server.entity.Company;
import org.inventory.management.server.model.company.CompanyRequest;
import org.inventory.management.server.service.company.CompanyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/companies")
public class CompanyController {
    private final CompanyService companyService;

    @GetMapping
    public ResponseEntity<List<Company>> getAll() {
        return ResponseEntity.ok(companyService.getAll());
    }
    @PostMapping
    public ResponseEntity<Company> createCompany(@Valid @RequestBody CompanyRequest companyRequest) {
        return ResponseEntity.ok(companyService.createCompany(companyRequest));
    }
    @PutMapping("/{id}")
    public ResponseEntity<Company> updateCompany(@PathVariable Long id, @Valid @RequestBody CompanyRequest companyRequest){
        return ResponseEntity.ok(companyService.updateCompany(id, companyRequest));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Company> deleteCompany(@PathVariable Long id) {
        return ResponseEntity.ok(companyService.deleteCompany(id));
    }
}