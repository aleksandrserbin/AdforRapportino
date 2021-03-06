package it.adfor.rapportino.controller;

import com.itextpdf.text.Document;
import it.adfor.rapportino.model.Activity;
import it.adfor.rapportino.model.Project;
import it.adfor.rapportino.repository.ActivityRepository;
import it.adfor.rapportino.util.PDFGenerator;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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
    
    @RequestMapping(value = "submitted/{userid}", method = RequestMethod.GET)
    Collection<Activity> getSubmittedActivities(@PathVariable Integer userid) {
        Collection<Activity> acts = activityRepository
                .findByEmplIdAndSubmittedTrue(userid);
        for (Activity act : acts) {
            Hibernate.initialize(act.getProj().getId());
        }
        return acts;
    }
    
    @RequestMapping(value="{id}", method = RequestMethod.PATCH)
    public void submitSingle(@PathVariable("id") Integer id){
        Activity a = activityRepository.findOne(id);
        a.setSubmitted(Boolean.TRUE);
        activityRepository.save(a);
    }
    
    @RequestMapping(value="{id}/u", method = RequestMethod.PATCH)
    public void unsubmitSingle(@PathVariable("id") Integer id){
        Activity a = activityRepository.findOne(id);
        a.setSubmitted(Boolean.FALSE);
        activityRepository.save(a);
    }
    
    @RequestMapping(value="{userid}/{m}", method = RequestMethod.PUT)
    public void submit(@PathVariable("userid") Integer id, @PathVariable("m") Integer m){
        Calendar c = Calendar.getInstance();
        c.set(Calendar.MONTH, m);
        c.set(Calendar.DATE, 1);
        Date b = c.getTime();
        c.set(Calendar.DATE, c.getActualMaximum(Calendar.DATE));
        Date e = c.getTime();
        Collection<Activity> col = activityRepository
                .findByDateBetweenAndEmplId(b, e, id);
        for (Activity a:col){
            a.setSubmitted(Boolean.TRUE);
            activityRepository.save(a);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "{userid}/{s}_{e}")
    Collection<Activity> getActivities(@PathVariable("s") String ss, @PathVariable("e") String se, @PathVariable("userid") Integer id) {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date s = formatter.parse(ss);
            Date e = formatter.parse(se);
            System.out.println(s);
            System.out.println(e);
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
