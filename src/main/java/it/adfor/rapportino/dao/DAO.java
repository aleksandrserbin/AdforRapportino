/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.adfor.rapportino.dao;

import java.io.Serializable;

/**
 *
 * @author aleksandrserbin
 * <p>Data Access Object Interface</p>
 * <p> Provides interaction with database </p>
 * @see it.adfor.rapportino.dao.AbstractDao
 * @see it.adfor.rapportino.dao.ActivityDao
 * @see it.adfor.rapportino.dao.ProjectDao
 */
public interface DAO<T> {
    /**
     * @param id Identifier (Integer) of Element in database
     * @return Element from database with given identifier
     */
    public T findById(Integer id);
    /**
     * @param obj Object to be added to database
     * @return Identifier (Integer) of added object in the databases
     */
    public Serializable create(T obj);
    /**
     * 
     * @param obj Object to be update 
     * <p> To update object, you have firstly to get in from database using find method</p>
     */
    public void update(T obj);
    /**
     * 
     * @param obj Deletes given object from database if exists 
     */
    public void delete(T obj);
}
