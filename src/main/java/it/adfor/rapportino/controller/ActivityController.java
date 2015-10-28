/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.adfor.rapportino.controller;

import it.adfor.rapportino.model.Activity;
import it.adfor.rapportino.repository.ActivityRepository;
import java.util.Collection;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("{userid}/a")
public class ActivityController {

    @Autowired
    ActivityRepository activityRepository;

    Collection<Activity> getActivities(@PathVariable Integer userid) {
        return activityRepository.findByStaffId(userid);
    }

    @RequestMapping(method = RequestMethod.POST)
    public void addActivity(@PathVariable Integer userid, @Valid @RequestBody Activity activity) {
//        User user = userRepository.findByUsername(username)
//                .orElseThrow(() -> new UserNotFoundException(username));
//        note.setUser(user);
//        noteRepository.save(note);
    }

}
