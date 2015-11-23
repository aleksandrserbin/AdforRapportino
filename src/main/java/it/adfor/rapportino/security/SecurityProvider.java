
package it.adfor.rapportino.security;

import java.security.Principal;
import org.springframework.stereotype.Service;

@Service
public interface SecurityProvider {
    
    public boolean hasAccess(Object cuds, Integer id);
    
}
