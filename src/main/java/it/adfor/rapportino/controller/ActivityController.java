/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.adfor.rapportino.controller;

import it.adfor.rapportino.model.Activity;
import it.adfor.rapportino.model.Project;
import it.adfor.rapportino.repository.ActivityRepository;
import java.util.Collection;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("{userid}/a")
public class ActivityController {

    @Autowired
    ActivityRepository activityRepository;
    @RequestMapping(method=RequestMethod.GET)
    Collection<Activity> getActivities(@PathVariable Integer userid) {
        return activityRepository.findByEmplId(userid);
    }

    @RequestMapping(method=RequestMethod.POST)
    public void addActivity(@Valid @RequestBody Activity a) {
        System.out.println("IM IN ADDACTIVITY");
        System.out.println(a.getDate());
        System.out.println(a.getHours());
        System.out.println(a.getEmpl().getId());
//        Project p = new Project();
//        p.setId(pid);
//        a.setProj(p);
        activityRepository.save(a);
    }

}
