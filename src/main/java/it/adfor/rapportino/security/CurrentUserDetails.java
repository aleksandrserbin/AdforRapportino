package it.adfor.rapportino.security;

import it.adfor.rapportino.model.User;
import org.springframework.security.core.authority.AuthorityUtils;

public class CurrentUserDetails
        extends org.springframework.security.core.userdetails.User {

    private User user;

    public CurrentUserDetails(User u) {
        super(u.getUsername(), u.getPassword(),
                AuthorityUtils.createAuthorityList(
                        u.getScope()));
        this.user = u;
    }

    public User getUser() {
        return user;
    }

    public String getUsername() {
        return user.getUsername();
    }

    public String getScope() {
        return user.getScope();
    }
    
    public Integer getStaffId(){
        return user.getStaffId();
    }
}
