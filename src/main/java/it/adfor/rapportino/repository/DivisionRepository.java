
package it.adfor.rapportino.repository;

import it.adfor.rapportino.model.Division;
import java.io.Serializable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


@RepositoryRestResource(exported=false)
public interface DivisionRepository extends CrudRepository<Division, Integer>{
    
}
