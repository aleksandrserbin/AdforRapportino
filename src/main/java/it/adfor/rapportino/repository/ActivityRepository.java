
package it.adfor.rapportino.repository;

import it.adfor.rapportino.model.Activity;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;


public interface ActivityRepository extends CrudRepository<Activity, Integer>{
    Collection<Activity> findByStaffId(Integer id);
    Optional<Activity> findByProjectId(Integer id);
    Optional<Activity> findByType_id(Integer id);
    Optional<Activity> findByDateBetween(Date beg, Date end);
}
