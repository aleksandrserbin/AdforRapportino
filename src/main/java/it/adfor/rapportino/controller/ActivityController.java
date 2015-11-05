
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

    @RequestMapping(method = RequestMethod.GET)
    Collection<Activity> getActivities(@PathVariable Integer userid) {
        return activityRepository.findByEmplId(userid);
    }

    @RequestMapping(method = RequestMethod.GET, value = "{s}_{e}")
    Collection<Activity> getActivities(@PathVariable("s") String ss, @PathVariable("e") String se, @PathVariable("userid") Integer id) {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date s = formatter.parse(ss);
            Date e = formatter.parse(se);
            return activityRepository.findByDateBetweenAndEmplId(s, e, id);
        } catch (Exception ex) {
            return null;
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public void addActivity(@Valid @RequestBody Activity a) {
        activityRepository.save(a);
    }

    @RequestMapping(value="{a_id}",method = RequestMethod.DELETE)
    public void deleteActivity(@PathVariable("a_id") Integer id) {
        activityRepository.delete(id);
    }
    
    @RequestMapping(method=RequestMethod.PUT)
    public void updateActivity(@RequestBody Activity a){
        activityRepository.save(a);
    }
    
    

}
