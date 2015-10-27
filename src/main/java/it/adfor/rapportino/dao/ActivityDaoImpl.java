
package it.adfor.rapportino.dao;

import it.adfor.rapportino.model.Activity;
import it.adfor.rapportino.model.Project;
import it.adfor.rapportino.model.Staff;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author aleksandrserbin
 * <p> Specific Data Access Object for Activity Objects </p>
 * <p> Contains all methods of DAO interface and adds some specific</p>
 * @see it.adfor.rapportino.dao.DAO
 * @see it.adfor.rapportino.model.Activity
 */
@Repository("activityDao")
public class ActivityDaoImpl extends AbstractDao<Activity> implements ActivityDao{
    
    /**
     * 
     * @param s Employee, whose activities should be found
     * @return Collection (List) of Activities for given Employee
     */
    public List<Activity> findByEmployee(Staff s){
        return getHibernateTemplate().getSessionFactory().getCurrentSession().
                createCriteria(Activity.class).add(Restrictions.eq("empl_id", s.getId())).
                list();
    }
    
    /**
     * 
     * @param p Project, whose activities should be found
     * @return Collection (List) of Activities for given Project
     */
    public List<Activity> findByProject(Project p){
        return getHibernateTemplate().getSessionFactory().getCurrentSession().
                createCriteria(Activity.class).add(Restrictions.eq("proj_id", p.getId())).
                list();
    } 
    
    /**
     * 
     * @param s Employee, whose activities should be found
     * @param begin Date from which activities should be found
     * @param end Date until which activities should be found
     * @return Collection (List) of Activities for given Employee with restrictions about date
     */
    public List<Activity> findByEmployee(Staff s, Date begin, Date end){
        return getHibernateTemplate().getSessionFactory().getCurrentSession().
                createCriteria(Activity.class).add(Restrictions.and(
                Restrictions.or(Restrictions.le("date", end),
                                Restrictions.ge("date", begin)),
                Restrictions.eq("empl_id", s.getId()))).list();
    }
    
    /**
     * 
     * @param p Project, whose activities should be found
     * @param begin Date from which activities should be found
     * @param end Date until which activities should be found
     * @return Collection (List) of Projects for given Employee with restrictions about date
     */
    public List<Activity> findByProject(Project p, Date begin, Date end){
        return getHibernateTemplate().getSessionFactory().getCurrentSession().
                createCriteria(Activity.class).add(Restrictions.and(
                Restrictions.or(Restrictions.le("date", end),
                                Restrictions.ge("date", begin)),
                Restrictions.eq("proj_id", p.getId()))).list();
    }
    
    /**
     * 
     * @param kv Key-Value pairs of attributes and their values
     * For example, "hours" -> 8
     * @return List of Activities according to given restrictions
     */
    public List<Activity> findByParameters(Map<String,String> kv){
        StringBuilder sb = new StringBuilder();
        sb.append("select from Activity where ");
        for (String key : kv.keySet()){
            sb.append(key);
            sb.append("="+kv.get(key));
            sb.append(" AND ");
        }
        sb.delete(sb.length()-5, 4);
        sb.append(";");
        return (List<Activity>) getHibernateTemplate().find(sb.toString());
    }
    
}
