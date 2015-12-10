
package it.adfor.rapportino.repository;

import it.adfor.rapportino.controller.ReportController;
import it.adfor.rapportino.model.Activity;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PreAuthorize;

@RepositoryRestResource(exported=false)
public interface ActivityRepository extends CrudRepository<Activity, Integer>{
    @PreAuthorize("@SecurityProvider.hasAccess(principal, #id)")
    Collection<Activity> findByEmplId(@Param("id") Integer id);
    @PreAuthorize("@SecurityProvider.hasAccess(principal, #id)")
    Collection<Activity> findByEmplIdAndSubmittedTrue(Integer id);
    @PreAuthorize("hasAuthority('ROLE_ADM')")
    Collection<Activity> findByEmplIdAndProjId(@Param("id") Integer id,@Param("pid") Integer pid);
    Collection<Activity> findByProjId(Integer id);
    Collection<Activity> findByTypeIdAndProjId(Integer id, Integer pid);
    Collection<Activity> findByTypeIdAndEmplId(Integer id, Integer eid);
    @PreAuthorize("@SecurityProvider.hasAccess(principal, #id)")
    Collection<Activity> findByDateBetweenAndEmplId(
            @Param("beg") Date beg,
            @Param("end") Date end,
            @Param("id") Integer id);
    @PreAuthorize("hasAuthority('ROLE_ADM')")
    Collection<Activity> findByDateBetweenAndEmplIdAndSubmittedTrue(
            @Param("beg") Date beg,
            @Param("end") Date end,
            @Param("id") Integer id);
    @PreAuthorize("hasAuthority('ROLE_ADM')")
    Collection<Activity> findByDateBetweenAndProjId(
            @Param("beg") Date beg,
            @Param("end") Date end,
            @Param("id") Integer id);
    Collection<Activity> findByDateBetween(
            @Param("beg") Date beg,
            @Param("end") Date end);
    Collection<Activity> findByDateBetweenAndTypeId(
            @Param("beg") Date beg,
            @Param("end") Date end,
            @Param("id") Integer id);
    @PreAuthorize("hasAuthority('ROLE_ADM')")
    Collection<Activity> findByDateBetweenAndTypeIdAndProjId(
            @Param("beg") Date beg,
            @Param("end") Date end,
            @Param("id") Integer id,
            @Param("pid") Integer pid);
    @PreAuthorize("hasAuthority('ROLE_ADM')")
    Collection<Activity> findByDateBetweenAndTypeIdAndEmplId(
            @Param("beg") Date beg,
            @Param("end") Date end,
            @Param("id") Integer id,
            @Param("eid") Integer eid);
    @PreAuthorize("hasAuthority('ROLE_ADM')")
    Collection<Activity> findByDateBetweenAndTypeIdAndEmplIdAndProjId(
            @Param("beg") Date beg,
            @Param("end") Date end,
            @Param("id") Integer id,
            @Param("eid") Integer eid,
            @Param("pid") Integer pid);
}
