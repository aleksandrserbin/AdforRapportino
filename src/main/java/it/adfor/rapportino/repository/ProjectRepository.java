
package it.adfor.rapportino.repository;

import it.adfor.rapportino.model.Activity;
import it.adfor.rapportino.model.Project;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PreAuthorize;

@RepositoryRestResource(exported=false)
public interface ProjectRepository extends CrudRepository<Project, Integer>{
        Optional<Project> findByClientId(Integer id);
        @PreAuthorize("@SecurityProvider.hasAccess(principal, #id)")
        Collection<Project> findByPmId(@Param("id") Integer id);
        Optional<Project> findByCmId(Integer id);
        Optional<Project> findByDivisionId(Integer id);
        Optional<Project> findByNameLike(String name);
        Optional<Project> findByTypeId(Integer id);
}
