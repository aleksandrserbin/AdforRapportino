package it.adfor.rapportino.dao;

import it.adfor.rapportino.model.Client;
import it.adfor.rapportino.model.Division;
import it.adfor.rapportino.model.Project;
import it.adfor.rapportino.model.Staff;
import java.util.Date;
import java.util.List;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 *
 * @author aleksandrserbin
 * <p> Specific Data Access Object for Project Objects </p>
 * <p> Contains all methods of DAO interface and adds some specific</p>
 * @see it.adfor.rapportino.dao.DAO
 * @see it.adfor.rapportino.model.Project
 */
@Repository("projectDao")
public class ProjectDao extends AbstractDao<Project>{
    /**
     * 
     * @param c Client, whose projects should be found
     * @return List of projects according to given restrictions
     */
    public List<Project> findByClient(Client c){
        return getHibernateTemplate().getSessionFactory().getCurrentSession().
                createCriteria(Project.class).add(Restrictions.eq("client_id", c.getId())).
                list();
    }
    
    
    /**
     * 
     * @param pm Project Manager, whose projects should be found
     * @return List of projects according to given restrictions
     */
    public List<Project> findByProjectManager(Staff pm){
        return getHibernateTemplate().getSessionFactory().getCurrentSession().
                createCriteria(Project.class).
                add(Restrictions.eq("pm_id",pm.getId())).list();
    }
    
    /**
     * 
     * @param cm Client Manager, whose projects should be found
     * @return List of projects according to given restrictions
     */
     public List<Project> findByClientManager(Staff cm){
        return getHibernateTemplate().getSessionFactory().getCurrentSession().
                createCriteria(Project.class).
                add(Restrictions.eq("cm_id",cm.getId())).list();
    }
     
    /**
     * 
     * @param name Name of project to be found
     * @return List of projects according to given restrictions
     */
    public List<Project> findByName(String name){
        return getHibernateTemplate().getSessionFactory().getCurrentSession().
                createCriteria(Project.class).
                add(Restrictions.eq("name",name)).list();
    }
    
    /**
     * @param d Division, whose projects should be found
     * @return List of projects according to given restrictions
     */
    public List<Project> findByDivision(Division d){
        return getHibernateTemplate().getSessionFactory().getCurrentSession().
                createCriteria(Project.class).
                add(Restrictions.eq("division_id",d.getId())).list();
    }
}
