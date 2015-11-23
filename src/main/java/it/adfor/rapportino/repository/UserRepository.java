/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.adfor.rapportino.repository;

import it.adfor.rapportino.model.Activity;
import it.adfor.rapportino.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PreAuthorize;

@RepositoryRestResource(exported=false)
public interface UserRepository extends CrudRepository<User, Integer>{
    User findByUsername(String username);
    @PreAuthorize("@SecurityProvider.hasAccess(principal, #id)")
    User findByStaffid(@Param("id") Integer id);
}
