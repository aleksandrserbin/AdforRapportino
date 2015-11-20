/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.adfor.rapportino.dao;

import it.adfor.rapportino.model.Activity;
import it.adfor.rapportino.model.User;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author alex
 */
@Repository("userDao")
public class UserDaoImpl extends AbstractDao<Activity> implements UserDao{

    @Override
    public boolean checkIfExists(String username, String pass) {
        List<User> users = new ArrayList<User>();
        HibernateTemplate ht = getHibernateTemplate();
        SessionFactory sf = ht.getSessionFactory();
        Session s = sf.openSession();
        users = s.createSQLQuery("select * from users where username=\'"+username+"\' and password=\'"+pass+"\';").list();
        s.close();
        if (users.size()!=1) return false; else return true;
        
    }

    @Override
    public User getByUsername(String username) {
        System.out.println(username);
        HibernateTemplate ht = getHibernateTemplate();
        SessionFactory sf = ht.getSessionFactory();
        Session s = sf.openSession();
        return (User) s.createCriteria(User.class)
                .add(Restrictions.eq("username", username)).uniqueResult();
    }


   
    
}
