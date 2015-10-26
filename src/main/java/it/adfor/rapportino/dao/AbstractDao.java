/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.adfor.rapportino.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

/**
 *
 * @author alex
 */
public abstract class AbstractDao<T> extends HibernateDaoSupport implements DAO<T> {
    
    private Class<T> parameterType = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
            
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
