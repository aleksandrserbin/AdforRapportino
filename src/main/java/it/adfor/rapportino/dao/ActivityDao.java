/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.adfor.rapportino.dao;

import it.adfor.rapportino.model.Activity;
import it.adfor.rapportino.model.Project;
import it.adfor.rapportino.model.Staff;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author alex
 */
public interface ActivityDao {
    public List<Activity> findByEmployee(Staff s);
    public List<Activity> findByEmployee(Staff s, Date begin, Date end);
    public List<Activity> findByProject(Project p, Date begin, Date end);
    public List<Activity> findByParameters(Map<String,String> kv);
    public List<Activity> findByProject(Project p);
}  
