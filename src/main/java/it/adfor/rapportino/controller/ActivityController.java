package it.adfor.rapportino.controller;

import it.adfor.rapportino.model.Activity;
import it.adfor.rapportino.model.Project;
import it.adfor.rapportino.repository.ActivityRepository;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.validation.Valid;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/activities")
public class ActivityController {

    @Autowired
    ActivityRepository activityRepository;

    @RequestMapping(value = "{userid}", method = RequestMethod.GET)
    Collection<Activity> getActivities(@PathVariable Integer userid) {
        Collection<Activity> acts = activityRepository.findByEmplId(userid);
        for (Activity act : acts) {
            Hibernate.initialize(act.getProj().getId());
        }
        return acts;
    }

    @RequestMapping(method = RequestMethod.GET, value = "{userid}/{s}_{e}")
    Collection<Activity> getActivities(@PathVariable("s") String ss, @PathVariable("e") String se, @PathVariable("userid") Integer id) {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date s = formatter.parse(ss);
            Date e = formatter.parse(se);
            Collection<Activity> acts
                    = activityRepository.findByDateBetweenAndEmplId(s, e, id);
            for (Activity act : acts) {
                Hibernate.initialize(act.getProj().getId());
            }
            return acts;
        } catch (Exception ex) {
            return null;
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public void addActivity(@Valid @RequestBody Activity a) {
        activityRepository.save(a);
    }

    @RequestMapping(value = "{a_id}", method = RequestMethod.DELETE)
    public void deleteActivity(@PathVariable("a_id") Integer id) {
        activityRepository.delete(id);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public void updateActivity(@RequestBody Activity a) {
        activityRepository.save(a);
    }

    @RequestMapping(method = RequestMethod.GET)
    public Collection<Activity> getActivitiesByProject(@RequestParam("projid") Integer id) {
        Collection<Activity> acts
                = activityRepository.findByProjId(id);
        for (Activity act : acts) {
            Hibernate.initialize(act.getProj().getId());
            Hibernate.initialize(act.getEmpl());
        }
        return acts;
    }

}
