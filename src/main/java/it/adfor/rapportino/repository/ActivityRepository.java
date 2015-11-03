
package it.adfor.rapportino.repository;

import it.adfor.rapportino.model.Activity;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported=false)
public interface ActivityRepository extends CrudRepository<Activity, Integer>{
    Collection<Activity> findByEmplId(Integer id);
    Collection<Activity> findByProjId(Integer id);
    Collection<Activity> findByTypeId(Integer id);
    Collection<Activity> findByDateBetweenAndEmplId(Date beg, Date end, Integer id);
}
