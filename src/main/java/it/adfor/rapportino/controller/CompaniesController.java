
package it.adfor.rapportino.controller;

import it.adfor.rapportino.model.Client;
import it.adfor.rapportino.model.Company;
import it.adfor.rapportino.model.Division;
import it.adfor.rapportino.repository.ClientRepository;
import it.adfor.rapportino.repository.CompanyRepository;
import it.adfor.rapportino.repository.DivisionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/companies")
public class CompaniesController {
    
    @Autowired
    CompanyRepository companyRepository;
    
    @Autowired
    ClientRepository clientRepository;
    
    @Autowired
    DivisionRepository divisionRepository;
    
    @RequestMapping(method=RequestMethod.GET)
    public Iterable<Company> getCompanies(){
        return companyRepository.findAll();
    }
    
    @RequestMapping(method=RequestMethod.POST)
    public void saveCompany(@RequestBody Company c){
        companyRepository.save(c);
    }
    
    @RequestMapping(method=RequestMethod.DELETE)
    public void deleteCompany(@RequestParam("id") Integer id){
        companyRepository.delete(id);
    }
    
    @RequestMapping(value="clients",method=RequestMethod.GET)
    public Iterable<Client> getClients(){
        return clientRepository.findAll();
    }
    @RequestMapping(value="clients",method=RequestMethod.POST)
    public void saveClient(@RequestBody Client c){
        clientRepository.save(c);
    }
    
    @RequestMapping(value="divisions",method=RequestMethod.GET)
    public Iterable<Division> getDivisions(){
        return divisionRepository.findAll();
    }
    
    @RequestMapping(value="divisions",method=RequestMethod.POST)
    public void saveDivision(@RequestBody Division d){
        divisionRepository.save(d);
    }
    
    @RequestMapping(value="divisions",method=RequestMethod.DELETE)
    public void deleteDivision(@RequestParam("id") Integer id){
        divisionRepository.delete(id);
    }
}
