
package it.adfor.rapportino.repository;

import it.adfor.rapportino.model.Activity;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PreAuthorize;

@RepositoryRestResource(exported=false)
public interface ActivityRepository extends CrudRepository<Activity, Integer>{
    @PreAuthorize("@SecurityProvider.hasAccess(principal, #id)")
    Collection<Activity> findByEmplId(@Param("id") Integer id);
    Collection<Activity> findByProjId(Integer id);
    Collection<Activity> findByTypeId(Integer id);
    @PreAuthorize("@SecurityProvider.hasAccess(principal, #id)")
    Collection<Activity> findByDateBetweenAndEmplId(
            @Param("beg") Date beg,
            @Param("end") Date end,
            @Param("id") Integer id);
}
