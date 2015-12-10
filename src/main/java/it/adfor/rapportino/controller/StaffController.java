
package it.adfor.rapportino.controller;

import it.adfor.rapportino.model.Staff;
import it.adfor.rapportino.repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/staff")
public class StaffController {
    
    @Autowired 
    StaffRepository staffRepository;
    
    @RequestMapping(value="{id}", method=RequestMethod.GET)
    public Staff getStaffInfo(@PathVariable Integer id){
        return staffRepository.findOne(id);
    }
    
    @RequestMapping(value="{id}", method=RequestMethod.DELETE)
    public void deleteStaff(@PathVariable Integer id){
        staffRepository.delete(id);
    }
    
    @RequestMapping( method=RequestMethod.PUT)
    public Integer updateStaff(@RequestBody Staff s){
        return staffRepository.save(s).getId();
    }
    
    
    @RequestMapping(method = RequestMethod.GET)
    public Iterable<Staff> getStaff(){
        return staffRepository.findAll();
    }
}
