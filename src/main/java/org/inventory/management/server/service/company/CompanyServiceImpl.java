package org.inventory.management.server.service.company;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.inventory.management.server.entity.Company;
import org.inventory.management.server.model.company.CompanyRequest;
import org.inventory.management.server.repository.CompanyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {
    private final CompanyRepository companyRepository;

    @Override
    @Transactional
    public Company createCompany(CompanyRequest companyRequest) {
        Company newCompany = Company
                .builder()
                .name(companyRequest.getName())
                .email(companyRequest.getEmail())
                .phone(companyRequest.getPhone())
                .address(companyRequest.getAddress())
                .build();
        return companyRepository.save(newCompany);
    }

    @Override
    public List<Company> getAll() {
        return companyRepository.findAll();
    }

    @Override
    public Company getCompanyById(long id) {
        return companyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Company not found"));
    }
    
    @Override
    @Transactional
    public Company updateCompany(long companyId, CompanyRequest companyRequest) {
        Company existingCompany = getCompanyById(companyId);
        existingCompany.setName(companyRequest.getName());
        existingCompany.setEmail(companyRequest.getEmail());
        existingCompany.setPhone(companyRequest.getPhone());
        existingCompany.setAddress(companyRequest.getAddress());
        companyRepository.save(existingCompany);
        return existingCompany;
    }

    @Override
    @Transactional
    public Company deleteCompany(long id)  {
        Company company = companyRepository.findById(id)
                .orElseThrow(() ->
                new EntityNotFoundException("Not found company with id"+ id));
         companyRepository.delete(company);
        return company; 
    }
}
