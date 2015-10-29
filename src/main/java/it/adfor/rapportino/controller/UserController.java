/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.adfor.rapportino.controller;

import it.adfor.rapportino.model.Activity;
import it.adfor.rapportino.model.User;
import it.adfor.rapportino.repository.UserRepository;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author alex
 */
@RestController
@RequestMapping("/users")
public class UserController {
    
    @Autowired
    UserRepository userRepository;
    
    @RequestMapping(method=RequestMethod.GET)
    User getActivities(@RequestBody User u) {
        return userRepository.findOneByUsenameAndPassword(u.getUsername(), u.getPassword());
    }
    
}
