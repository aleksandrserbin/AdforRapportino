
package it.adfor.rapportino.repository;

import it.adfor.rapportino.model.Activity;
import it.adfor.rapportino.model.Project;
import java.util.Date;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;


public interface ProjectRepository extends CrudRepository<Project, Integer>{
        Optional<Project> findByClientId(Integer id);
        Optional<Project> findByPmId(Integer id);
        Optional<Project> findByCmId(Integer id);
        Optional<Project> findByDivisionId(Integer id);
        Optional<Project> findByNameLike(String name);
        Optional<Project> findByTypeId(Integer id);
}
