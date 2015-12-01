
package it.adfor.rapportino.security;

import java.security.Principal;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component(value = "SecurityProvider")
public class SecurityProviderImpl implements SecurityProvider{
    @Override
    public boolean hasAccess(Object cuds, Integer id){
        if (cuds instanceof CurrentUserDetails) {
            CurrentUserDetails cud = (CurrentUserDetails) cuds;
            return   (cud.getScope().equals("ROLE_ADM") || 
                id.equals(cud.getStaffId())) && cud!=null;
        }
        return false;
    }
    
    
}
