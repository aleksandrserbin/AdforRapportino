
package it.adfor.rapportino.security;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component(value = "SecurityProvider")
public class SecurityProviderImpl implements SecurityProvider{
    @Override
    public boolean hasAccess(CurrentUserDetails cud, Integer id){
        System.out.println("ya rabotayu suka");
        return cud!=null && (cud.getScope().equals("ADM") || 
                cud.getStaffId().equals(id));
    }
}
