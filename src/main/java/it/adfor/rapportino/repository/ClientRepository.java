
package it.adfor.rapportino.repository;

import it.adfor.rapportino.model.Client;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported=false)
public interface ClientRepository extends CrudRepository<Client, Integer>{
    
}
