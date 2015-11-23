
package it.adfor.rapportino.repository;


import it.adfor.rapportino.model.Staff;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported=false)
public interface StaffRepository extends CrudRepository<Staff, Integer>{
    
}
