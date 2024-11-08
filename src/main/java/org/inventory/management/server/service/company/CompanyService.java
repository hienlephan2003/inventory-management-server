package org.inventory.management.server.service.company;


import org.inventory.management.server.entity.Company;
import org.inventory.management.server.model.company.CompanyRequest;

import java.util.List;

public interface CompanyService {
    Company createCompany(CompanyRequest company);
    List<Company> getAll();
    Company getCompanyById(long id) ;
    Company updateCompany(long companyId, CompanyRequest companyRequest);

    Company deleteCompany(long id) ;
}
