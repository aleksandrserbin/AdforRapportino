
package it.adfor.rapportino.controller;

import it.adfor.rapportino.AppConfig;
import it.adfor.rapportino.dao.UserDao;
import it.adfor.rapportino.dao.UserDaoImpl;
import it.adfor.rapportino.model.Activity;
import it.adfor.rapportino.model.Staff;
import it.adfor.rapportino.model.User;
import it.adfor.rapportino.repository.UserRepository;
import it.adfor.rapportino.security.CurrentUserDetails;
import java.util.Collection;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    
    @Autowired
    UserRepository userRepository;
    
    
    @RequestMapping(value="{id}",method=RequestMethod.GET)
    User getUser(@PathVariable Integer id) {
        return userRepository.findByStaffid(id);
    }
    
    @RequestMapping(value="{id}",method=RequestMethod.PUT)
    void resetPassword(@PathVariable Integer id) {
        User u =  userRepository.findByStaffid(id);
        u.setPassword("123456");
        userRepository.save(u);
    }
    
    @RequestMapping(method=RequestMethod.PUT)
    void setUserWithPassword(@RequestBody User u, @RequestParam("password") String pass) {
        u.setPassword(pass);
        userRepository.save(u);
    }
    
    @RequestMapping(value="add",method=RequestMethod.PUT)
    void setUser(@RequestBody User u) {
        User user = userRepository.findByStaffid(u.getStaffId());
        if (user!=null){
            String password = user.getPassword();
            userRepository.delete(user);
            u.setPassword(password);
            userRepository.save(u);
        } else {
            u.setPassword("123456");
            userRepository.save(u);
        }
    }
    
    @RequestMapping(value = "cur", method = RequestMethod.GET)
    public UserDetails getCurrentUser(HttpServletResponse r) throws Exception{
        Object o  =  SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (o instanceof UserDetails) return (CurrentUserDetails) o;
        throw new Exception("UNAUTHORIZED");
    }
    
}
