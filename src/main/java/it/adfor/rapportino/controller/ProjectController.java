/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.adfor.rapportino.controller;

import it.adfor.rapportino.model.Project;
import it.adfor.rapportino.repository.ProjectRepository;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController 
@RequestMapping("/p")
public class ProjectController {
    
    @Autowired
    ProjectRepository projectRepository;
    @RequestMapping(method=RequestMethod.GET)
    public Iterable<Project> getProjects(){
        return projectRepository.findAll();
    }
    
}
