/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.adfor.rapportino.controller;

import it.adfor.rapportino.model.Activity;
import it.adfor.rapportino.model.Client;
import it.adfor.rapportino.model.Project;
import it.adfor.rapportino.repository.ActivityRepository;
import it.adfor.rapportino.repository.ClientRepository;
import it.adfor.rapportino.repository.ProjectRepository;
import java.util.Collection;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    ClientRepository clientRepository;

    @RequestMapping( method = RequestMethod.GET)
    public Iterable<Project> getProjects() {
        Iterable<Project> projects =  projectRepository.findAll();
        for(Project p : projects){
            Hibernate.initialize(p.getClient());
        }
        return projects;
    }
    @RequestMapping(value="{id}",method = RequestMethod.GET)
    public Project getProject(@PathVariable Integer id){
        return projectRepository.findOne(id);
    }
    
    @RequestMapping(value="details/{id}",method = RequestMethod.GET)
    public Project getDetailedProject(@PathVariable Integer id){
        Project p = projectRepository.findOne(id);
        Hibernate.initialize(p.getClient());
        Hibernate.initialize(p.getPm());
        Hibernate.initialize(p.getCm());
        Hibernate.initialize(p.getDivision());
        Hibernate.initialize(p.getType());
        return p;
    }
    @RequestMapping(value = "pm/{pmid}", method = RequestMethod.GET)
    public Iterable<Project> getProjectsByPm(@PathVariable("pmid") Integer pmid) {
        Iterable<Project> projects =  projectRepository.findByPmId(pmid);
        for (Project p : projects){
            Hibernate.initialize(p.getClient());
            Hibernate.initialize(p.getCm());
            Hibernate.initialize(p.getCommittente());
            Hibernate.initialize(p.getPm());
            Hibernate.initialize(p.getStatus());
            Hibernate.initialize(p.getDivision());
        }
        return projects;
    }
    
    @RequestMapping( method = RequestMethod.POST)
    public Integer saveProject(@RequestBody Project p){
       return projectRepository.save(p).getId();
        
    }

}
