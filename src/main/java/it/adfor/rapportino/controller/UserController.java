/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.adfor.rapportino.controller;

import it.adfor.rapportino.AppConfig;
import it.adfor.rapportino.dao.UserDao;
import it.adfor.rapportino.dao.UserDaoImpl;
import it.adfor.rapportino.model.Activity;
import it.adfor.rapportino.model.User;
import it.adfor.rapportino.repository.UserRepository;
import java.util.Collection;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
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
    User checkUser(HttpServletRequest req) {
     
        String u = req.getParameter("username");
        String p = req.getParameter("password");
        AnnotationConfigApplicationContext ctx =  new AnnotationConfigApplicationContext(AppConfig.class);
        //ctx.refresh();
        UserDao udao =  ctx.getBean("userDao", UserDao.class);
        if (udao.checkIfExists(u, p))
        return userRepository.findByUsername(u);
        else 
            System.out.println("heh mda");
        return null;
        
    }
    
    
    
}
