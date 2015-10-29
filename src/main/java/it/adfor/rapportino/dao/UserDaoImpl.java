/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.adfor.rapportino.dao;

import it.adfor.rapportino.model.Activity;
import it.adfor.rapportino.model.User;
import java.util.List;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author alex
 */
public class UserDaoImpl extends AbstractDao<Activity> implements UserDao{

    @Override
    public boolean checkIfExists() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

   
    
}
