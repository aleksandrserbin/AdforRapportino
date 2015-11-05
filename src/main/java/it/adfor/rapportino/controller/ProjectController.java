/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.adfor.rapportino.controller;

import it.adfor.rapportino.model.Activity;
import it.adfor.rapportino.model.Project;
import it.adfor.rapportino.repository.ActivityRepository;
import it.adfor.rapportino.repository.ProjectRepository;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/p")
public class ProjectController {

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    ActivityRepository activityRepository;

    @RequestMapping(value = "all", method = RequestMethod.GET)
    public Iterable<Project> getProjects() {
        return projectRepository.findAll();
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public Iterable<Project> getProjectsByPm(@PathVariable("id") Integer pmid) {
        return projectRepository.findByPmId(pmid);
    }

    @RequestMapping(value = "info/{pid}", method = RequestMethod.GET)
    public Collection<Activity> getActivitiesByProject(@PathVariable("pid") Integer id) {
        return activityRepository.findByProjId(id);
    }

}
