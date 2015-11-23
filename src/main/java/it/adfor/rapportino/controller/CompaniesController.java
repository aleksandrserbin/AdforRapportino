
package it.adfor.rapportino.controller;

import it.adfor.rapportino.model.Company;
import it.adfor.rapportino.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/companies")
public class CompaniesController {
    
    @Autowired
    CompanyRepository companyRepository;
    
    @RequestMapping(method=RequestMethod.GET)
    public Iterable<Company> getCompanies(){
        return companyRepository.findAll();
    }
}
