/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.adfor.rapportino.dao;

import java.io.Serializable;

/**
 *
 * @author alex
 */
public interface DAO<T> {
    public T findById(Integer id);
    public Serializable create(T obj);
    public void update(T obj);
    public void delete(T obj);
}
