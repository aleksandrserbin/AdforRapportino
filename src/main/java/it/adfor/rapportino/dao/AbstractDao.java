/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.adfor.rapportino.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import javax.transaction.Transactional;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

/**
 *
 * @author aleksandrserbin
 * <p> Abstract implementation of DAO interface </p>
 * <p> For method defenitions 
 * @see it.adfor.rapportino.dao.DAO </p>
 * @see it.adfor.rapportino.dao.ActivityDao
 * @see it.adfor.rapportino.dao.ProjectDao
 */
@Transactional
public abstract class AbstractDao<T> extends HibernateDaoSupport implements DAO<T> {
    
    protected Class<T> parameterType;
    
    /**
     * SessionFactory for interaction with database
     * SessionFactory can be configured in properties.properties file
     */
    @Autowired
    protected SessionFactory sessionFactory;
    
    @Autowired
    protected void initSessionFactory(){
        setSessionFactory(sessionFactory);
        
    }
    
    @Override
    public T findById(Integer id) {
        return getHibernateTemplate().get(parameterType, id);
    }

    @Override
    public Serializable create(T obj) {
       return getHibernateTemplate().save(obj);
    }

    @Override
    public void update(T obj) {
        getHibernateTemplate().update(obj);
    }

    @Override
    public void delete(T obj) {
        getHibernateTemplate().delete(obj);
    }
    
   
}
