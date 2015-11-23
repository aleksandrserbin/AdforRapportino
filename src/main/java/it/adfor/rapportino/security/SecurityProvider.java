
package it.adfor.rapportino.security;

import org.springframework.stereotype.Service;

@Service
public interface SecurityProvider {
    
    public boolean hasAccess(CurrentUserDetails cud, Integer id);
    
}
