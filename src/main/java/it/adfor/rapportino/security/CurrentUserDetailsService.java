
package it.adfor.rapportino.security;

import it.adfor.rapportino.dao.UserDao;
import it.adfor.rapportino.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CurrentUserDetailsService implements UserDetailsService{
    @Autowired
    private UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String string) throws UsernameNotFoundException {
        User user = userDao.getByUsername(string);
        if (user == null) throw new UsernameNotFoundException(
                String.format("User with username='%s' was not found", string));
        return new CurrentUserDetails(user);
    }
    
}
